import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BankAccount_1_2 {


    public static int count;
    public int accountNum;
    public double accountBalance;
    public String customerAccName;
    public char[] password;

    public BankAccount_1_2(int accountNum, double accountBalance, String customerName, char[] password) {
        super();
        this.accountNum = accountNum;
        this.accountBalance = accountBalance;
        this.customerAccName = customerName;
        this.password = password;
        count++;
    }

    public char[] getPassword() {
        return password;
    }

    public int getAccountNum() {
        return accountNum;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getCustomerAccName() {
        return customerAccName;
    }

    @Override
    public String toString() {
        return "BankAccount_1_2{" +
                "accountNum=" + accountNum +
                ", accountBalance=" + accountBalance +
                '}';
    }
}

class BankAccountTester {
    private static int accNumValidation(Scanner scn, String output) {
        int accNum;
        do {
            System.out.println(output);
            while (!scn.hasNextInt()) {
                System.out.println("Invalid input! Try again.");
                scn.next();
            }
            accNum = scn.nextInt();

        }
        while (accNum > 9999 || accNum < 1000);

        return accNum;
    }


    private static double moneyValidation(Scanner scanner, String message) {
        double sum;
        do {
            System.out.println(message);
            while (!scanner.hasNextDouble()) {
                System.out.println("invalid input!\nPlease enter a valid number");
                scanner.next();
            }
            sum = scanner.nextDouble();
        } while (sum < 0);

        return sum;
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);//Creates the Scanner object
        ArrayList<BankAccount_1_2> userData = new ArrayList<>();//Declaring the ArrayList
        String authUserName = "Muiz";//Authorized Username
        char[] authPassword = "Hello".toCharArray(); //Authorized password
        int customerCount = 0;//Counter for the accounts created by an admin

        char exit;//Declaration of the char Variable
        doLoop:
        //Label for the do While Loop
        do {//Start of the do loop
            System.out.println("_______________________________\n");
            System.out.println("Welcome to the InterBanking Pty");
            System.out.println("_______________________________");
            whileLoop:
            while (customerCount <= 2) {//while 2 accounts have been created by a user this will run
                System.out.println("Enter your Authorized Username: ");//Authorized User's username
                String authNameEntry = scn.nextLine();//The variable where the user can enter their username
                System.out.println("Enter your Authorized Password: ");
                char[] authPasswordEntry = scn.nextLine().toCharArray();//The variable where the user can enter their password
                if (authNameEntry.equals(authUserName) && Arrays.equals(authPassword, authPasswordEntry)) //To check if the admin has logged in successfully
                {
                    System.out.println("Enter customer Name: ");
                    String customerName = scn.nextLine();
                    System.out.println("Enter customer's Password: ");
                    char[] customerPassword = scn.nextLine().toCharArray();
                    int customerAccNum = accNumValidation(scn, "Enter customer's account number: ");
                    double initialDeposit = moneyValidation(scn, "Enter initial deposit: $");
                    if (userData.size() == 0) {
                        userData.add(new BankAccount_1_2(customerAccNum, initialDeposit, customerName, customerPassword));
                        customerCount++;
                        System.out.println("Account creation success!");

                    } else {
                        if (userData.get(0).getAccountNum() != customerAccNum) {
                            userData.add(new BankAccount_1_2(customerAccNum, initialDeposit, customerName, customerPassword));
                            customerCount++;
                            System.out.println("Account creation success!");
                            break;
                        } else {
                            System.out.println("Account number already taken\nPlease try again");
                        }

                    }
                } else {
                    System.out.println("Invalid Login credentials! Please try again");
                }
                if (userData.size() > 1) {
                    for (BankAccount_1_2 bankAccount12 : userData) {
                        System.out.println("Bank Account Number: " + bankAccount12.getAccountNum() + ", Bank Balance: " + bankAccount12.getAccountBalance());
                    }
                }
                scn.nextLine();
            }
            System.out.println("Welcome to the Account Holders portal!");
            int userAccNum = accNumValidation(scn, "Enter your account number: ");
            loginLoop:
            for (BankAccount_1_2 bankAccount : userData) {
                if (bankAccount.getAccountNum() == userAccNum) {
                    double openingDeposit = moneyValidation(scn, "Enter your opening deposit: ");

                    bankAccount.setAccountBalance((bankAccount.getAccountBalance() + openingDeposit));
                    for (BankAccount_1_2 bankAccount_1_22 : userData) {

                        System.out.println("Account number: " + bankAccount_1_22.getAccountNum() + ", Opening balance: $"
                                + bankAccount_1_22.getAccountBalance() + "\n");
                    }
                    double transferToAccNum = accNumValidation(scn, "Enter the account number you want to transfer money to:");
                    for (BankAccount_1_2 bnkAccount : userData) {
                        if (transferToAccNum != userAccNum && transferToAccNum == bnkAccount.getAccountNum()) {
                            //validating the transfer account number is not the users number and is valid
                            double transferAmount = moneyValidation(scn, "Enter the amount of money to transfer: $");
                            for (BankAccount_1_2 bankAccount_ : userData) {


                                if (bankAccount_.getAccountNum() == userAccNum && (bankAccount_.getAccountBalance() -
                                        transferAmount) < 0)
                                //Checking if the account balance falls below 10
                                {
                                    System.out.println("Error! Account balance is less than $0.00");
                                    break doLoop;
                                } else if (bankAccount_.getAccountNum() == userAccNum && ((bankAccount_.getAccountBalance()
                                        - transferAmount) >= 0)) {
                                    System.out.println("Transfer success!");

                                    if (bankAccount_.getAccountNum() == userAccNum) {
                                        bankAccount_.setAccountBalance(bankAccount_.getAccountBalance() - transferAmount);
                                        if (bankAccount.getAccountNum() == userAccNum && bankAccount_.getAccountBalance() < 10) {
                                                System.out.println("Warning! Balance has fallen below $10");
                                            }
                                        System.out.println("Account number: " + bankAccount.getAccountNum() + ", Account Balance: $" +
                                                bankAccount_.getAccountBalance());
                                        for (BankAccount_1_2 bankAccount_1_ : userData) {
                                            if (bankAccount_1_.getAccountNum() == transferToAccNum) {
                                                    bankAccount_1_.setAccountBalance(bankAccount_1_.getAccountBalance() + transferAmount);
                                                    System.out.println("Account number: " +
                                                            bankAccount_1_.getAccountNum() + " Account Balance: $" +
                                                            bankAccount_1_.getAccountBalance());

                                                }
                                            }
                                        }


                                }
                            }
                        } else {
                            int counter = 0;
                            if (transferToAccNum == userAccNum && transferToAccNum != bnkAccount.getAccountNum()) {
                                counter++;
                                if (counter == userData.size()) {
                                    System.out.println(bnkAccount.getAccountNum());
                                    System.out.println("Invalid Bank Account transfer Number!");
                                    break loginLoop;
                                }
                            }

                        }


                    }
                }
                int accNumCount = 0;
                for (BankAccount_1_2 bankAccount12 : userData) {
                    if (bankAccount12.getAccountNum() != userAccNum) {
                        accNumCount++;
                        if (accNumCount == userData.size()) {
                        System.out.println("Invalid Bank Account Number!");
                        }
                    }
                }
            }
            System.out.println("Type 'x' to exit! \nTo continue type any other number");
            exit = scn.next().charAt(0);
            scn.nextLine();


        } while (exit != 'x');
    }
}