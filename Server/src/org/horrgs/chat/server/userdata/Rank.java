package org.horrgs.chat.server.userdata;

/**
 * Created by Horrgs on 11/22/2015.
 */
public enum Rank {
    USER("USER"),
    ADMIN("ADMINISTRATOR");

    private String name;
    private Rank(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Rank getByName(String name) {
        for(Rank rank : Rank.values()) {
            if(rank.getName().equals(name)) {
                return rank;
            }
        }
        return null;
    }
}
