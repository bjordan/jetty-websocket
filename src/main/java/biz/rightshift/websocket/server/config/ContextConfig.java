package biz.rightshift.websocket.server.config;

import biz.rightshift.websocket.server.connection.ConnectionManager;
import biz.rightshift.websocket.server.endpoint.WebSocketEndPoint;
import biz.rightshift.websocket.server.endpoint.SocketEndpointConfigurator;
import biz.rightshift.websocket.server.server.JettyContainer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import javax.websocket.server.ServerEndpointConfig;

/**
 * Spring context configuration.
 */
@Configuration
@PropertySource("classpath:websocketserver.properties")
public class ContextConfig {

    /**
     * Used to maintain websocket connections.
     *
     * @return A ConnectionManager bean.
     */
    @Bean
    public ConnectionManager connectionManager() {
        return new ConnectionManager();
    }

    /**
     * A socketEndPoint bean is created for
     * each new websocket connection.
     *
     * @param connectionManager Manages connections.
     * @return The WebSocketEndPoint bean.
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebSocketEndPoint webSocketEndPoint(final ConnectionManager connectionManager) {
        return new WebSocketEndPoint(connectionManager);
    }

    /**
     * Used to create custom WebSocketEndPoint instances.
     *
     * @return A custom Configurator bean.
     */
    @Bean
    public ServerEndpointConfig.Configurator configurator() {
        return new SocketEndpointConfigurator();
    }

    /**
     * Creates and starts LifeCycle bean encapsulating a Jetty websocket server.
     *
     * @param configurator Custom configurator for creating WebSocketEndPoint instances.
     * @param env The Spring environment.
     * @return The JettyContainer bean.
     */
    @Bean
    public JettyContainer jettyContainer(
            final ServerEndpointConfig.Configurator configurator,
            final Environment env) {
        JettyContainer jettyContainer = new JettyContainer();
        jettyContainer.addEndPoint(WebSocketEndPoint.class, configurator);

        jettyContainer.setHost(env.getProperty("websocketserver.host", String.class, JettyContainer.DEFAULT_HOST));
        jettyContainer.setPort(env.getProperty("websocketserver.port", Integer.class, JettyContainer.DEFAULT_PORT));

        final boolean sslEnabled = env.getProperty("websocketserver.ssl.enabled", Boolean.class, JettyContainer.SSL_ENABLED);

        if (sslEnabled) {
            jettyContainer.setSslEnabled(sslEnabled);
            jettyContainer.setSslPort(env.getProperty("websocketserver.ssl.port", Integer.class, JettyContainer.DEFAULT_PORT_SSL));
            jettyContainer.setKeyStorePassword(env.getRequiredProperty("websocketserver.ssl.keystorepassword"));
            jettyContainer.setKeyStorePath(env.getRequiredProperty("websocketserver.ssl.keystorelocation"));
            jettyContainer.setKeyManagerPassword(env.getRequiredProperty("websocketserver.ssl.certificatepassword"));
        }

        return jettyContainer;
    }

    /**
     * Instantiate a PropertySourcesPlaceholderConfigurer.
     *
     * @return new PropertySourcesPlaceholderConfigurer bean
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
