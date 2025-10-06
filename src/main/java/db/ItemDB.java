package db;

import bo.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Vector;

/**
 * ItemDB — handles SQL queries for retrieving items from the ITEM table.
 * Provides: searching all items.
 */
public class ItemDB extends Item {

    private ItemDB(int itemID, String name, String desc, int stock, double price) {
        super(itemID, name, desc, stock, price);
    }
    /**
     * Retrieves all items from the ITEM table in the database.
     *
     * @return a Collection containing all items as ItemDB objects
     */
    public static Collection searchAllItems() {
        Vector v = new Vector();
        try {
            Connection con = DBManager.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select itemID, name, description, stock, price from ITEM");

            while (rs.next()) {
                int i = rs.getInt("itemID");
                String name = rs.getString("name");
                String desc = rs.getString("description");
                int stock = rs.getInt("stock");
                double price = rs.getDouble("price");
                v.addElement(new ItemDB(i, name, desc, stock, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }
    /*public static Collection searchItemsByDescription(String description) {
        Vector v = new Vector();
        try {
            Connection con = DBManager.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select itemID, name, description, stock, price from ITEM");

            while (rs.next()) {
                int i = rs.getInt("itemID");
                String name = rs.getString("name");
                String desc = rs.getString("description");
                int stock = rs.getInt("stock");
                double price = rs.getDouble("price");
                v.addElement(new ItemDB(i, name, desc, stock, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }*/

    /*public static Collection searchItemsByName(String nameQuery) {
        Vector v = new Vector();
        try {
            Connection con = DBManager.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT name FROM ITEM WHERE name = 'nameQuery'");

            while (rs.next()) {
                int i = rs.getInt("itemID");
                String name = rs.getString("name");
                String desc = rs.getString("description");
                int stock = rs.getInt("stock");
                double price = rs.getDouble("price");
                v.addElement(new ItemDB(i, name, desc, stock, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }*/
}
