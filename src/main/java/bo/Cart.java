package bo;

import db.CartDB;
import ui.ItemInfo;

import java.util.Map;

class Cart {

    Cart() {
    }

    public static boolean setQty(User user, int itemId, int qty) {
        return CartDB.setQty(user, itemId, qty);
    }

    public static Map<Integer, Integer> quantities(User user) {
        return CartDB.quantities(user);
    }

    public static double totalPrice(User user) {
        return CartDB.totalPrice(user);
    }

    public static boolean addItem(User user, ItemInfo item) {
        return CartDB.addItem(user, item);
    }

    public static boolean removeItem(User user, ItemInfo item) {
        return CartDB.removeItem(item, user);
    }

    public static boolean clear(User user) {
        return CartDB.clear(user);
    }

}
