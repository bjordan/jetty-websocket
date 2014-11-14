package biz.rightshift.websocket.server.protocol;

import javax.websocket.Session;

/**
 * Protocol Request message model.
 */
public class ProtocolRequest extends Protocol {

    /**
     * Constructor.
     */
    public ProtocolRequest() {
        super();
        setType(Protocol.TYPE_REQUEST);
        setVersion(Protocol.VERSION_NUMBER);
    }

    /**
     * @param action the protocol action
     */
    public void setAction(final String action) {
        getHeaders().put(Protocol.HEADER_ACTION, action);
    }

    /**
     * @return the request action.
     */
    public String getAction() {
        return getHeaderAs(Protocol.HEADER_ACTION, String.class);
    }

    /**
     * @param handler the protocol action handler
     */
    public void setHandler(final String handler) {
        getHeaders().put(Protocol.HEADER_HANDLER, handler);
    }

    /**
     * @return the request handler.
     */
    public String getHandler() {
        return getHeaderAs(Protocol.HEADER_HANDLER, String.class);
    }

    /**
     * @param connectionID the protocol connection ID
     */
    public void setConnectionID(final String connectionID) {
        getHeaders().put(Protocol.HEADER_CONNECTION_ID, connectionID);
    }

    /**
     * @return the request connection ID.
     */
    public String getConnectionID() {
        return getHeaderAs(Protocol.HEADER_CONNECTION_ID, String.class);
    }

    /**
     * @param requestID the protocol connection ID
     */
    public void setRequestID(final String requestID) {
        getHeaders().put(Protocol.HEADER_REQUEST_ID, requestID);
    }

    /**
     * @return the request request ID.
     */
    public String getRequestID() {
        return getHeaderAs(Protocol.HEADER_REQUEST_ID, String.class);
    }

    /**
     * @return The {@link Session} for this request.
     */
    public Session getSession() {
        return getHeaderAs(Protocol.HEADER_SESSION, Session.class);
    }

    /**
     * @param session The session to associate with this request.
     */
    public void setSession(final Session session) {
        getHeaders().put(Protocol.HEADER_SESSION, session);
    }

    /**
     * @return the remote address
     */
    public String getRemoteAddress() {
        return getHeaderAs(Protocol.HEADER_REMOTE_ADDRESS, String.class);
    }

    /**
     * @param remoteAddress the remote address
     */
    public void setRemoteAddress(final String remoteAddress) {
        getHeaders().put(Protocol.HEADER_REMOTE_ADDRESS, remoteAddress);
    }
}

