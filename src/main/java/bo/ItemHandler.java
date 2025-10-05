package bo;

import ui.ItemInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ItemHandler {
    public static Collection<ItemInfo> getAllItems() {
        Collection c = Item.searchAllItems();

        ArrayList<ItemInfo> items = new ArrayList<ItemInfo>();
        for(Iterator it = c.iterator(); it.hasNext();) {
            Item item = (Item) it.next();
            items.add(new ItemInfo(item.getName(), item.getDescription(), item.getItemID(), item.getStock(), item.getPrice()));
        }
        return items;
    }
}
