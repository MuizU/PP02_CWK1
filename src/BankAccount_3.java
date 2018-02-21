import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BankAccount_3 {

    public static int count;
    public int accountNum;
    public double accountBalance;
    public String customerAccName;
    public char[] password;
    public double interestRate;

    public BankAccount_3(int accountNum, double accountBalance, String customerName, char[] password, double interestRate) {
        super();
        this.accountNum = accountNum;
        this.accountBalance = accountBalance;
        this.customerAccName = customerName;
        this.password = password;
        this.interestRate = interestRate;
        count++;
    }

    public char[] getPassword() {
        return password;
    }

    public static int getCount() {
        return count;
    }

    public double getInterestRate() {
        return interestRate;
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
        return "BankAccount_3{" +
                "accountNum=" + accountNum +
                ", accountBalance=" + accountBalance +
                '}';
    }
}

class Tester {
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

    private static double interestRateValidation(Scanner scanner, String message) {
        double rate;
        do {
            System.out.println(message);
            while (!scanner.hasNextDouble()) {
                System.out.println("invalid interest rate!\nPlease enter a valid rate");
                scanner.next();
            }
            rate = scanner.nextDouble();
        } while (rate < 0.01 && rate >= 15);
        return rate;
    }


    private static double moneyValidation(Scanner scanner, String message) {
        double sum;
        do {
            System.out.println(message);
            while (!scanner.hasNextDouble()) {
                System.out.println("invalid value!\nPlease enter a valid amount");
                scanner.next();
            }
            sum = scanner.nextDouble();
        } while (sum < 0 && sum > 100000);

        return sum;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<BankAccount_3> userData = new ArrayList<>();
        String authUserName = "Muiz";//Authorized Username
        char[] authPassword = "Hello".toCharArray(); //Authorized password


        char exit;//Declaration of the char Variable
        doLoop:
        //Label for the do While Loop
        do {//Start of the do loop
            System.out.println("_______________________________\n");
            System.out.println("Welcome to the InterBanking Pty");
            System.out.println("_______________________________");
            while (userData.size() < 2) {//while 2 accounts have been created by a user this will run
                System.out.println("Enter your Authorized Username: ");//Authorized User's username
                String authNameEntry = scanner.nextLine();//The variable where the user can enter their username
                System.out.println("Enter your Authorized Password: ");
                char[] authPasswordEntry = scanner.nextLine().toCharArray();//The variable where the user can enter their password
                if (authNameEntry.equals(authUserName) && Arrays.equals(authPassword, authPasswordEntry)) //To check if the admin has logged in successfully
                {
                    System.out.println("Enter customer Name: ");
                    String customerName = scanner.nextLine();
                    System.out.println("Enter customer's Password: ");
                    char[] customerPassword = scanner.nextLine().toCharArray();
                    int customerAccNum = accNumValidation(scanner, "Enter customer's account number: ");
                    double initialDeposit = moneyValidation(scanner, "Enter initial deposit: $");
                    double intRate = interestRateValidation(scanner, "Enter the interest rate: ");
                    if (userData.size() == 0) {
                        userData.add(new BankAccount_3(customerAccNum, initialDeposit, customerName, customerPassword, intRate));
                        System.out.println("Account creation success!");

                    } else {
                        if (userData.get(0).getAccountNum() != customerAccNum && userData.get(0).getInterestRate() != userData.get(1).getInterestRate()) {
                            userData.add(new BankAccount_3(customerAccNum, initialDeposit, customerName, customerPassword, intRate));
                            System.out.println("Account creation success!");
                            break;
                        } else if (userData.get(0).getInterestRate() == userData.get(1).getInterestRate()) {
                            System.out.println("Interest rates cannot be the same.");
                        } else {
                            System.out.println("Account number already taken\nPlease try again");
                        }

                    }
                } else {
                    System.out.println("Invalid Login credentials! Please try again");
                }
                if (userData.size() > 1) {
                    for (BankAccount_3 account_3 : userData) {
                        System.out.println("Bank Account Number: " + account_3.getAccountNum() + ", Bank Balance: " + account_3.getAccountBalance());
                    }
                }
                scanner.nextLine();
            }
            System.out.println("Type 'x' to exit! \nTo continue type any other number");
            exit = scanner.next().charAt(0);
            scanner.nextLine();
        } while (exit != 'x');
    }
}