
public class BuyableClothing extends Buyable {

    private String size;
    
    public BuyableClothing(double price, String name, String size) {
        super(price, name, "Clothing");
        this.size = size;
    }

    public String getSize() {
        return size;
    }
    
}
