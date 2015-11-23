package org.horrgs.chat.server.exceptions;

/**
 * Created by Horrgs on 11/22/2015.
 */
public class DataNonExistentException extends Exception {
    public DataNonExistentException() {
        super();
    }
    public DataNonExistentException(String message) {
        super(message);
    }

    public DataNonExistentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNonExistentException(Throwable cause) {
        super(cause);
    }
}
