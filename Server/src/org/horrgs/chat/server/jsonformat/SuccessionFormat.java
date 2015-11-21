package org.horrgs.chat.server.jsonformat;

import org.horrgs.chat.server.exceptions.FormatKeysException;

/**
 * Created by Horrgs on 11/21/2015.
 */
public class SuccessionFormat extends Module {

    @Format(requestType = RequestType.SUCCESSION)
    public SuccessionFormat(Module format, String[] keys, String... values) throws FormatKeysException {
        super(format, keys, values);
    }

    @Override
    public RequestType getRequestType() {
        return super.getRequestType();
    }

    @Override
    public void setRequestType(RequestType requestType) {
        super.setRequestType(requestType);
    }

    @Override
    public String getJsonFormat() {
        return super.getJsonFormat();
    }

    @Override
    public void setJsonFormat(String jsonFormat) {
        super.setJsonFormat(jsonFormat);
    }

    @Override
    public <T> T getValue(String key) {
        return super.getValue(key);
    }
}
