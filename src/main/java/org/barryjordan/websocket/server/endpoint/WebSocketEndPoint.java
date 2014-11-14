package org.barryjordan.websocket.server.endpoint;

import org.barryjordan.websocket.server.connection.ConnectionManager;
import org.barryjordan.websocket.server.encoder.ProtocolPushEncoder;
import org.barryjordan.websocket.server.encoder.ProtocolRequestDecoder;
import org.barryjordan.websocket.server.encoder.ProtocolResponseEncoder;
import org.barryjordan.websocket.server.exception.InternalServerErrorCodes;
import org.barryjordan.websocket.server.exception.ProtocolException;
import org.barryjordan.websocket.server.protocol.ProtocolRequest;
import org.barryjordan.websocket.server.protocol.ProtocolResponse;
import org.barryjordan.websocket.server.protocol.ProtocolResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpoint;

/**
 * Creates a new instance for each connection.
 */
@ServerEndpoint(
        value = "/",
        decoders = { ProtocolRequestDecoder.class },
        encoders = { ProtocolResponseEncoder.class, ProtocolPushEncoder.class })
public class WebSocketEndPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEndPoint.class);
    private final ConnectionManager connectionManager;

    /**
     * Creates a new instance.
     *
     * @param connectionManager Used to manage connections.
     */
    public WebSocketEndPoint(final ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Invoked on connect.
     *
     * @param session The session object for this connection.
     * @param endpointConfig THe endpoint configuration.
     */
    @OnOpen
    public void onWebSocketConnect(final Session session, final EndpointConfig endpointConfig) {
        HandshakeRequest request = (HandshakeRequest) endpointConfig.getUserProperties().get("handshakeRequest");

        LOGGER.info("Socket Opened: {}", session.getId());
        LOGGER.info("User Agent: {}", request.getHeaders().get("User-Agent"));

        connectionManager.addConnection(session);
    }

    /**
     * Invoked when a request packet is received.
     *
     * @param session The socket session.
     * @param request The request object.
     */
    @OnMessage
    public void onProtocolRequest(final Session session, final ProtocolRequest request) {
        LOGGER.info("Request: {}", request);

        ProtocolResponse p = new ProtocolResponse();
        p.setRequestID(request.getRequestID());
        p.setResponseStatus(new ProtocolResponseStatus(ProtocolResponseStatus.STATUS_CODE_OK));

        session.getAsyncRemote().sendObject(p);
    }

    /**
     * Invoked the connection is closed.
     *
     * @param session The session.
     * @param reason The reason the connection was closed.
     */
    @OnClose
    public void onWebSocketClose(final Session session, final CloseReason reason) {
        LOGGER.info("Socket {} Closed: {}", session.getId(), reason.getReasonPhrase());

        connectionManager.removeConnection(session);
    }

    /**
     * Invoked when there are connection problems, runtime errors from
     * message handlers, or conversion errors when decoding messages.
     *
     * @param session The session.
     * @param cause The cause of the exception.
     */
    @OnError
    public void onWebSocketError(final Session session, final Throwable cause) {
        LOGGER.error("Error Socket " + session.getId(), cause);

        connectionManager.removeConnection(session);

        if (cause instanceof ProtocolException) {
            ProtocolResponse protocolResponse = new ProtocolResponse();
            protocolResponse.setResponseStatus(new ProtocolResponseStatus(
                    ProtocolResponseStatus.STATUS_CODE_INTERNAL_ERROR,
                    InternalServerErrorCodes.MALFORMED_PROTOCOL,
                    cause.getMessage()));

            if (session.isOpen()) {
                session.getAsyncRemote().sendObject(protocolResponse);
            }
        }
    }
}
