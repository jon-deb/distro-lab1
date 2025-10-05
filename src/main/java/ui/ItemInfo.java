package ui;
/**
 * ItemInfo — simple DTO for presenting item data in the UI layer.
 * Contains name, description, id, stock, and price.
 */
public class ItemInfo {
    private String name;
    private String description;
    private int id;
    private int stock;
    private double price;

    public ItemInfo(String name, String description, int id, int stock, double price) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.stock = stock;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}