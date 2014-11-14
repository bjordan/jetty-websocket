package org.barryjordan.websocket.server.exception;

/**
 * Base exception for request dispatching issues.
 */
public class ProtocolException extends RuntimeException {

    static final long serialVersionUID = 2779260104662464591L;

    /**
     * Creates a new exception from the given cause.
     *
     * @param cause The root cause.
     */
    public ProtocolException(final Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new exeption with the given message.
     *
     * @param message The exception cause.
     */
    public ProtocolException(final String message) {
        super(message);
    }

    /**
     * Creates a new exeption with the given message and cause.
     *
     * @param message The exception cause.
     * @param cause The exception which caused this one.
     */
    public ProtocolException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
