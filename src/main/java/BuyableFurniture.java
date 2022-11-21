
public class BuyableFurniture extends Buyable{

    private String type;
    public BuyableFurniture(double price, String name, String type) {
        super(price, name, "Furniture");
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
