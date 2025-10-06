package bo;

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

}