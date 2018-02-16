import java.util.ArrayList;
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
        int sum;
        do {
            System.out.println(output);
            while (!scn.hasNextInt()) {
                System.out.println("Invalid input! Try again.");
                scn.next();
            }
            sum = scn.nextInt();

        } while (sum < 0 || sum > 9999 || sum < 1000);

        return sum;
    }

    private static double moneyValidation(Scanner scanner, String message) {
        double sum;
        do {
            System.out.println(message);
            while (!scanner.hasNextDouble()) {
                System.out.println("invalid input!");
                scanner.next();
            }
            sum = scanner.nextDouble();
        } while (sum < 0);

        return sum;
    }

    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);
        ArrayList<BankAccount_1_2> userData = new ArrayList<>();
        BankAccount_1_2 bankAccount_1_2 = new BankAccount_1_2(1234, 0, "Muiz Uvais", "Hello".toCharArray());
        BankAccount_1_2 bankAccount_1_21 = new BankAccount_1_2(1111, 0, "Muiz Uvais", "Hello".toCharArray());
        userData.add(bankAccount_1_2);
        userData.add(bankAccount_1_21);

        char exit;
        do {
            System.out.println("_______________________________");
            System.out.println("Welcome to the InterBanking Pty");
            System.out.println("_______________________________");

            int userAccNum = accNumValidation(scn, "Enter your account number: ");
            for (BankAccount_1_2 bankAccount : userData) {
                if (bankAccount.getAccountNum() == userAccNum) {
                    double openingDeposit = moneyValidation(scn, "Enter your opening deposit: ");
                    bankAccount.setAccountBalance(openingDeposit);
                }
                System.out.println("Account number: " + userData.get(0).getAccountNum() + " Opening balance " + userData.get(0).getAccountBalance());
                System.out.println("Account number: " + userData.get(1).getAccountNum() + "Opening balance" + userData.get(1).getAccountBalance());
                double transferToAccNum = accNumValidation(scn, "Enter the account number you want to transfer money to:");

                if (userData.contains(transferToAccNum) && transferToAccNum != userAccNum) {
                    double transferAmount = moneyValidation(scn, "Enter the amount of money to transfer: $");
                    if (transferAmount < userData.get(0).getAccountBalance()) {
                        if (userData.get(0).getAccountBalance() - transferAmount >= 10) {
                            if ((transferAmount + userData.get(1).getAccountBalance()) <= 100000) {
                                userData.set(userData.indexOf(userAccNum), bankAccount_1_2).setAccountBalance(bankAccount_1_2.getAccountBalance() - transferToAccNum);
                                userData.set(userData.indexOf(transferToAccNum), bankAccount_1_2).setAccountBalance(bankAccount_1_2.getAccountBalance() + transferToAccNum);
                                System.out.println("Account number: " + userData.get(0).getAccountNum() + " Opening balance " + userData.get(0).getAccountBalance());
                                System.out.println("Account number: " + userData.get(1).getAccountNum() + "Opening balance" + userData.get(1).getAccountBalance());
                            } else {
                                System.out.println("Warning the account balance of the receiver is above the federal highest " +
                                        "federally insured amount");
                            }
                        } else {
                            System.out.println("Warning account balance is below $10");
                        }
                    } else {
                        System.out.println("Error! Transfer value exceeds account balance");
                        exit = 'x';
                    }
                } else {
                    System.out.println("Error! Incorrect account number");
                    exit = 'x';
                }
            }
            System.out.println("Type 'x' to exit! \nTo continue type any other number");
            exit = scn.next().charAt(0);
            scn.nextLine();
        } while (exit != 'x');


    }

    @Override
    public String toString() {
        return "BankAccountTester{}";
    }
}