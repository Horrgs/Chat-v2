package org.horrgs.chat.server;

import com.google.gson.Gson;
import org.horrgs.chat.server.exceptions.DataInUseException;
import org.horrgs.chat.server.exceptions.FormatKeysException;
import org.horrgs.chat.server.jsonformat.CreateAccountFormat;
import org.horrgs.chat.server.jsonformat.ErrorFormat;
import org.horrgs.chat.server.jsonformat.RequestType;
import org.horrgs.chat.server.userdata.UserManager;
import org.horrgs.chat.server.windows.Console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Horrgs on 11/22/2015.
 */
public class ConnectionHandle implements Runnable {
    Console console = new Console().getInstance();
    private BufferedReader bufferedReader;
    private Socket clientSocket;
    private static ConnectionHandle instance = new ConnectionHandle();
    public static ConnectionHandle getInstance() {
        return instance;
    }
    ArrayList<PrintWriter> clientOutputStreams = new ArrayList<>();

    public ConnectionHandle() {
        super();
    }

    public ConnectionHandle(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream()); //This recieves the messsages sent to client.
            bufferedReader = new BufferedReader(inputStreamReader);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        String[] keys = new String[0];
        String receivingMessage;
        try {
            while((receivingMessage = bufferedReader.readLine()) != null) {
                console.appendConsole(receivingMessage);
                Gson gson = new Gson();
                RequestType requestType = RequestType.getByName(receivingMessage);
                switch (requestType) {
                    case CREATE_ACCOUNT:
                        CreateAccountFormat createAccountFormat = gson.fromJson(receivingMessage, CreateAccountFormat.class);
                        try {
                            UserManager userManager = new UserManager();
                            userManager.createNewUser(createAccountFormat);
                        } catch (DataInUseException ex) {
                            try {
                                if(clientSocket != null) {
                                    keys = new String[1];
                                    keys[0] = "message";
                                    PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
                                    ErrorFormat errorFormat = new ErrorFormat(ErrorFormat.getInstance(), keys, "There is already an account with that email.");
                                    printWriter.println(errorFormat.getJsonFormat());
                                    printWriter.flush();
                                }
                            } catch (FormatKeysException e) {
                                e.printStackTrace();
                            }
                            ex.printStackTrace();
                        }
                        break;
                }

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public RequestType getRequestType(String jsonFormat){
        String[] split;
        if (jsonFormat.contains(",")) {
            split = jsonFormat.split(",");
            for (String k : split) {
                if (k.contains("type")) {
                    String[] lol = k.split("\"");
                    return RequestType.getByName(lol[lol.length - 1]);
                }
            }
        } else {
            return RequestType.getByName(jsonFormat.split("\"")[3]);
        }
    }
}
