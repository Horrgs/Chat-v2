package org.horrgs.chat.server.jsonformat;

import org.horrgs.chat.server.exceptions.FormatKeysException;

import java.util.HashMap;

/**
 * Created by Horrgs on 11/21/2015.
 */
public abstract class Module {
    private HashMap<String, Object> jsonString = new HashMap<>();
    public String jsonFormat;
    public RequestType requestType;

    public Module() {
        super();
    }

    public Module(Module format, String[] keys, String... values) throws FormatKeysException {
        if(keys.length != values.length) {
            throw new FormatKeysException("Keys length does not equal values length in format message.");
        }
        jsonFormat = "{";
        for(int x = 0; x < keys.length; x++) {
            jsonString.put(keys[x], values[x]);
            if(x >= 1) {
                jsonFormat += ",\"" + keys[x] + "\":\"" + values[x];
            } else {
                jsonFormat += "\"" + keys[x] + "\":\"" + values[x];
            }
        }
        switch (format.getRequestType()) {
            case CREATE_ACCOUNT:
                CreateAccountFormat createAccountFormat = (CreateAccountFormat) format;
                createAccountFormat.setEmail(getValue("email"));
                createAccountFormat.setUsername(getValue("username"));
                createAccountFormat.setPassword(getValue("password"));
                createAccountFormat.setJsonFormat(jsonFormat);
                break;
            case LOGIN:
                LoginFormat loginFormat =  (LoginFormat) format;
                loginFormat.setEmail(getValue("email"));
                loginFormat.setUsername(getValue("username"));
                loginFormat.setPassword(getValue("password"));
                break;
            case ERROR:
                break;
        }

        jsonFormat += "}";
        setRequestType(RequestType.getByName(getValue("requestType")));
    }
    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getJsonFormat() {
        return jsonFormat;
    }

    public void setJsonFormat(String jsonFormat) {
        this.jsonFormat = jsonFormat;
    }

    public <T> T getValue(String key) {
        return (T) jsonString.get(key);
    }
}