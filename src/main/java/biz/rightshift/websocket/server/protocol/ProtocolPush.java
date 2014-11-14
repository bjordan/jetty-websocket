package biz.rightshift.websocket.server.protocol;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Protocol Push message model.
 */
public final class ProtocolPush extends Protocol {

    /**
     * Constructor.
     */
    public ProtocolPush() {
        super();
        setType(Protocol.TYPE_PUSH);
        setVersion(Protocol.VERSION_NUMBER);
    }

    /**
     * @return messageType the protocol payload message type
     */
    @JsonIgnore
    public String getMessageType() {
        return getHeaderAs(Protocol.HEADER_MESSAGE_TYPE, String.class);
    }

    /**
     * @param messageType the protocol payload message type
     */
    @JsonIgnore
    public void setMessageType(final String messageType) {
        getHeaders().put(Protocol.HEADER_MESSAGE_TYPE, messageType);
    }

    /**
     * @return the payload creation timestamp
     */
    @JsonIgnore
    public Long getTimestampCreated() {
        return getHeaderAs(Protocol.HEADER_TIMESTAMP_CREATED, Long.class);
    }

    /**
     * @param timestamp the payload creation timestamp
     */
    @JsonIgnore
    public void setTimestampCreated(final Long timestamp) {
        getHeaders().put(Protocol.HEADER_TIMESTAMP_CREATED, timestamp);
    }

    /**
     * @return the payload pushed timestamp
     */
    @JsonIgnore
    public Long getTimestampPushed() {
        return getHeaderAs(Protocol.HEADER_TIMESTAMP_PUSHED, Long.class);
    }

    /**
     * @param timestamp the payload pushed timestamp
     */
    @JsonIgnore
    public void setTimestampPushed(final Long timestamp) {
        getHeaders().put(Protocol.HEADER_TIMESTAMP_PUSHED, timestamp);
    }

    /**
     * @return topic the message topic
     */
    @JsonIgnore
    public String getTopic() {
        return getHeaderAs(Protocol.HEADER_TOPIC, String.class);
    }

    /**
     * @param topic the message topic
     */
    @JsonIgnore
    public void setTopic(final String topic) {
        getHeaders().put(Protocol.HEADER_TOPIC, topic);
    }

    /**
     * @return the message version
     */
    @JsonIgnore
    public int getMessageVersion() {
        return getHeaderAs(Protocol.HEADER_MESSAGE_VERSION, Integer.class);
    }

    /**
     * @param version the message version
     */
    @JsonIgnore
    public void setMessageVersion(final int version) {
        getHeaders().put(Protocol.HEADER_MESSAGE_VERSION, version);
    }
}
