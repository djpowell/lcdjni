package net.djpowell.lcdjni;

/**
 * An exception representing an error in the api.
 *
 * @author David Powell
 * @since 05-Apr-2010
 */
public class LcdException extends RuntimeException {
    // a runtime exception because checked exceptions suck

    private final int code;

    /**
     * Construct an exception from a native error code.
     *
     * @param code native error code.
     */
    public LcdException(int code) {
        super(getMessage(code));
        this.code = code;
    }

    private static String getMessage(int code) {
        return "LCD Exception [" + code + "] " + decode(code);
    }

    private static String decode(int code) {
        if (code == Native.cERROR_ALREADY_EXISTS) return "ERROR_ALREADY_EXISTS";
        else if (code == Native.cERROR_ALREADY_INITIALIZED) return "ERROR_ALREADY_INITIALIZED";
        else if (code == Native.cERROR_DEVICE_NOT_CONNECTED) return "ERROR_DEVICE_NOT_CONNECTED";
        else if (code == Native.cERROR_FILE_NOT_FOUND) return "ERROR_FILE_NOT_FOUND";
        else if (code == Native.cERROR_INVALID_PARAMETER) return "ERROR_INVALID_PARAMETER";
        else if (code == Native.cERROR_LOCK_FAILED) return "ERROR_LOCK_FAILED";
        else if (code == Native.cERROR_NO_SYSTEM_RESOURCES) return "ERROR_NO_SYSTEM_RESOURCES";
        else if (code == Native.cERROR_OLD_WIN_VERSION) return "ERROR_OLD_WIN_VERSION";
        else if (code == Native.cERROR_SERVICE_NOT_ACTIVE) return "ERROR_SERVICE_NOT_ACTIVE";
        else if (code == Native.cERROR_SUCCESS) return "ERROR_SUCCESS";
        else if (code == Native.cRPC_S_SERVER_UNAVAILABLE) return "RPC_S_SERVER_UNAVAILABLE";
        else if (code == Native.cRPC_X_WRONG_PIPE_VERSION) return "RPC_X_WRONG_PIPE_VERSION";
        else return "UNKNOWN ERROR";
    }

    /**
     * Get the native error code.
     *
     * @return native error code
     */
    public int getCode() {
        return code;
    }

    /**
     * Check a native error code, and it it is non-success, create an exception and throw it.
     *
     * @param status native error code
     * @throws LcdException exception corresponding to the code
     */
    public static void checkResult(int status) throws LcdException {
        if (status != Native.cERROR_SUCCESS) {
            throw new LcdException(status);
        }
    }

}
