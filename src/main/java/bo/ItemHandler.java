package bo;

import db.ItemDB;
import ui.ItemInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * ItemHandler provides methods to retrieve item information.
 */
public class ItemHandler {
    /**
     * Retrieves all items from the database and converts them into ItemInfo objects.
     *
     * @return a collection of ItemInfo objects representing all items
     */
    public static Collection<ItemInfo> getAllItems() {
        Collection c = ItemDB.searchAllItems();

        ArrayList<ItemInfo> items = new ArrayList<ItemInfo>();
        for(Iterator it = c.iterator(); it.hasNext();) {
            Item item = (Item) it.next();
            items.add(new ItemInfo(item.getName(), item.getDescription(), item.getItemID(), item.getStock(), item.getPrice()));
        }
        return items;
    }
}
