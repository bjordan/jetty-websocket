package org.barryjordan.websocket.server.endpoint;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Custom endpoint configuration. Allows us to control
 * the instantiation of WebSocketEndPoint instances. In this
 * case we use Spring to manage the bean creation.
 */
public class SocketEndpointConfigurator
        extends ServerEndpointConfig.Configurator implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketEndpointConfigurator.class);
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void modifyHandshake(final ServerEndpointConfig conf,
                                final HandshakeRequest req,
                                final HandshakeResponse resp) {

        conf.getUserProperties().put("handshakeRequest", req);
    }

    @Override
    public boolean checkOrigin(final String originHeaderValue) {
        // customize this
        LOGGER.info("Origin: {}", originHeaderValue);
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getEndpointInstance(final Class<T> endpointClass) throws InstantiationException {
        return (T) applicationContext.getBean("webSocketEndPoint", WebSocketEndPoint.class);
    }
}
