package bo;

import db.CartDB;
import ui.ItemInfo;
import java.util.*;
/**
 * CartHandler provides a simplified interface for cart operations.
 * It delegates to the Cart and UserHandler classes to manage
 * adding/removing items, setting quantities, clearing the cart,
 * listing items, fetching quantities, and calculating total price.
 */
public class CartHandler {
    private CartHandler() {}

    /**
     * Adds an item to the user's cart.
     *
     * @param user the user
     * @param item the item to add
     * @return true if the item was successfully added
     */
    public static boolean add(User user, ItemInfo item) {
        return CartDB.addItem(user, item);
    }

    /**
     * Removes an item from the user's cart.
     *
     * @param user the user
     * @param item the item to remove
     * @return true if the item was successfully removed
     */
    public static boolean remove(User user, ItemInfo item) {
        return CartDB.removeItem(user, item);
    }

    /**
     * Sets the quantity of a specific item in the user's cart.
     *
     * @param user the user
     * @param itemId the ID of the item
     * @param qty the new quantity
     * @return true if the quantity was successfully set
     */
    public static boolean setQty(User user, int itemId, int qty) {
        return CartDB.setQty(user, itemId, qty);
    }

    /**
     * Clears all items from the user's cart.
     *
     * @param user the user
     * @return true if the cart was successfully cleared
     */
    public static boolean clear(User user) {
        return CartDB.clear(user);
    }

    /**
     * Lists all items in the user's cart.
     *
     * @param user the user
     * @return a list of ItemInfo objects in the user's cart
     */
    public static List<ItemInfo> listItems(User user) {
        return UserHandler.listUserItems(user);
    }

    /**
     * Retrieves quantities of all items in the user's cart.
     *
     * @param user the user
     * @return a map of item IDs to their quantities in the cart
     */
    public static Map<Integer, Integer> quantities(User user) {
        return CartDB.quantities(user);
    }

    /**
     * Calculates the total price of all items in the user's cart.
     *
     * @param user the user
     * @return the total price
     */
    public static double totalPrice(User user) {
        return CartDB.totalPrice(user);
    }
}
