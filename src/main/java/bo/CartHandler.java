package bo;

import ui.ItemInfo;
import java.util.*;

public final class CartHandler {
    private CartHandler() {}

    public static boolean add(User user, ItemInfo item) {
        return Cart.addItem(user, item);
    }

    public static boolean remove(User user, ItemInfo item) {
        return Cart.removeItem(user, item);
    }

    public static boolean setQty(User user, int itemId, int qty) {
        return Cart.setQty(user, itemId, qty);
    }

    public static boolean clear(User user) {
        return Cart.clear(user);
    }

    public static List<ItemInfo> listItems(User user) {
        return UserHandler.listUserItems(user);
    }

    public static Map<Integer, Integer> quantities(User user) {
        return Cart.quantities(user);
    }

    public static double totalPrice(User user) {
        return Cart.totalPrice(user);
    }
}
