import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

public class BankAccount_5 {
    private static int count;
    private final double interestRate;
    private int accountNum;
    private BigDecimal accountBalance;
    private String customerAccName;
    private char[] password;

    public BankAccount_5(int accountNum, BigDecimal accountBalance, String customerAccName, char[] password) {
        this.accountNum = accountNum;
        this.accountBalance = accountBalance;
        this.customerAccName = customerAccName;
        this.password = password;
        this.interestRate = 0.03;
    }

    public static int getCount() {
        return count;
    }

    public int getAccountNum() {
        return accountNum;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getCustomerAccName() {
        return customerAccName;
    }

    public char[] getPassword() {
        return password;
    }

    public final double getInterestRate() {
        return interestRate;
    }

    @Override
    public String toString() {
        return "BankAccount_5{" +
                "accountNum=" + accountNum +
                ", accountBalance=" + accountBalance +
                ", customerAccName='" + customerAccName + '\'' +
                ", password=" + Arrays.toString(password) +
                ", interestRate=" + interestRate +
                '}';
    }
}

class BankAccountGenerator extends BankAccount {


    public static void main(String[] args) {
        int exitKey;
        Scanner scanner = new Scanner(System.in);
        Collection<BankAccount_5> userData = new ArrayList<>();
        BankAccount_5 bankAccount_5;
        BankAccountGenerator bankAccountGenerator = new BankAccountGenerator();
        do {
            System.out.println("-------------------------------------------------");
            System.out.println("\nWelcome to InterBanking PTY");
            System.out.println("\n--------------------------------------------------");
            System.out.println("Type 'x' as the account Number to exit");
            bankAccount_5 = bankAccountGenerator.enterAccountData();
            exitKey = bankAccount_5.getAccountNum();
            if (exitKey != 0 || userData.size() < 10) {
                int years = bankAccountGenerator.yearValidation(scanner, "Enter the amount of years to get interest");
                System.out.println(bankAccountGenerator.computeInterest(years, bankAccount_5));
                userData.add(bankAccount_5);
            }
        } while (exitKey != 0 || userData.size() < 10);
    }

    @Override
    public BankAccount_5 enterAccountData() {
        Scanner scanner = new Scanner(System.in);
        int accNum = accNumValidation(scanner, "Enter your account number: ");
        scanner.nextLine();
        System.out.print("Enter your account name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your password: ");
        char[] password = scanner.nextLine().toCharArray();
        BigDecimal openingDeposit = moneyValidation(scanner, "Enter your opening deposit: ");

        return new BankAccount_5(accNum, openingDeposit, name, password);
    }

    @Override
    public String displayAccount(BankAccount_5 bankAccount_5) {
        return bankAccount_5.toString();
    }

    @Override
    public String computeInterest(int years, BankAccount_5 bankAccount_5) {
        StringBuilder compoundInterest = new StringBuilder();
        compoundInterest.append("Compound interest for ").append(years).append(" years at ").append(bankAccount_5.getInterestRate()).append("% per year\n\n");
        compoundInterest.append(String.format("%10s %10s %10s", "YEAR", "|", "BALANCE"));
        compoundInterest.append("\n---------------------------------------------------------------------------");
        for (int year = 1; year <= years; year++) {
            double amount = new Double(String.valueOf(bankAccount_5.getAccountBalance())) * Math.pow(1 + bankAccount_5.getInterestRate(), year);
            compoundInterest.append((System.lineSeparator()));
            compoundInterest.append(String.format("%10d %10s %s %10f", year, "|", "$", amount));
        }
        compoundInterest.append("\n---------------------------------------------------------------------------");
        return compoundInterest.toString();
    }

    @Override
    public BigDecimal moneyValidation(Scanner scanner, String message) {
        BigDecimal sum;
        do {
            System.out.print(message + "$");
            int count = 0;
            while (!scanner.hasNextBigDecimal() && count == 0) {
                System.out.println("Invalid Balance has reached maximum");
                count++;
            }
            sum = scanner.nextBigDecimal();
        } while (sum.doubleValue() < 0 || sum.doubleValue() > 100000);

        return sum;
    }

    @Override
    public int accNumValidation(Scanner scanner, String message) {
        int accNum;
        do {
            System.out.print(message);
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Try again.");
                scanner.next();
            }
            accNum = scanner.nextInt();
        }
        while ((String.valueOf(accNum).length() != 4) && accNum != 0);

        return accNum;
    }

    public int yearValidation(Scanner scanner, String message) {//Method to validate the years
        int year;
        do {
            System.out.print(message);
            while (!scanner.hasNextInt()) {
                System.out.println("invalid year!\nPlease enter a valid year");
                scanner.next();
            }
            year = scanner.nextInt();

        } while (year < 0 || year > 50);
        return year;
    }
}

abstract class BankAccount {
    public abstract BankAccount_5 enterAccountData();

    public abstract String displayAccount(BankAccount_5 bankAccount_5);

    public abstract String computeInterest(int years, BankAccount_5 bankAccount_5);

    public abstract BigDecimal moneyValidation(Scanner scanner, String message);

    public abstract int accNumValidation(Scanner scanner, String message);

    public abstract int yearValidation(Scanner scanner, String message);

}