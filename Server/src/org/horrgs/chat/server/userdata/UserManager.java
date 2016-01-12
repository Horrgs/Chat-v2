package org.horrgs.chat.server.userdata;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.horrgs.chat.server.FileManager;
import org.horrgs.chat.server.exceptions.DataInUseException;
import org.horrgs.chat.server.exceptions.UserNotFoundException;
import org.horrgs.chat.server.jsonformat.CreateAccountFormat;
import org.horrgs.chat.server.jsonformat.Module;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Horrgs on 11/21/2015.
 */
public class UserManager extends User {
    private static UserManager instance = new UserManager();
    public static UserManager getInstance() {
        return instance;
    }
    private List<User> usersOnline = new ArrayList<>();
    public UserManager() {
        super();
    }

    public UserManager(String username) throws UserNotFoundException {
        super(username);
    }

    public UserManager(String username, boolean online) throws UserNotFoundException {
        super(username, online);
    }

    public void createNewUser(CreateAccountFormat createAccountFormat) throws DataInUseException {
        if(isEmailInUse(createAccountFormat.getEmail())) {
            throw new DataInUseException("That email is already being used!");
        }
        if(isUsernameTaken(createAccountFormat.getUsername())) {
            throw new DataInUseException("That username is already being used!");
        }

        JsonArray jsonArray = (JsonArray) new FileManager().parseJson("users.json");
        try {
            JsonObject email = new JsonObject();
            jsonArray.add(email);
            email.addProperty("email", createAccountFormat.getEmail());
            email.addProperty("username", createAccountFormat.getUsername());
            email.addProperty("password", createAccountFormat.getPassword());
            //email.addProperty("status", Status.ONLINE.getId());
            email.addProperty("rank", Rank.USER.getName());
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("users.json"));
            bufferedWriter.write(jsonArray.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isUsernameTaken(String username) {
        JsonArray jsonArray = (JsonArray) new FileManager().parseJson("users.json");
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
        JsonArray jsonArray = (JsonArray) new FileManager().parseJson("users.json");
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

    public List<User> getUsersOnline() {
        return usersOnline;
    }
    public User getUser(String username) throws UserNotFoundException {
        for(User user : getUsersOnline()) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        JsonArray jsonArray = (JsonArray) new FileManager().parseJson("users.json");
        for (int x = 0; x < jsonArray.size(); x++) {
            JsonObject jsonObject = jsonArray.get(x).getAsJsonObject();
            if(jsonObject.get("username").getAsString().equals(username)) {
                UserManager userManager = new UserManager(username);
                return userManager.getUser();
            }
        }
        throw new UserNotFoundException("User " + username + " was not found!");
    }

    public boolean isPasswordCorrect(String username, String password) {
        if(isUsernameTaken(username)) {
            JsonArray jsonArray = (JsonArray) new FileManager().parseJson("users.json");
            for(int x = 0; x < jsonArray.size(); x++) {
                JsonObject jsonObject = jsonArray.get(x).getAsJsonObject();
                if(jsonObject.get("username").getAsString().equals(username)) {
                    if(jsonObject.get("password").getAsString().equals(password)) {
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    public boolean doesDataMatch(String email, String username, String password) {
        /*
        The reason I don't use isUsernameTaken() is because isPasswordCorrect(String username, String password)
        already checks for that and would throw that if the username doesn't exist.
        */
        if (isEmailInUse(email) && isPasswordCorrect(username, password)) {
            JsonArray jsonArray = (JsonArray) new FileManager().parseJson("users.json");
            for (int x = 0; x < jsonArray.size(); x++) {
                JsonObject jsonObject = jsonArray.get(x).getAsJsonObject();
                if (jsonObject.get("email").getAsString().equals(email) && jsonObject.get("username").getAsString().equals(username) &&
                        jsonObject.get("password").getAsString().equals(username)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public boolean doesDataMatch(Module format) {
        return doesDataMatch((String) format.getValue("email"), (String) format.getValue("username"), (String) format.getValue("password"));
    }
}