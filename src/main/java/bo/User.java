package bo;

import db.UserDB;
import ui.ItemInfo;

import java.util.List;

public class User {
    private final String username;
    private final int userID;
    private final AccessType accessType;

    public User(String username, int accessTypeOrdinal, int userID) {
        this.username = username;
        this.accessType = AccessType.values()[accessTypeOrdinal];
        this.userID = userID;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public String getUsername() {
        return username;
    }

    public int getUserID() {
        return userID;
    }

    public static List<ItemInfo> listUserItems(User user) {
        return UserDB.listUserItems(user);
    }

    public static User userExists(String username, String password) {
        return UserDB.userExists(username, password);
    }

}