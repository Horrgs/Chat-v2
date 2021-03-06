package org.horrgs.chat.server.jsonformat;

import org.horrgs.chat.server.exceptions.FormatKeysException;

/**
 * Created by Horrgs on 11/21/2015.
 */
public class CreateAccountFormat extends Module {
    private String email;
    private String username;
    private String password;
    private RequestType requestType;
    private String jsonFormat;
    private static CreateAccountFormat instance = new CreateAccountFormat();
    public static CreateAccountFormat getInstance() {
        return instance;
    }
    public CreateAccountFormat() {
        super();
    }

    @Format(requestType = RequestType.CREATE_ACCOUNT)
    public CreateAccountFormat(Module format, String[] keys, String... values) throws FormatKeysException {
        super(getInstance(), keys, values);
    }

    public String getUsername() {
        return getValue(username);
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}