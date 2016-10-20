package model;

/**
 * Created by shuorenwang on 2016-10-16.
 */
public class User {
    private int userID;
    private String name;
    private String userName;
    private String password;

    public User(int userID, String name, String userName, String password) {
        this.userID = userID;
        this.name = name;
        this.userName = userName;
        this.password = password;
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
}
