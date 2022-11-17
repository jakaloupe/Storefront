
import java.util.Scanner;

public class BankAccount {
    
    Scanner scan = new Scanner(System.in);
    
    private String password;
    private float balance;

    public BankAccount(float initialDeposit)
    {
        balance = initialDeposit;
        setPassword();
    }
    
    // Ask the user to set password, requiring two successful entries to lock it in permanently
    private void setPassword()
    {
        System.out.println("Please enter a password for your account: ");
        password = scan.nextLine();
        System.out.println("Confirm your password by entering it one more time!");
        String tempPassCheck = scan.nextLine();
        if(!password.equals(tempPassCheck))
        {
            System.out.println("Your passwords do not match ... ");
            setPassword();
        }
        else
        {
            System.out.println("Password set! Your account is now ready with a balance of " + balance);
        }
    }
    
    public boolean canAfford(double amount)
    {
        if(amount <= balance)
        {
            return true;
        }
        else
        {
            System.out.println("You don't have enough money in your account.");
            return false;
        }            
    }
    
    // Allow the user to attempt to withdraw money and report on success. If successful, adjust balance.
    public boolean makePurchase(double amount)
    {
        if(checkPassword())
        {
            if(amount <= balance)
            {
                balance-=amount;
                System.out.println("" + amount + " spent from your account");
                System.out.println("You have " + balance + " remaining.");
                return true;
            }
            else
            {
                System.out.println("Not enough remaining funds");
                return false;
            }
        }
        else
        {
            return false; // fail to withdraw due to bad password
        }
    }
    
    public void depositMoney(double amount)
    {
        if(checkPassword())
        {
            balance+=amount;
            System.out.println("You added " + amount + " to your account.");
            System.out.println("You now have $" + balance + " available.");            
        }
    }
    
    public void balanceReport()
    {
        System.out.println("You have " + balance + " left in your account.");
    }
    
    private boolean checkPassword()
    {
        System.out.println("Please enter your password to access account: ");
        String passEntry = scan.nextLine();
        if(passEntry.equals(password))
        {
            return true;
        }
        else
        {
            System.out.println("Incorrect password!");
            return false;
        }
    }

}
