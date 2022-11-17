
public class BuyableGame extends Buyable {

    private int numPlayers;
    private String genre;
    
    public BuyableGame(double price, String name, int numPlayers, String genre) {
        super(price, name, "Game");
        this.numPlayers = numPlayers;
        this.genre = genre;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
    
    public String getGenre() {
        return genre;
    }
    
}
