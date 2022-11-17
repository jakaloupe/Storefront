
public class Buyable {
    
    // Sellable generic variables
    private double price;
    private String itemName;
    private String itemCategory;
    
    public Buyable(double price, String name, String category)
    {
        this.price = price;
        itemName = name;
        itemCategory = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

}
