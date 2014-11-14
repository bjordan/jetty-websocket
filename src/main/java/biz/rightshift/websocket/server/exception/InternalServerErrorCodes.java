package biz.rightshift.websocket.server.exception;

/**
 * Internal server error constants.
 *
 * These describe the details for all internal errors.
 *
 * @errors Internal Errors
 */
public final class InternalServerErrorCodes {

    /** Unknown error code. */
    public static final int UNKNOWN = 0;
    /** No such route error code. */
    public static final int NO_SUCH_ROUTE = 1;
    /** Unauthorised error code. */
    public static final int UNAUTHORISED = 2;
    /** Malformed protocol error code. */
    public static final int MALFORMED_PROTOCOL = 3;
    /** Bad read error code. */
    public static final int BAD_READ = 4;
    /** Invalid request body. */
    public static final int INVALID_REQUEST_BODY = 5;
    /** Missing required parameter. */
    public static final int MISSING_PARAMETER = 6;
    /** Invalid parameter type. */
    public static final int INVALID_PARAMETER_TYPE = 7;

    /**
     * Don't instantiate.
     */
    private InternalServerErrorCodes() {
    }
}
