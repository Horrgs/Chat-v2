package org.horrgs.chat.server.userdata;

/**
 * Created by Horrgs on 11/21/2015.
 */
public class UserManager implements User {
    private String username, password, email;
    private boolean online;

    public void createNewUser(String json) {

    }
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public boolean isOnline() {
        return online;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setOnline(boolean online) {
        this.online = online;
    }
}
