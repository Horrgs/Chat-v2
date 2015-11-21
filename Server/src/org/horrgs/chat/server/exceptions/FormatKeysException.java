package org.horrgs.chat.server.exceptions;

/**
 * Created by Horrgs on 11/21/2015.
 */
public class FormatKeysException extends Exception {
    public FormatKeysException() {
        super();
    }
    public FormatKeysException(String message) {
        super(message);
    }

    public FormatKeysException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormatKeysException(Throwable cause) {
        super(cause);
    }
}
