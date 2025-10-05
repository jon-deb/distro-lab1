package bo;

import db.ItemDB;
import java.util.Collection;

public class Item {
    private String name;
    private String descr;
    private int id;
    private int stock;
    private double price;

    protected Item (int itemID, String name, String desc, int stock, double price){
        this.id = itemID;
        this.name = name;
        this.descr = desc;
        this.stock = stock;
        this.price = price;
    }

    /*static public Collection searchItemsByDescription(String group){
        return ItemDB.searchItemsByDescription(group);
    }*/

    static public Collection searchAllItems() {
        return ItemDB.searchAllItems();
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItemID() {
        return id;
    }

    public String getDescription() {
        return descr;
    }

    public int getStock() {
        return this.stock;
    }

    public double getPrice() {
        return this.price;
    }
}
