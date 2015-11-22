package org.horrgs.chat.server;

import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Horrgs on 11/22/2015.
 */
public class FileManager {
    private File users;
    public void setup() {
        users = new File("users.json");
        if(!users.exists()) {
            try {
                users.createNewFile();
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(users));
                JsonObject jsonObject = new JsonObject();
                bufferedWriter.write(jsonObject.toString());
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
