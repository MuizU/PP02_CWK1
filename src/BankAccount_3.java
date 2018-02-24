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

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
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
        } while (rate < 0.01 || rate >= 15);
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
        } while (sum < 0 || sum > 100000);

        return sum;
    }

    static void calculateBalance(double balance, double rate, double autoDeposit, double autoWithdraw, int months) {
        double balancePerMonth;
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%10s %10s %10s", "MONTH", "|", "BALANCE");
        System.out.println("\n-----------------------------------------------------------------------------");
        for (int i = 0; i <= months; i++) {
            balancePerMonth = (balance * (rate / 12)) + autoDeposit - autoWithdraw;
            balance += balancePerMonth;
            if (balance >= 0 || balance <= 100000) {
                System.out.format("%10d %10s %10f", i, "|", balance);
                System.out.println();
            } else {
                System.out.println("Balance has reached cannot change anymore");
            }

        }
        System.out.println("-----------------------------------------------------------------------------");

    }

    static int monthValidation(Scanner scanner, String message) {
        int month;
        do {
            System.out.println(message);
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid number of months");
                scanner.next();
            }
            month = scanner.nextInt();
        } while (month < 0 || month > 60);
        return month;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<BankAccount_3> userData = new ArrayList<>();
        String authUserName = "Muiz";//Authorized Username
        char[] authPassword = "Hello".toCharArray(); //Authorized password
        userData.add(new BankAccount_3(2222, 0, "1", "1".toCharArray(), 0));
        userData.add(new BankAccount_3(1111, 0, "1", "123".toCharArray(), 0));
        char exit;//Declaration of the char Variable
        //Label for the do While Loop
        do {//Start of the do loop
            System.out.println("_______________________________\n");
            System.out.println("Welcome to the InterBanking Pty");
            System.out.println("_______________________________");
            while (userData.size() < 2) {//while 2 accounts have been created by a user this will run
                System.out.println("_______________________________\nWelcome to the ADMIN PORTAL\n_______________________________");
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
                    if (userData.size() == 0) {

                        userData.add(new BankAccount_3(customerAccNum, 0, customerName, customerPassword, 0));
                        System.out.println("Account creation success!");

                    } else {
                        if (userData.get(0).getAccountNum() != customerAccNum) {
                            userData.add(new BankAccount_3(customerAccNum, 0, customerName, customerPassword, 0));
                            System.out.println("Account creation success!");
                        } else {
                            System.out.println("Account number already taken\nPlease try again");
                        }

                    }
                } else {
                    System.out.println("Invalid Login credentials! Please try again");
                }
                if (userData.size() > 1) {
                    for (BankAccount_3 account_3 : userData) {
                        System.out.println("Successfully created accounts:");
                        System.out.println("Bank Account Number: " + account_3.getAccountNum() + ", Bank Balance: " + account_3.getAccountBalance());
                    }
                }
            }
                while (userData.size() == 2) {
                    System.out.println("\nWelcome to the Account Holders portal!");
                    int accNum = accNumValidation(scanner, "Enter your account number: ");
                    for (BankAccount_3 bankAccount_3 : userData) {
                        if (bankAccount_3.getAccountNum() == accNum) {
                            double openingBalance = moneyValidation(scanner, "Enter your opening balance: ");
                            bankAccount_3.setAccountBalance(openingBalance);
                            int monthsToView = monthValidation(scanner, "Enter the number of months to view the forecast");
                            double interestRate = interestRateValidation(scanner, "Enter your interest rate: ");
                            bankAccount_3.setInterestRate(interestRate);
                            double autoDeposit = moneyValidation(scanner, "Enter automatic monthly deposit: ");
                            double autoWithdrawal = moneyValidation(scanner, "Enter automatic withdrawal: ");
                            calculateBalance(bankAccount_3.getAccountBalance(), interestRate, autoDeposit, autoWithdrawal, monthsToView);
                        } else {
                            int accNumCount = 0;//Iterator to check the user's who have been iterated
                            for (BankAccount_3 bankAccount12 : userData) {
                                if (bankAccount12.getAccountNum() != accNum) {
                                    accNumCount++;
                                    if (accNumCount == userData.size()) {//Iterator to check the
                                        // user's who have been iterated
                                        System.out.println("Invalid Bank Account Number!");

                                    }
                                }
                            }
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