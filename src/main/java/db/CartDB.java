package db;

import ui.ItemInfo;
import bo.User;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * CartDB — handles all SQL operations for the shopping cart.
 * Features: add/remove items, clear cart, update item stock,
 * and retrieve quantities for a specific user.
 * Uses shared JDBC {@link Connection} from {@link DBManager}.
 */

public class CartDB {

    private static final String updateStockQuery = "UPDATE ITEM SET stock = stock + ? WHERE itemID = ?";

    /**
     * Adds an item to the user's cart. Increases quantity if already present.
     *
     * @param user the user whose cart is updated
     * @param item the item to add
     * @return true if the operation succeeded, false otherwise
     */
    public static boolean addItem(User user, ItemInfo item) {
        if (item == null || user == null) return false;
        int userID = user.getUserID();
        int itemID = item.getId();
        if (userID < 1 || itemID < 1) return false;

        String addItemQuery = "UPDATE CART SET amountOfItems = amountOfItems + 1 WHERE userID = ? AND itemID = ?";
        String addNewItemQuery = "INSERT INTO CART (userID, itemID, amountOfItems) VALUES (?, ?, 1)";

        try {
            Connection con = DBManager.getConnection();
            try (PreparedStatement ps = con.prepareStatement(addItemQuery)) {
                ps.setInt(1, userID);
                ps.setInt(2, itemID);
                int changed = ps.executeUpdate();
                if (changed > 0) return updateStock(con, -1, itemID);
            }
            try (PreparedStatement ps = con.prepareStatement(addNewItemQuery)) {
                ps.setInt(1, userID);
                ps.setInt(2, itemID);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("addItem failed", e);
        }
    }

    private static boolean updateStock(Connection con, int changedAmount, int itemID) throws SQLException {
        try(PreparedStatement psUpdateStock = con.prepareStatement(updateStockQuery)) {
            psUpdateStock.setInt(1, changedAmount);
            psUpdateStock.setInt(2, itemID);
            if(psUpdateStock.executeUpdate() > 0) return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    /**
     * Removes one unit of an item from the user's cart.
     * If quantity is 1, removes the item entirely.
     *
     * @param item the item to remove
     * @param user the user whose cart to update
     * @return true if the operation succeeded, false otherwise
     */
    public static boolean removeItem(User user, ItemInfo item) {
        if (item == null || user == null) return false;
        int userID = user.getUserID();
        int itemID = item.getId();
        if (userID < 1 || itemID < 1) return false;

        String decreaseQtyQuery = "UPDATE CART SET amountOfItems = amountOfItems - 1 " +
                "WHERE userID = ? AND itemID = ? AND amountOfItems > 1";
        String deleteFromCartQuery = "DELETE FROM CART WHERE userID = ? AND itemID = ? AND amountOfItems <= 1";

        try {
            Connection con = DBManager.getConnection();
            try (PreparedStatement ps = con.prepareStatement(decreaseQtyQuery)) {
                ps.setInt(1, userID);
                ps.setInt(2, itemID);
                int changed = ps.executeUpdate();
                if (changed > 0) return true;
            }
            try (PreparedStatement ps = con.prepareStatement(deleteFromCartQuery)) {
                ps.setInt(1, userID);
                ps.setInt(2, itemID);
                int deleted = ps.executeUpdate();
                return deleted > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("removeItem failed", e);
        }
    }
    /**
     * Calculates the total price of all items in the user's cart.
     *
     * @param user the user whose cart total is calculated
     * @return total price of items in the cart
     */
    public static double totalPrice(User user) {
        if (user == null) return 0.0;
        String cartTotal = """
            SELECT COALESCE(SUM(i.price * c.amountOfItems), 0) AS total
            FROM CART c
            JOIN ITEM i ON i.itemID = c.itemID
            WHERE c.userID = ?
        """;
        try {
            Connection con = DBManager.getConnection();
            try (PreparedStatement ps = con.prepareStatement(cartTotal)) {
                ps.setInt(1, user.getUserID());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) return rs.getDouble("total");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("totalPrice failed", e);
        }
        return 0.0;
    }
    /**
     * Retrieves a map of item IDs and their corresponding quantities in the user's cart.
     *
     * @param user the user whose cart quantities are retrieved
     * @return a map of item IDs to quantities
     */
    public static Map<Integer, Integer> quantities(User user) {
        Map<Integer, Integer> map = new HashMap<>();
        if (user == null) return map;

        String sql = "SELECT itemID, amountOfItems FROM CART WHERE userID = ?";
        try {
            Connection con = DBManager.getConnection();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, user.getUserID());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        map.put(rs.getInt("itemID"), rs.getInt("amountOfItems"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("quantities failed", e);
        }
        return map;
    }
    /**
     * Sets the quantity of a specific item in the user's cart.
     * Deletes the item if quantity is zero or less.
     *
     * @param user the user whose cart is updated
     * @param itemId the ID of the item
     * @param qty the new quantity
     * @return true if the operation succeeded, false otherwise
     */
    public static boolean setQty(User user, int itemId, int qty) {
        if (user == null || itemId < 1) return false;
        String sql = (qty <= 0)
                ? "DELETE FROM CART WHERE userID = ? AND itemID = ?"
                : "UPDATE CART SET amountOfItems = ? WHERE userID = ? AND itemID = ?";
        try {
            Connection con = DBManager.getConnection();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                int idx = 1;
                if (qty > 0) ps.setInt(idx++, qty);
                ps.setInt(idx++, user.getUserID());
                ps.setInt(idx, itemId);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("setQty failed", e);
        }
    }

    /**
     * Clears all items from the user's cart.
     *
     * @param user the user whose cart to clear
     * @return true if the cart was cleared successfully, false otherwise
     */
    public static boolean clear(User user) {
        if (user == null) return false;
        String sql = "DELETE FROM CART WHERE userID = ?";
        try {
            Connection con = DBManager.getConnection();
            try(PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, user.getUserID());
                ps.executeUpdate();
                return true;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("clear cart failed", e);
        }
    }
}
