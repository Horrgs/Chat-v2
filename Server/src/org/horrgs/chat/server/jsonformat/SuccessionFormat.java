package org.horrgs.chat.server.jsonformat;

import org.horrgs.chat.server.exceptions.FormatKeysException;
import org.horrgs.chat.server.userdata.Rank;

/**
 * Created by Horrgs on 11/21/2015.
 */
public class SuccessionFormat extends Module {

    @Format(requestType = RequestType.SUCCESSION)
    public SuccessionFormat(Module format, String[] keys, String... values) throws FormatKeysException {
        super(format, keys, values);
    }

    public Rank getRank() {
        return getValue("rank");
    }

    public RequestType getSuccessionIn() {
        return getValue("succession");
    }


}
