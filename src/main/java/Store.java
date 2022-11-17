
import java.util.ArrayList;
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
        System.out.println("To begin, please set up a bank account.");
        System.out.println("How much money should your account contain?");
        int depositAmount = scan.nextInt();
        myBankAccount = new BankAccount(depositAmount);
    }
    
    private void presentShoppingMenu()
    {
        boolean stillShopping = true;
        while(stillShopping)
        {
            System.out.println("\n****************************************************** ");
            System.out.println("Please choose from one of the following menu options: ");
            System.out.println("1. View catalog of items to buy");
            System.out.println("2. Buy an item");
            System.out.println("3. View your cart of held items");
            System.out.println("4. Review the items you already own");
            System.out.println("5. View the status of your financials");
            System.out.println("6. YOUR CUSTOM IDEA HERE??");
            System.out.println("7. Exit program");
            
            int input = scan.nextInt();
            scan.nextLine(); // buffer clear
            
            switch(input){
                case 1:
                    viewCatalog();
                    break;
                case 2:
                    buyItem();
                    break;
                case 3:
                    reviewMyShoppingCart();
                    break;
                case 4:
                    reviewMyInventory();
                    break;
                case 5:
                    reviewFinancials();
                    break;
                case 6:
                    System.out.println("YOUR CONTENT HERE! :) :)");
                    break;
                case 7:
                    System.out.println("Thanks for shopping! Now exiting program ... ");
                    System.exit(0);                    
                    break;
                default:
                    System.out.println("Incorrect input. Choose again!");
                    break;
            }
            
        }
    }
    
    private void viewCatalog()
    {
        System.out.println("Here is a list of all the items currently for sale!");
        
        // Retrieve the master list from the store inventory and examine each entry individually
        for(Buyable item: storeInventory.getFullInventoryList()) 
        {
            System.out.println("" + item.getItemName());
        }
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
            System.out.println("" + myStuff.get(i).getItemName());
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
                 System.out.println("" + item.getItemName());
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
            myBankAccount.makePurchase(item.getPrice());
            System.out.println("Purchase complete! You now own " + item.getItemName());
            myStuff.add(item);
            storeInventory.removeItemFromInventory(item);
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
            myBankAccount.makePurchase(item.getPrice());
            System.out.println("Purchase complete! You now own " + item.getItemName());
            myStuff.add(item);
            myShoppingCart.remove(item);
        }
        else
        {
            System.out.println("You can't afford that item ... ");
        }        
    }
}
