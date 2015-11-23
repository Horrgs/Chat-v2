package org.horrgs.chat.server.userdata;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.horrgs.chat.server.exceptions.DataInUseException;
import org.horrgs.chat.server.exceptions.UserNotFoundException;
import org.horrgs.chat.server.jsonformat.CreateAccountFormat;
import org.horrgs.chat.server.jsonformat.LoginFormat;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Horrgs on 11/21/2015.
 */
public class UserManager extends User {
    private List<User> usersOnline = new ArrayList<>();
    private boolean online;
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

        JsonArray jsonObject = new JsonArray();
        JsonParser jsonParser = new JsonParser();
        try {
            Object obj = jsonParser.parse(new FileReader("users.json"));
            jsonObject = (JsonArray) obj;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            JsonObject email = new JsonObject();
            jsonObject.add(email);
            email.addProperty("email", createAccountFormat.getEmail());
            email.addProperty("username", createAccountFormat.getUsername());
            email.addProperty("password", createAccountFormat.getPassword());
            //email.addProperty("status", Status.ONLINE.getId());
            email.addProperty("rank", Rank.USER.getName());
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("users.json"));
            bufferedWriter.write(jsonObject.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
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

    public List<User> getUsersOnline() {
        return usersOnline;
    }
    public User getUser(String username) throws UserNotFoundException {
        for(User user : getUsersOnline()) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = null;
        try {
            Object obj = jsonParser.parse(new FileReader("secrets.json"));
            jsonArray = (JsonArray) obj;
            System.out.println("Parsing secrets.json ....");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
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
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = null;
            try {
                Object obj = jsonParser.parse(new FileReader("secrets.json"));
                jsonArray = (JsonArray) obj;
                System.out.println("Parsing secrets.json ....");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                return false;
            }
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
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = null;
            try {
                Object obj = jsonParser.parse(new FileReader("secrets.json"));
                jsonArray = (JsonArray) obj;
                System.out.println("Parsing secrets.json ....");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                return false;
            }
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

    public boolean doesDataMatch(CreateAccountFormat createAccountFormat) {
        return doesDataMatch(createAccountFormat.getEmail(), createAccountFormat.getUsername(), createAccountFormat.getPassword());
    }

    public boolean doesDataMatch(LoginFormat loginFormat) {
        return doesDataMatch(loginFormat.getEmail(), loginFormat.getUsername(), loginFormat.getPassword());
    }

}
