package org.horrgs.chat.server;

import com.google.gson.*;
import org.horrgs.chat.server.jsonformat.ErrorFormat;

import java.io.*;

/**
 * Created by Horrgs on 11/22/2015.
 */
public class FileManager {
    private File users;
    private File errors;
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
        errors = new File("errors.json");
        if(!errors.exists()) {
            try {
                errors.createNewFile();
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(errors));
                JsonArray jsonArray = new JsonArray();
                bufferedWriter.write(jsonArray.toString());
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void logError(ErrorFormat errorFormat) {
        JsonArray jsonArray = (JsonArray) parseJson(errors);
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(errors));
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        if(doesErrorExist(errorFormat.getError())) {
            if(hasVersion(errorFormat.getVersion())) {
                for(int x = 0; x < jsonArray.size(); x++) {
                    JsonObject jsonObject1 = jsonArray.get(x).getAsJsonObject();
                    if(jsonObject1.get("occurrence") != null) {
                        jsonObject1.addProperty("occurrence", jsonObject1.get("occurrence").getAsLong() + 1);
                        try {
                            bufferedWriter.write(jsonArray.toString());
                            bufferedWriter.flush();
                            bufferedWriter.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        } else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("error", errorFormat.getError());
            JsonArray jsonArray1 = new JsonArray();
            jsonArray1.add(new JsonPrimitive(errorFormat.getVersion()));
            jsonObject.add("versions", jsonArray1);
            jsonObject.addProperty("occurrence", 1);
            jsonArray.add(jsonObject);
            try {
                bufferedWriter.write(jsonArray.toString());
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean doesErrorExist(String error) {
        JsonArray jsonArray = (JsonArray) parseJson(errors);
        for (int x = 0; x < jsonArray.size(); x++) {
            if (jsonArray.get(x).getAsJsonObject().get("error").getAsString().equals(error)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasVersion(long version) {
        JsonArray jsonArray = (JsonArray) parseJson(errors);
        for (int x = 0; x < jsonArray.size(); x++) {
            for (int i = 0; i < jsonArray.get(x).getAsJsonObject().get("versions").getAsJsonArray().size(); i++) {
                if(jsonArray.get(x).getAsJsonObject().get("versions").getAsJsonArray().get(i).getAsLong() == version) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * TODO: Soon to replace all the repitive code that parses all the files.
     * @param fileName - the name of the json file you want to parse.
     * @return the json you want to access, cast it to a com.google.gson.JsonArray or com.google.gson.JsonObject
     */
    public JsonElement parseJson(String fileName) {
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = null;
        try {
            if(new File(fileName).exists()) {
                Object obj = jsonParser.parse(new FileReader(new File(fileName)));
                jsonElement  = (JsonElement) obj;
                System.out.println("Parsing " + fileName + ".json ....");
            } else {
                throw new FileNotFoundException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return jsonElement;
    }

    public JsonElement parseJson(File file) {
        return parseJson(file.getName());
    }
}
