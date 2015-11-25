package org.horrgs.chat.server;

import org.horrgs.chat.server.windows.Console;

/**
 * Created by Horrgs on 11/21/2015.
 */
public class Chat {

    public static void main(String[] args) {
        new FileManager().setup();
        Console.getInstance().openWindow();
    }
}