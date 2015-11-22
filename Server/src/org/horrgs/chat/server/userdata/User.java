package org.horrgs.chat.server.userdata;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.horrgs.chat.server.jsonformat.CreateAccountFormat;

import java.io.FileReader;

/**
 * Created by Horrgs on 11/21/2015.
 */
public abstract class User {
    private String username, password, email;
    private boolean online;
    private Rank rank;

    public User(String username) {
        this(username, false);
    }

    public User(String username, boolean online) {
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = null;
        try {
            Object obj = jsonParser.parse(new FileReader("secrets.json"));
            jsonArray = (JsonArray) obj;
            System.out.println("Parsing secrets.json ....");
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
        for(int x = 0; x < jsonArray.size(); x++) {
            JsonObject jsonObject = (JsonObject) jsonArray.get(x);
            if(jsonObject.get("username").equals(username)) {
                setEmail(jsonObject.get("email").getAsString());
                setUsername(jsonObject.get("username").getAsString());
                setPassword(jsonObject.get("password").getAsString());
                setRank(Rank.getByName(jsonObject.get("rank").getAsString()));
                setOnline(online);
            }
        }
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
    //public void setStatus(Status status){\n} //TODO: status
}
