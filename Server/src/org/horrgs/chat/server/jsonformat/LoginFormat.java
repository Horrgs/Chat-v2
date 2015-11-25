package org.horrgs.chat.server.jsonformat;

import org.horrgs.chat.server.exceptions.FormatKeysException;

/**
 * Created by Horrgs on 11/21/2015.
 */
public class LoginFormat extends Module {
    private String email, username, password;
    private static LoginFormat instance = new LoginFormat();
    public static LoginFormat getInstance() {
        return instance;
    }
    public LoginFormat() {
        super();
    }
    @Format(requestType = RequestType.LOGIN)
    public LoginFormat(Module format, String[] keys, String... values) throws FormatKeysException {
        super(format, keys, values);
    }
    @Override
    public void setJsonFormat(String jsonFormat) {
        super.setJsonFormat(jsonFormat);
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}