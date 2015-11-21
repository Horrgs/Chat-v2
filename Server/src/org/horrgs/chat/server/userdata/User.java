package org.horrgs.chat.server.userdata;

/**
 * Created by Horrgs on 11/21/2015.
 */
public interface User {
    public String getUsername();
    public String getPassword();
    public String getEmail();
    //public Rank getRank(); //TODO: rank.
    public boolean isOnline();
    //public Status getStatus();//TODO: status.
    public void setUsername(String username);
    public void setPassword(String password);
    public void setEmail(String email);
    //public void setRank(Rank rank);//TODO: rank
    public void setOnline(boolean online);
    //public void setStatus(Status status); //TODO: status
}
