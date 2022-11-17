
public class BuyableFood extends Buyable {

    private double weight;
    
    public BuyableFood(double price, String name, double weight) {
        super(price, name, "Food");
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }
    
}
