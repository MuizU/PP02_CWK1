import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BankAccount_4 {

    public static int count;
    public int accountNum;
    public double accountBalance;
    public String customerAccName;
    public char[] password;
    public double interestRate;

    public BankAccount_4(int accountNum, double accountBalance, String customerName, char[] password, double interestRate) {
        super();
        this.accountNum = accountNum;
        this.accountBalance = accountBalance;
        this.customerAccName = customerName;
        this.password = password;
        this.interestRate = interestRate;
        count++;
    }

    public static int getCount() {
        return count;
    }

    public char[] getPassword() {
        return password;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
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
        return "BankAccount details{" +
                "accountNum=" + accountNum +
                ", accountBalance=" + accountBalance +
                ", customerAccName='" + customerAccName + '\'' +
                ", password=" + Arrays.toString(password) +
                ", interestRate=" + interestRate +
                '}';
    }
}

class BankAccTester {

    static BankAccount_4 enterAccountData() {
        Scanner scanner = new Scanner(System.in);
        int accNum = accNumValidation(scanner, "Enter your account number: ");
        scanner.nextLine();
        System.out.println("Enter your account name: ");
        String name = scanner.nextLine();
        System.out.println("Enter your password: ");
        char[] password = scanner.nextLine().toCharArray();
        double openingDeposit = moneyValidation(scanner, "Enter your opening deposit: ");
        double intRate = interestRateValidator(scanner, "Enter the interest rate: ");

        return new BankAccount_4(accNum, openingDeposit, name, password, intRate);
    }

    /*static void computeInterest (BankAccount_4 bankAccount_4){
        Scanner scanner = new Scanner(System.in);
        int years = yearValidation(scanner,"Enter the amount of years to earn interest: ");
        for(int x = 1; x <= years; x++) {
            double amount = bankAccount_4.getAccountBalance() * Math.pow(1+ bankAccount_4.getInterestRate(), x);
            System.out.printf("Year " + x + ": %.2f \n" , amount);
        }
    }*/
    static String computeInterest(BankAccount_4 bankaccount4) {
        Scanner scanner = new Scanner(System.in);
        int years = yearValidation(scanner, "Enter the amount of years to earn interest: ");
        String yEars = String.valueOf(years);
        System.out.println("Compound Interest for " + yEars + " Years");
        String values;
        for (int x = 1; x <= years; x++) {
            double amount = bankaccount4.getAccountBalance() * Math.pow(1 + bankaccount4.getInterestRate(), x);
            values = "\n\n\n" + String.format("Year " + x + ": %.2f \n", amount);
            while (x == years) {
                return values;
            }
        }
        return "";
    }

    static void dataPersistency(String fileName) throws IOException {
        String line = null;

        FileReader fileReader = new FileReader(fileName);

        BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);


            bufferedReader.close();
        }


    }

    static void dataPersistency(String data, String fileName) throws IOException {
        PrintWriter writer = new PrintWriter(fileName + ".txt", "UTF-8");
        writer.println(data);
        writer.close();
    }

    static String displayAccount(BankAccount_4 bankAccount4) {
        return bankAccount4.toString();
    }

    private static int yearValidation(Scanner scanner, String message) {
        int year;
        do {
            System.out.println(message);
            while (!scanner.hasNextInt()) {
                System.out.println("invalid year!\nPlease enter a valid year");
                scanner.next();
            }
            year = scanner.nextInt();

        } while (year < 0 || year > 50);
        return year;
    }

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
        while ((accNum > 9999 || accNum < 1000) && (accNum < 0 || accNum > 0));

        return accNum;
    }//Method to validate the account number


    private static double interestRateValidator(Scanner scanner, String message) {
        double rate;
        do {
            System.out.println(message);
            while (!scanner.hasNextDouble()) {
                System.out.println("invalid interest rate!\nPlease enter a valid rate");
                scanner.next();
            }
            rate = scanner.nextDouble();

        }
        while (rate < 0.01 || rate >= 15);
        return rate;
    }

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


    public static void main(String[] args) throws IOException {
        int exit;
        ArrayList<BankAccount_4> userData = new ArrayList<>();
        BankAccount_4 bankAccount_4;
        Loop:
//Label for the do While Loop
        do {//Start of the do loop
            System.out.println("\nType 0 as the account number to exit! \nTo continue type any other number\n");
            bankAccount_4 = enterAccountData();
            exit = bankAccount_4.getAccountNum();
            String customerName = bankAccount_4.getCustomerAccName();
            if (exit != 0) {
                userData.add(bankAccount_4);
                String fileName = exit + " - " + customerName + "'s Account details";
                dataPersistency(displayAccount(bankAccount_4) + "\n\n" + computeInterest(bankAccount_4), fileName);

            }

        } while (exit != 0);
        for (BankAccount_4 bankAccount4 : userData) {
            dataPersistency(bankAccount4.getAccountNum() + " - " + bankAccount4.getCustomerAccName() + "'s Account details.txt");
        }

    }
}


