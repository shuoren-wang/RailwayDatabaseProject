package model;

/**
 * Created by shuorenwang on 2016-10-16.
 */
public class User {
    private int userID;
    private String name;
    private String userName;
    private String password;
    private boolean active;

    public User(int userID, String name, String userName, String password,boolean active) {
        this.userID = userID;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.active=active;
    }

    //Used for test interface
    public User(){
        userID = 0;
        name = "name";
        userName = "userName";
        password = "password";
        active=true;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(boolean active) { this.active = active; }
}
