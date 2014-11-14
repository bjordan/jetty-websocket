package biz.rightshift.websocket.server.server;

import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.jsr356.server.AnnotatedServerEndpointConfig;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.SmartLifecycle;

import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.util.HashMap;
import java.util.Map;

/**
 * Lifecycle bean that wraps the Jetty Server.
 */
public final class JettyContainer implements SmartLifecycle, DisposableBean {

    /** Default port. **/
    public static final int DEFAULT_PORT = 8080;
    /** Default secure port. **/
    public static final int DEFAULT_PORT_SSL = 8443;
    /** Default host. **/
    public static final String DEFAULT_HOST = "127.0.0.1";
    /** Is SSL enabled by default. **/
    public static final boolean SSL_ENABLED = false;

    private static final boolean AUTO_STARTUP = true;
    private static final int PHASE = Integer.MAX_VALUE;
    private static final String DEFAULT_PATH = "/";
    private static final String PROTOCOL = "http/1.1";
    private volatile boolean running = false;

    private String host = DEFAULT_HOST;
    private int port = DEFAULT_PORT;
    private int sslPort = DEFAULT_PORT_SSL;

    private boolean sslEnabled;
    private String keyStorePath;
    private String keyStorePassword;
    private String keyManagerPassword;

    private Server server;
    private final Map<Class<?>, ServerEndpointConfig.Configurator> endPoints;

    /**
     * Creates a new instance.
     */
    public JettyContainer() {
        endPoints = new HashMap<>();
    }

    /**
     * @param host The host to bind to.
     */
    public void setHost(final String host) {
        this.host = host;
    }

    /**
     * @param port The port to bind to.
     */
    public void setPort(final int port) {
        this.port = port;
    }

    /**
     * @param sslPort The secure port to bind to.
     */
    public void setSslPort(final int sslPort) {
        this.sslPort = sslPort;
    }

    /**
     * @param keyStorePath SSL keystore path.
     */
    public void setKeyStorePath(final String keyStorePath) {
        this.keyStorePath = keyStorePath;
    }

    /**
     * @param keyStorePassword SSL keystore password.
     */
    public void setKeyStorePassword(final String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    /**
     * @param keyManagerPassword SSL key manager password.
     */
    public void setKeyManagerPassword(final String keyManagerPassword) {
        this.keyManagerPassword = keyManagerPassword;
    }

    /**
     * @param sslEnabled Enable SSL port.
     */
    public void setSslEnabled(final boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    /**
     * Adds a new endpoint plus a configurator.
     *
     * @param clazz The end point class.
     * @param configurator The configurator to create instances of endpoint class.
     */
    public void addEndPoint(final Class<?> clazz, final ServerEndpointConfig.Configurator configurator) {
        endPoints.put(clazz, configurator);
    }

    @Override
    public void start() {
        server = new Server();

        // non secure connector
        ServerConnector http = new ServerConnector(server);
        http.setPort(port);
        http.setHost(host);

        server.addConnector(http);

        if (sslEnabled) {
            SslContextFactory sslContextFactory = new SslContextFactory();
            sslContextFactory.setKeyStorePath(keyStorePath);
            sslContextFactory.setKeyStorePassword(keyStorePassword);
            sslContextFactory.setKeyManagerPassword(keyManagerPassword);

            HttpConfiguration httpsConfiguration = new HttpConfiguration();
            httpsConfiguration.addCustomizer(new SecureRequestCustomizer());

            // secure connector
            ServerConnector https = new ServerConnector(
                    server,
                    new SslConnectionFactory(sslContextFactory, PROTOCOL),
                    new HttpConnectionFactory(httpsConfiguration));
            https.setHost(host);
            https.setPort(sslPort);

            server.addConnector(https);
        }

        // Create the Spring application context
        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(DEFAULT_PATH);

        server.setHandler(context);

        ServerContainer serverContainer = WebSocketServerContainerInitializer.configureContext(context);

        try {
            for (final Map.Entry<Class<?>, ServerEndpointConfig.Configurator> entry : endPoints.entrySet()) {
                serverContainer.addEndpoint(
                        new AnnotatedServerEndpointConfig(
                                entry.getKey(),
                                entry.getKey().getAnnotation(ServerEndpoint.class)
                        ) {
                            @Override
                            public Configurator getConfigurator() {
                                return entry.getValue();
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        running = true;
    }

    @Override
    public void destroy() {
        stop();
    }

    @Override
    public void stop() {
        if (server != null) {
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        running = false;
    }

    @Override
    public void stop(final Runnable callback) {
        stop();
        callback.run();
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public boolean isAutoStartup() {
        return AUTO_STARTUP;
    }

    @Override
    public int getPhase() {
        return PHASE;
    }
}
