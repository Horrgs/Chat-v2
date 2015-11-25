package org.horrgs.chat.server.userdata;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.horrgs.chat.server.FileManager;
import org.horrgs.chat.server.exceptions.UserNotFoundException;

import java.io.FileReader;

/**
 * Created by Horrgs on 11/21/2015.
 */
public abstract class User {
    private String username, password, email;
    private boolean online;
    private Rank rank;
    private User user;

    public User(String username) throws UserNotFoundException {
        this(username, false);
    }

    public User() {
        super();
    }

    public User(String username, boolean online) throws UserNotFoundException {
        JsonArray jsonArray = (JsonArray) new FileManager().parseJson("users.json");
        for(int x = 0; x < jsonArray.size(); x++) {
            JsonObject jsonObject = (JsonObject) jsonArray.get(x);
            if(jsonObject.get("username").equals(username)) {
                setEmail(jsonObject.get("email").getAsString());
                setUsername(jsonObject.get("username").getAsString());
                setPassword(jsonObject.get("password").getAsString());
                setRank(Rank.getByName(jsonObject.get("rank").getAsString()));
                setOnline(online); //TODO: if true, should add to those online list in UserManager.
                setUser(this);
                return;
            }
        }
        throw new UserNotFoundException("No user with the username \"" + username + "\" exists.");
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public Rank getRank() {
        return rank;
    }
    public boolean isOnline(){
        return online;
    }
    public User getUser() {
        return user;
    }
    //public Status getStatus(){\n}//TODO: status.
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setRank(Rank rank){
        this.rank = rank;
    }
    public void setOnline(boolean online){
        this.online = online;
    }
    public void setUser(User user) {
        this.user = user;
    }
    //public void setStatus(Status status){\n} //TODO: status
}
