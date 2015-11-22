package org.horrgs.chat.server.exceptions;

/**
 * Created by Horrgs on 11/22/2015.
 */
public class DataInUseException extends Exception {
    public DataInUseException() {
        super();
    }
    public DataInUseException(String message) {
        super(message);
    }

    public DataInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataInUseException(Throwable cause) {
        super(cause);
    }
}
