package org.horrgs.chat.server.jsonformat;

/**
 * Created by Horrgs on 11/21/2015.
 */
public enum RequestType {
    CREATE_ACCOUNT("CREATE_ACCOUNT"),
    LOGIN("LOGIN"),
    SUCCESSION("SUCCESSION"),
    ERROR("ERROR");
    private String name;
    private RequestType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public RequestType getByName(String name) {
        for(RequestType rank : RequestType.values()) {
            if(rank.getName().equals(name)) {
                return rank;
            }
        }
        return null;
    }
}
