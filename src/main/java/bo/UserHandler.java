package bo;

import db.UserDB;
import ui.ItemInfo;

import java.util.List;

/**
 * UserHandler provides static methods to manage user-related operations.
 */
public class UserHandler {
    /**
     * Checks if a user exists with the given username and password.
     *
     * @param username the user's username
     * @param password the user's password
     * @return a User object if the user exists; null otherwise
     */
    public static User userExists(String username, String password) {
        return UserDB.userExists(username, password);
    }

    /**
     * Retrieves a list of items in the user's cart.
     *
     * @param user the user
     * @return a list of ItemInfo objects representing the user's cart items
     */
    public static List<ItemInfo> listUserItems(User user) {
        return UserDB.listUserItems(user);
    }
}
