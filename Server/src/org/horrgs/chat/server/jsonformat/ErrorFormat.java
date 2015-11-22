package org.horrgs.chat.server.jsonformat;

import org.horrgs.chat.server.exceptions.FormatKeysException;

/**
 * Created by Horrgs on 11/22/2015.
 */
public class ErrorFormat extends Module {
    @Format(requestType = RequestType.ERROR)
    public ErrorFormat(Module format, String[] keys, String... values) throws FormatKeysException {
        super(format, keys, values);
    }

    public String getMessage() {
        return getValue("message");
    }
}