import java.io.*;
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
        return "BankAccount details{" +
                "accountNum=" + accountNum +
                ", accountBalance=" + accountBalance +
                ", customerAccName='" + customerAccName + '\'' +
                ", password=" + Arrays.toString(password) +
                ", interestRate=" + interestRate +
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
    }//Method to validate the account number

    private static double interestRateValidator(Scanner scanner, String message, double balance, int months) {
        double rate;
        do {
            System.out.println(message);
            while (!scanner.hasNextDouble()) {
                System.out.println("invalid interest rate!\nPlease enter a valid rate");
                scanner.next();
            }
            rate = scanner.nextDouble();

        }
        while (rate < 0.01 || rate >= 15 && (balanceAtEndOfTerm(balance, rate, months) < 0 || balanceAtEndOfTerm(balance, rate, months) > 1000000));
        return rate;
    }//Validator for interest rate


    private static double moneyValidation(Scanner scanner, String message) {
        double sum;
        do {
            System.out.println(message);
            int count = 0;
            while (!scanner.hasNextDouble() && count == 0) {
                System.out.println("Invalid Balance has reached maximum");
                count++;
            }
            sum = scanner.nextDouble();
        } while (sum < 0 || sum > 100000);

        return sum;
    } //Validator for cash inputs

    static double withdrawalValidator(Scanner scanner, String message, double balance, int months) {
        double amount;
        do {
            System.out.println(message);
            int count = 0;
            while (!scanner.hasNextDouble() && count == 0) {
                System.out.println("Invalid Balance has reached maximum");
                count++;
            }
            amount = scanner.nextDouble();
        }
        while (balanceAtEndOfTerm(months, balance, amount) < 0 || balanceAtEndOfTerm(balance, months, amount) > 100000);
        return amount;
    }//Validator for withdrawal inputs

    static double depositValidator(Scanner scanner, String message, double balance, int months) {
        double amount;
        do {

            System.out.println(message);

            while (!scanner.hasNextDouble()) {
                System.out.println("Invalid entry");
            }
            amount = scanner.nextDouble();
            System.out.println();
            int count = -1;
            count++;
            if (count > 0) {
                System.out.println("Invalid amount!!");
            }


        }
        while (balanceAtEndOfTerm(balance, months, amount) < 0 || balanceAtEndOfTerm(balance, months, amount) > 100000);
        return amount;
    }//validator for deposit inputs

    static double balanceAtEndOfTerm(double balance, double interestRate, int months) {
        double balancePerMonth;
        for (int i = 1; i <= months; i++) {
            balancePerMonth = (balance * (interestRate / 12));
            balance += balancePerMonth;
        }
        return balance;
    }//balance calculator for only interest

    static double balanceAtEndOfTerm(double balance, int months, double deposit) {
        for (int i = 1; i <= months; i++) {
            balance += deposit;
        }
        return balance;
    }//balance calculator for only deposit

    static double balanceAtEndOfTerm(int months, double balance, double withdrawal) {
        for (int i = 1; i <= months; i++) {
            balance += withdrawal;
        }
        return balance;
    }//balance calculator for only withdrawal

    static double calculateBalance(double balance, double rate, double autoDeposit, double autoWithdraw, int months) {
        double balancePerMonth;
        for (int i = 0; i < months; i++) {
            balancePerMonth = (balance * (rate / 12)) + autoDeposit - autoWithdraw;
            balance += balancePerMonth;
        }
        return balance;
    }//balance calculator

    static void balanceTable(double balance, double rate, double autoDeposit, double autoWithdraw, int months) {
        double balancePerMonth;
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%10s %10s %10s  %10s %10s", "YEAR", "|", "MONTH", "|", "BALANCE");
        System.out.println("\n---------------------------------------------------------------------------");
        for (int i = 1; i <= months; i++) {
            balancePerMonth = (balance * (rate / 12)) + autoDeposit - autoWithdraw;
            balance += balancePerMonth;
            int year = i / 12;
            System.out.format("%10d %10s %10d %10s %s %10f", year, "|", i, "|", "$", balance);
            System.out.println();
        }
        System.out.println("-----------------------------------------------------------------------------");

    }//Balance table generator

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
    }//Month validator


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        ArrayList<BankAccount_3> userData = new ArrayList<>();
        String authUserName = "Muiz";//Authorized Username
        char[] authPassword = "Hello".toCharArray(); //Authorized password
        char exit;//Declaration of the char Variable
        //Label for the do While Loop
        doLoop:
        do {//Start of the do loop
            System.out.println("_______________________________\n");
            System.out.println("Welcome to the InterBanking Pty");
            System.out.println("_______________________________");
            while (userData.size() < 2) {//while 2 accounts have been created by a user this will run
                System.out.println("_______________________________\nWelcome to the ACCOUNT CREATION PORTAL\n_______________________________");
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
                    System.out.println("Successfully created accounts:");
                    for (BankAccount_3 account_3 : userData) {
                        System.out.println("Bank Account Number: " + account_3.getAccountNum());
                    }
                }
                scanner.nextLine();
            }
            whileLoop:
            while (userData.size() == 2) {
                System.out.println("-------------------------------------------------");
                System.out.println("\nWelcome to the Account portal!");
                System.out.println("\n--------------------------------------------------");
                int accNum = accNumValidation(scanner, "Enter your account number: ");
                validationLoop:
                //Foreach loop name
                for (BankAccount_3 bankAccount_3 : userData) {
                    if (bankAccount_3.getAccountNum() == accNum) {//Validation of the user's account number
                        while (bankAccount_3.getAccountBalance() == 0 && bankAccount_3.getInterestRate() == 0) {//To check if the user has already applied an auto deposit and withdrawas
                            int monthsToView = monthValidation(scanner, "Enter the number of months to view the forecast: ");
                            double openingBalance = moneyValidation(scanner, "Enter your opening balance: ");
                            double interestRate = interestRateValidator(scanner, "Enter your interest rate: ", bankAccount_3.getAccountBalance(), monthsToView);
                            double autoDeposit = depositValidator(scanner, "Enter automatic monthly deposit: ", bankAccount_3.getAccountBalance(), monthsToView);
                            double autoWithdrawal = withdrawalValidator(scanner, "Enter automatic withdrawal: ", bankAccount_3.getAccountBalance(), monthsToView);
                            if (calculateBalance(bankAccount_3.getAccountBalance(), interestRate, autoDeposit, autoWithdrawal, monthsToView) < 0 || calculateBalance(bankAccount_3.getAccountBalance(), interestRate, autoDeposit, autoWithdrawal, monthsToView) > 100000) {
                                System.out.println("Account balance is out of valid range please try again");
                                break;
                            } else {
                                bankAccount_3.setAccountBalance(openingBalance);//setting the balance
                                bankAccount_3.setInterestRate(interestRate);
                                balanceTable(bankAccount_3.getAccountBalance(), interestRate, autoDeposit, autoWithdrawal, monthsToView);
                                bankAccount_3.setAccountBalance(calculateBalance(bankAccount_3.getAccountBalance(), interestRate, autoDeposit, autoWithdrawal, monthsToView));
                                File fileL = new File(bankAccount_3.getCustomerAccName() + "'s user details.txt");
                                BufferedWriter file = new BufferedWriter(new FileWriter(bankAccount_3.getCustomerAccName() + "'s user details.txt"));//Writing the
                                // users data
                                file.write(bankAccount_3.toString());
                                file.flush();
                                file.close();
                                BufferedReader br = new BufferedReader(new FileReader(fileL.getCanonicalPath()));//Reading the user's data
                                System.out.println(br.readLine());
                                br.close();

                            }
                        }
                        if (bankAccount_3.getAccountBalance() > 0) {
                            double transferToAccNum = accNumValidation(scanner, "Enter the account number you want to transfer money to:");
                            for (BankAccount_3 bnkAccount : userData) {
                                TransferLoop:
                                if (transferToAccNum != accNum && transferToAccNum == bnkAccount.getAccountNum()) {
                                    //validating the transfer account number is not the users number and is valid
                                    double transferAmount = moneyValidation(scanner, "Enter the amount of money to transfer: $");
                                    int count = 0;
                                    for (BankAccount_3 bankAccount_ : userData) {


                                        if (bankAccount_.getAccountNum() == accNum && (bankAccount_.getAccountBalance() -
                                                transferAmount) < 0)
                                        //Checking if the account balance falls below $10
                                        {
                                            System.out.println("Error! Account balance is less than $0.00"); //Error message when account Balance falls below $0.00
                                            break doLoop; //program ends
                                        } else { //If transfer amount is greater than account balance
                                            count++;

                                            if (bankAccount_.getAccountNum() == accNum) {//
                                                bankAccount_.setAccountBalance(bankAccount_.getAccountBalance() - transferAmount);//removing the transfer amount from the sender
                                                System.out.println("Transfer success!");
                                                if (bankAccount_3.getAccountNum() == accNum && bankAccount_.getAccountBalance() < 10) {//if senders account balance falls below $10
                                                    System.out.println("Warning! Balance has fallen below $10");//Warning message
                                                }
                                                System.out.println("Account number: " + bankAccount_3.getAccountNum() + ", Account Balance: $" +
                                                        bankAccount_.getAccountBalance() + "\nTransferred Amount: $" + transferAmount);//Displays transfer amount
                                                for (BankAccount_3 bankAccount_1_ : userData) {
                                                    if (bankAccount_1_.getAccountNum() == transferToAccNum) {
                                                        if (bankAccount_1_.getAccountBalance() > 100000) {
                                                            System.out.println("Warning Balance is above $100,000 which is above federally insured amount");
                                                        } else {
                                                            bankAccount_1_.setAccountBalance(bankAccount_1_.getAccountBalance() + transferAmount);/*Adding the transferred money
                                                    to the receiver's account  */

                                                            System.out.println("Account number: " +//Displays Balance and account number
                                                                    bankAccount_1_.getAccountNum() + ", Account Balance: $" +
                                                                    bankAccount_1_.getAccountBalance());
                                                            break whileLoop;
                                                        }
                                                    }
                                                }
                                            } else {
                                                int accNumCount = 0;//Iterator to check the user's who have been iterated
                                                for (BankAccount_3 bankAccount3 : userData) {
                                                    if (bankAccount3.getAccountNum() != accNum) {
                                                        accNumCount++;
                                                        if (accNumCount == userData.size()) {//Iterator to check the
                                                            // user's who have been iterated
                                                            System.out.println("Invalid Bank Account Number!");

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                } else {
                                    int counter = 0;//Iterator to check the user's who have been iterated
                                    if (transferToAccNum == accNum && transferToAccNum != bnkAccount.getAccountNum()) {/*To check the transferee's
                           account number     */
                                        counter++;
                                        if (counter == userData.size()) {//if all users have been checked
                                            System.out.println(bnkAccount.getAccountNum());
                                            System.out.println("Invalid Bank Account transfer Number!");
                                            break validationLoop;
                                        }

                                    }

                                    scanner.nextLine();
                                }

                            }


                        }

                    }

                }

            }
            System.out.println("Type 'x' to exit! \nTo continue type any other number");
            exit = scanner.next().charAt(0);
            scanner.nextLine();
        } while (exit != 'x');
    }
}

