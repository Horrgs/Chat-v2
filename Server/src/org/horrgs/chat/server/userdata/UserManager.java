package org.horrgs.chat.server.userdata;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.horrgs.chat.server.exceptions.DataInUseException;
import org.horrgs.chat.server.jsonformat.CreateAccountFormat;

import javax.xml.crypto.Data;
import java.io.FileReader;

/**
 * Created by Horrgs on 11/21/2015.
 */
public class UserManager extends User {
    private boolean online;

    public UserManager(String username) {
        super(username);
    }

    public UserManager(String username, boolean online) {
        super(username, online);
    }

    public void createNewUser(CreateAccountFormat createAccountFormat) throws DataInUseException {
        if(isEmailInUse(createAccountFormat.getEmail())) {
            throw new DataInUseException("That email is already being used!");
        }
        if(isUsernameTaken(createAccountFormat.getUsername())) {
            throw new DataInUseException("That password is already being used!");
        }

    }

    public boolean isUsernameTaken(String username) {
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = null;
        try {
            Object obj = jsonParser.parse(new FileReader("secrets.json"));
            jsonArray = (JsonArray) obj;
            System.out.println("Parsing secrets.json ....");
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        for(int x = 0; x < jsonArray.size(); x++) {
            JsonObject jsonObject = (JsonObject) jsonArray.get(x);
            if(jsonObject.get("username").equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean isUsernameTaken() {
        return isUsernameTaken(getUsername());
    }

    public boolean isEmailInUse(String email) {
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = null;
        try {
            Object obj = jsonParser.parse(new FileReader("secrets.json"));
            jsonArray = (JsonArray) obj;
            System.out.println("Parsing secrets.json ....");
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        for(int x = 0; x < jsonArray.size(); x++) {
            JsonObject jsonObject = (JsonObject) jsonArray.get(x);
            if(jsonObject.get("email").getAsString().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmailInUse() {
        return isEmailInUse(getEmail());
    }
}
