
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Store {
    Scanner scan = new Scanner(System.in);

    // User data variables
    private BankAccount myBankAccount;
    private ArrayList<Buyable> myStuff;
    private ArrayList<Buyable> myShoppingCart;

    // Store data variables
    private StoreInventory storeInventory;

    public Store()
    {
        System.out.println("Welcome to my storefont!");
        setupAccounts();
        setupStore();
        presentShoppingMenu();
    }

    private void setupAccounts()
    {
        setupBankAccount();
        myStuff = new ArrayList<Buyable>();
        myShoppingCart = new ArrayList<Buyable>();
    }

    private void setupStore()
    {
        storeInventory = new StoreInventory();
    }

    private void setupBankAccount()
    {
        boolean setup = true;
        System.out.println("To begin, please set up a bank account.");
        while (setup){
            try{
                System.out.println("How much money should your account contain?");
                int depositAmount = scan.nextInt();
                myBankAccount = new BankAccount(depositAmount);
                setup = false;
            }
            catch (InputMismatchException exception){
                scan.nextLine();
                System.out.println("Invalid Input! Try Again.");
            }
        }
    }

    private void presentShoppingMenu()
    {
        boolean stillShopping = true;
        while(stillShopping)
        {
            try{
                System.out.println("\n****************************************************** ");
                System.out.println("Please choose from one of the following menu options: ");
                System.out.println("1. View catalog of items to buy");
                System.out.println("2. Buy an item");
                System.out.println("3. View your cart of held+ items");
                System.out.println("4. Review the items you already own");
                System.out.println("5. View the status of your financials");
                System.out.println("6. YOUR CUSTOM IDEA HERE??");
                System.out.println("7. Exit program");
                int input = scan.nextInt();
                scan.nextLine(); // buffer clear
                switch (input) {
                    case 1 -> viewCatalog();
                    case 2 -> buyItem();
                    case 3 -> reviewMyShoppingCart();
                    case 4 -> reviewMyInventory();
                    case 5 -> reviewFinancials();
                    case 6 -> System.out.println("YOUR CONTENT HERE! :) :)");
                    case 7 -> {
                        System.out.println("Thanks for shopping! Now exiting program ... ");
                        System.exit(0);
                    }
                    default -> System.out.println("Incorrect input. Choose again!");
                }
            }
            catch (InputMismatchException exception){
                scan.nextLine(); // buffer clear
                System.out.println("Invalid Input! Try Again.");
            }

        }
    }

    private void viewCatalog()
    {
        System.out.println("What are you looking for?");

        System.out.println("1. Clothing");
        System.out.println("2. Food");
        System.out.println("3. Game");
        System.out.println("4. Furniture");
        int input = scan.nextInt();
        if (input == 1){
            for(Buyable item: storeInventory.getClothesList())
            {
                System.out.println("" + item.getItemName());
            }
        }
        else if (input == 2){
            for(Buyable item: storeInventory.getFoodList()){
                System.out.println("" + item.getItemName());
            }
        }
        else if (input == 3){
            for(Buyable item: storeInventory.getGamesList()){
                System.out.println("" + item.getItemName());
            }
        }
        else if (input == 4){
            for(Buyable item: storeInventory.getFurnitureList()){
                System.out.println("" + item.getItemName());
            }
        }
        // Retrieve the master list from the store inventory and examine each entry individually

    }

    private void buyItem()
    {
        System.out.println("Please type in the name of the item you wish to buy!");

        // Get user input
        String itemName = scan.nextLine();

        // Holding variable for the desired item, if found
        Buyable itemToBuy = null;

        // Look through the full inventory to see if the item is present
        // Convert both item name and user input to lower case to prevent case issues!
        for(Buyable item: storeInventory.getFullInventoryList())
        {
            if(item.getItemName().toLowerCase().equals(itemName.toLowerCase()))
            {
                itemToBuy = item;
                break;
            }
        }

        // If a suitable item was found, give them the option to buy it!
        if(itemToBuy != null)
        {
            System.out.println("We have " + itemToBuy.getItemName() + " in stock!");
            System.out.println("Type 1 to BUY NOW or 2 to PLACE IN YOUR SHOPPING CART.");

            int input = scan.nextInt();
            scan.nextLine(); // buffer clear

            if(input == 1)
            {
                makePurchaseFromStore(itemToBuy);
            }
            else if(input == 2)
            {
                System.out.println("We'll hold onto this item for you.");
                moveItemToShoppingCart(itemToBuy);
            }
            else
            {
                System.out.println("Incorrect input. Purchase cancelled.");
            }

        }
        else // No suitable item found
        {
            System.out.println("The item you are looking for is sold out or does not exist, sorry!");
        }

    }


    private void reviewMyInventory()
    {
        System.out.println("Here is a list of the items you now own: ");
        for(int i = 0; i < myStuff.size(); i++)
        {
            System.out.println(" " + myStuff.get(i).getItemName());
        }
    }

    private void reviewFinancials()
    {
        myBankAccount.balanceReport();
    }


    // SHOPPING CART METHODS
    private void reviewMyShoppingCart()
    {
        if(!myShoppingCart.isEmpty())
        {
            System.out.println("Here are all of the items being held in your shopping cart: ");
            for(Buyable item: myShoppingCart)
            {
                System.out.println(item.getItemName());
            }

            System.out.println("Would you like to see the details of your items in your shopping cart? 1 for YES or any other key for NO");

            String input = scan.nextLine();

            if (input.equals("1")){
                reviewItems();
            }

            System.out.println("Would you like to purchase any held items now? 1 for YES or any other key for NO");

            String userInput = scan.nextLine();

            if(userInput.equals("1"))
            {
                buyItemInShoppingCart();
            }
            else
            {
                System.out.println("Leaving shopping cart as is and returning to the storefront ... ");
            }
        }
        else
        {
            System.out.println("Your shopping cart is empty! Nothing to see here ... ");
        }

    }

    private void reviewItems(){
        //asks for which item to see
        System.out.println("Type in the name of the item you want review the details of.");

        String userChoice = scan.nextLine();
        // checks if user has item in the shopping cart
        for(Buyable cartItem: myShoppingCart){

            if(cartItem.getItemName().toLowerCase().equals(userChoice.toLowerCase())){
                if(cartItem.getItemCategory().equals("Clothing")){
                    System.out.println(cartItem.getItemCategory()+ " | " + cartItem.getItemName() + " | " + "$" + cartItem.getPrice());
                }
                else if(cartItem.getItemCategory().equals("Food")){

                }
                else if(cartItem.getItemCategory().equals("Furniture")){

                }
            }
        }

    }


    private void buyItemInShoppingCart()
    {
        System.out.println("Type in the name of the item you want to buy from the shopping cart: ");
        String userChoice = scan.nextLine();

        for(Buyable itemInCart: myShoppingCart)
        {
            if(itemInCart.getItemName().toLowerCase().equals(userChoice.toLowerCase()))
            {
                makePurchaseFromShoppingCart(itemInCart);
                break;
            }
            else
            {
                System.out.println("Item could not be found in shopping cart.");
            }
        }

    }

    private void removeItemFromShoppingCart(Buyable item)
    {
        System.out.println("Which item would you like to remove from your shopping cart?");

        String userChoice = scan.nextLine();

        for(Buyable cartItem: myShoppingCart)
        {
            if(cartItem.getItemName().toLowerCase().equals(userChoice.toLowerCase()))
            {
                System.out.println("You have removed " + cartItem.getItemName() + " from your shopping cart.");
                moveItemFromShoppingCartToInventory(item);
            }
            else
            {
                System.out.println("Item could not be found in your shopping cart.");
            }
        }
    }

    // Move item from inventory to shopping cart
    private void moveItemToShoppingCart(Buyable item)
    {
        myShoppingCart.add(item);
        storeInventory.removeItemFromInventory(item);
    }

    private void moveItemFromShoppingCartToInventory(Buyable item)
    {
        storeInventory.restockItemToInventory(item);
        myShoppingCart.remove(item);
    }


    private void makePurchaseFromStore(Buyable item)
    {
        // If you can afford the item, buy it and remove it from the store
        if(myBankAccount.canAfford(item.getPrice()))
        {
            if(myBankAccount.checkPassword()){
                myBankAccount.makePurchase(item.getPrice());
                System.out.println("Purchase complete! You now own " + item.getItemName());
                myStuff.add(item);
                storeInventory.removeItemFromInventory(item);
            }
        }
        else
        {
            System.out.println("You can't afford that item ... ");
        }
    }

    private void makePurchaseFromShoppingCart(Buyable item)
    {
        // If you can afford the item, buy it and remove it from the store
        if(myBankAccount.canAfford(item.getPrice()))
        {
            if(myBankAccount.checkPassword()) {
                myBankAccount.makePurchase(item.getPrice());
                System.out.println("Purchase complete! You now own " + item.getItemName());
                myStuff.add(item);
                myShoppingCart.remove(item);
            }
        }
        else
        {
            System.out.println("You can't afford that item ... ");
        }
    }
}
