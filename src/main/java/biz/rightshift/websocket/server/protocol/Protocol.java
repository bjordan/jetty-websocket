package biz.rightshift.websocket.server.protocol;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract Protocol.
 */
public abstract class Protocol {

    /* protocol message attributes */

    /** Headers attribute. */
    public static final String ATTR_HEADERS = "headers";
    /** Payload attribute. */
    public static final String ATTR_PAYLOAD = "payload";
    /** Message type attribute. */
    public static final String ATTR_TYPE = "type";
    /** Message version attribute. */
    public static final String ATTR_VERSION = "version";

    /* protocol header keys */

    /** Action header key. */
    public static final String HEADER_ACTION = "action";
    /** Connection ID header key. */
    public static final String HEADER_CONNECTION_ID = "connectionID";
    /** Handler header key. */
    public static final String HEADER_HANDLER = "handler";
    /** Push message message type key. */
    public static final String HEADER_MESSAGE_TYPE = "messageType";
    /** Request ID header key. */
    public static final String HEADER_REQUEST_ID = "requestID";
    /** Response status header key. */
    public static final String HEADER_RESPONSE_STATUS = "responseStatus";
    /** Timestamp created key. */
    public static final String HEADER_TIMESTAMP_CREATED = "timestampCreated";
    /** Timestamp createdAt key. */
    public static final String HEADER_TIMESTAMP_PUSHED = "timestampPushed";
    /** Topic header key. */
    public static final String HEADER_TOPIC = "topic";
    /** Session header key. */
    public static final String HEADER_SESSION = "session";
    /** Remote Address header key. */
    public static final String HEADER_REMOTE_ADDRESS = "remoteAddress";
    /** Message version header key. */
    public static final String HEADER_MESSAGE_VERSION = "messageVersion";

    /* message types */

    /** Push message type. */
    public static final String TYPE_PUSH = "push";
    /** Request message type. */
    public static final String TYPE_REQUEST = "request";
    /** Response message type. */
    public static final String TYPE_RESPONSE = "response";

    /** Request key. */
    public static final String REQUEST_KEY = "protocolRequest";

    /** Current version key. */
    public static final String VERSION_NUMBER = "1.0";

    private String version;
    private String type;
    private Map<String, Object> headers;
    private Object payload;

    /**
     * Default Constructor.
     */
    public Protocol() {
        headers = new HashMap<>();
    }

    /**
     * @return version the protocol version
     */
    public final String getVersion() {
        return version;
    }

    /**
     * @param version the protocol version
     */
    public final void setVersion(final String version) {
        this.version = version;
    }

    /**
     * @return payload the push payload
     */
    public final Object getPayload() {
        return payload;
    }

    /**
     * @param payload the protocol payload
     */
    public final void setPayload(final Object payload) {
        this.payload = payload;
    }

    /**
     * @return headers the response headers
     */
    public final Map<String, Object> getHeaders() {
        return headers;
    }

    /**
     * @param headers the protocol push headers
     */
    public final void setHeaders(final Map<String, Object> headers) {
        this.headers = headers;
    }

    /**
     * @return type the protocol message type
     */
    public final String getType() {
        return type;
    }

    /**
     * @param type the protocol message type
     */
    public final void setType(final String type) {
        this.type = type;
    }

    /**
     * Utility function to get a header value as a specified type.
     * Returns <code>null</code> if the header does not exist.
     *
     * @param header The header name.
     * @param type The expected type.
     * @return The header value as the expected type.
     */
    protected final <T extends Object> T getHeaderAs(final String header, final Class<T> type) {
        Object headerValue = getHeaders().get(header);
        if (headerValue == null) {
            return null;
        } else if (type.isAssignableFrom(headerValue.getClass())) {
            return type.cast(headerValue);
        } else {
            throw new IllegalArgumentException(
                    String.format("Header '%s' not of type %s, instead of type '%s'",
                        header, type.getName(), headerValue.getClass().getName()));
        }
    }
}
