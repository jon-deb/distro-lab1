package bo;

import ui.ItemInfo;

import java.util.List;

public class UserHandler {
    public static User userExists(String username, String password) {
        return User.userExists(username, password);
    }

    public static List<ItemInfo> listUserItems(User user) {
        return User.listUserItems(user);
    }
}
