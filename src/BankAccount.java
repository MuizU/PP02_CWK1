import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;

public class BankAccount {
    private final static double interestRate = 0.03;
    private static int count;
    private int accountNum;
    private BigDecimal accountBalance;
    private String customerAccName;
    private char[] password;

    public BankAccount(int accountNum, BigDecimal accountBalance, String customerAccName, char[] password) {
        this.accountNum = accountNum;
        this.accountBalance = accountBalance;
        this.customerAccName = customerAccName;
        this.password = password;
    }

    public static int getCount() {
        return count;
    }

    public static BankAccount enterAccountData() {// Method to create bank account
        Scanner scanner = new Scanner(System.in);
        int accNum = BankAccountTester.accNumValidation(scanner, "Enter your account number: ");
        scanner.nextLine();
        System.out.print("Enter your account name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your password: ");
        char[] password = scanner.nextLine().toCharArray();
        BigDecimal openingDeposit = BankAccountTester.moneyValidation(scanner, "Enter your opening deposit: ");

        return new BankAccount(accNum, openingDeposit, name, password);
    }

    public static String computeInterest(int years, BankAccount bankAccount) {
        StringBuilder compoundInterest = new StringBuilder();
        compoundInterest.append("Compound interest for ").append(years).append(" years at ").append(bankAccount.getInterestRate() * 100).append("% interest per year\n\n");
        compoundInterest.append(String.format("%10s %10s %10s", "YEAR", "|", "BALANCE"));
        compoundInterest.append("\n---------------------------------------------------------------------------");
        for (int year = 1; year <= years; year++) {
            double amount = new Double(String.valueOf(bankAccount.getAccountBalance())) * Math.pow(1 + bankAccount.getInterestRate(), year);
            compoundInterest.append((System.lineSeparator()));
            compoundInterest.append(String.format("%10d %10s %s %10f", year, "|", "$", amount));
        }
        compoundInterest.append("\n---------------------------------------------------------------------------");
        return compoundInterest.toString();
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
    public String toString() {//toString method
        return "BankAccount{" +
                "accountNum=" + accountNum +
                ", accountBalance=" + accountBalance +
                ", customerAccName='" + customerAccName + '\'' +
                ", password=" + Arrays.toString(password).replace(",", "").replace("[", "")
                .replace(" ", "")
                .replace("]", "").trim() +
                ", interestRate=" + interestRate * 100 +
                "%}";
    }

    public String displayAccount() {//Method to display account details
        return toString();
    }


}
