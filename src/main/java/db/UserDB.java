package db;

import bo.User;
import ui.ItemInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDB — handles SQL authentication queries for user login.
 * Verifies credentials and returns a populated {@link User} if valid, else null.
 * Uses shared connection from {@link DBManager}.
 */
public class UserDB {

    /**
     * Checks if a user exists with the given username and password.
     *
     * @param username the user's username
     * @param password the user's password
     * @return a User object if a matching user exists; null otherwise
     */
    public static User userExists(String username, String password) {
        String userLogin = "SELECT name, accessType, userID FROM USER WHERE name = ? AND password = ?";

        try {
            Connection con = DBManager.getConnection();
            try (PreparedStatement ps = con.prepareStatement(userLogin)) {

                ps.setString(1, username);
                ps.setString(2, password);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new User(
                                rs.getString("name"),
                                rs.getInt("accessType"),
                                rs.getInt("userID")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Login query failed", e);
        }

        return null;
    }
    /**
     * Retrieves all items in the given user's cart, ordered by item name.
     *
     * @param user the user whose cart items are to be listed
     * @return a list of ItemInfo objects representing the user's cart items
     */
    public static List<ItemInfo> listUserItems(User user) {
        String fetchUserItems = """
            SELECT i.itemID, i.name, i.description, i.stock, i.price
            FROM CART c
            JOIN ITEM i ON i.itemID = c.itemID
            WHERE c.userID = ?
            ORDER BY i.name
        """;
        List<ItemInfo> list = new ArrayList<>();
        try {
            Connection con = DBManager.getConnection();
            try (PreparedStatement ps = con.prepareStatement(fetchUserItems)) {
                ps.setInt(1, user.getUserID());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(new ItemInfo(
                                rs.getString("name"),
                                rs.getString("description"),
                                rs.getInt("itemID"),
                                rs.getInt("stock"),
                                rs.getDouble("price")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("listItems failed", e);
        }
        return list;
    }
}