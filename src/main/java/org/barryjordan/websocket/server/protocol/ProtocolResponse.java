package org.barryjordan.websocket.server.protocol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Protocol Response message model.
 */
public final class ProtocolResponse extends Protocol {

    /**
     * Constructor.
     */
    public ProtocolResponse() {
        super();
        setType(Protocol.TYPE_RESPONSE);
        setVersion(Protocol.VERSION_NUMBER);
    }

    /**
     * @param requestID the protocol connection ID
     */
    @JsonIgnore
    public void setRequestID(final String requestID) {
        getHeaders().put(Protocol.HEADER_REQUEST_ID, requestID);
    }

    /**
     * @return the request request ID.
     */
    @JsonIgnore
    public String getRequestID() {
        return getHeaderAs(Protocol.HEADER_REQUEST_ID, String.class);
    }

    /**
     * @param responseStatus the response status details.
     */
    @JsonIgnore
    public void setResponseStatus(final ProtocolResponseStatus responseStatus) {
        getHeaders().put(Protocol.HEADER_RESPONSE_STATUS, responseStatus);
    }

    /**
     * @return the response status details.
     */
    @JsonIgnore
    public ProtocolResponseStatus getResponseStatus() {
        return getHeaderAs(Protocol.HEADER_RESPONSE_STATUS, ProtocolResponseStatus.class);
    }

    @Override
    public String toString() {
        return getType() + " "
                + getVersion() + " "
                + getPayload() + " "
                + getRequestID() + " "
                + getResponseStatus();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getType())
                .append(getVersion())
                .append(getPayload())
                .append(getRequestID())
                .append(getResponseStatus())
                .toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof ProtocolResponse) {
            final ProtocolResponse other = (ProtocolResponse) obj;
            return new EqualsBuilder()
                    .append(getType(), other.getType())
                    .append(getVersion(), other.getVersion())
                    .append(getPayload(), other.getPayload())
                    .append(getRequestID(), other.getRequestID())
                    .append(getResponseStatus(), other.getResponseStatus())
                    .isEquals();
        } else {
            return false;
        }
    }
}
