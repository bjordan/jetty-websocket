package biz.rightshift.websocket.server.protocol;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * ProtocolRequest response status.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public final class ProtocolResponseStatus {

    /** Code for an OK response. */
    public static final int STATUS_CODE_OK = 200;
    /** Code for a failed response. */
    public static final int STATUS_CODE_ERROR = 400;
    /** Code for an internal server error. */
    public static final int STATUS_CODE_INTERNAL_ERROR = 500;

    private int code;
    private Detail detail;

    /**
     * Default constructor.
     */
    public ProtocolResponseStatus() { }

    /**
     * Constructor.
     * @param code The status code.
     */
    public ProtocolResponseStatus(final int code) {
        this.code = code;
    }

    /**
     * Constructor.
     * @param code The status code.
     * @param internalCode The internal error code.
     * @param reason string representation of code
     */
    public ProtocolResponseStatus(final int code, final int internalCode, final String reason) {
        this.code = code;
        this.detail = new Detail(internalCode, reason);
    }

    /**
     * @return status code
     */
    public int getCode() {
        return code;
    }

    /**
     * @return The details, or <code>null</code> if there are none.
     */
    public Detail getDetail() {
        return detail;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getCode())
                .append(getDetail())
                .toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof ProtocolResponseStatus) {
            final ProtocolResponseStatus other = (ProtocolResponseStatus) obj;
            return new EqualsBuilder()
                    .append(getCode(), other.getCode())
                    .append(getDetail(), other.getDetail())
                    .isEquals();
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("ResponseStatus: [code: ").append(getCode());
        if (getDetail() != null) {
            string.append(" internalCode: ")
                    .append(getDetail().getCode())
                    .append(" reason: ")
                    .append(getDetail().getReason());
        }
        string.append("]");
        return string.toString();
    }

    /**
     * A holder for response detial, if applicable.
     */
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public static class Detail {
        private int code;
        private String reason;

        /**
         * Creates a new instance.
         *
         * @param code The internal error code.
         * @param reason The reason.
         */
        public Detail(final int code, final String reason) {
            this.code = code;
            this.reason = reason;
        }

        /**
         * @return The detail code.
         */
        public int getCode() {
            return code;
        }

        /**
         * @return The detailed reason.
         */
        public String getReason() {
            return reason;
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                    .append(getCode())
                    .append(getReason())
                    .toHashCode();
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof Detail) {
                final Detail other = (Detail) obj;
                return new EqualsBuilder()
                        .append(getCode(), other.getCode())
                        .append(getReason(), other.getReason())
                        .isEquals();
            }
            return false;
        }
    }
}
