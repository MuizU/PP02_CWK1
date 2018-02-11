import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class BankAccount_1 {


    public static int count;

    static {
        count = 0;

    }

    public int accountNum;
    public double accountBalance;
    public String customerAccName;
    public char[] password;


    public BankAccount_1(int accountNum, double accountBalance, String customerName, char[] password) {
        super();
        this.accountNum = accountNum;
        this.accountBalance = accountBalance;
        this.customerAccName = customerName;
        this.password = password;
        count++;
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

}

class AccountTest {

    private static int accNumValidation(Scanner scn, String output) {
        int sum;
        do {
            System.out.println(output);
            while (!scn.hasNextInt()) {
                System.out.println("Invalid input! Try again.");
                scn.next();
            }
            sum = scn.nextInt();
        } while (sum < 0);

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
        ArrayList<BankAccount_1> userData = new ArrayList<>();
        String authUserName = "Muiz";
        char[] authPassword = "Hello".toCharArray();
        char exit;
        do {
            System.out.println("_______________________________");
            System.out.println("Welcome to the InterBanking Pty");
            System.out.println("_______________________________");

            System.out.println("\nLogin Success!\nPlease choose an option!\n1.Create Bank account\n2.Deposit\n3.Withdraw\n" +
                    "4.View account balance\nPress 'x' to exit");
            System.out.println("_______________________________\n");

            int option = scn.nextInt();
            switch (option) {

                case 1:
                    scn.nextLine();
                    System.out.println("Enter authorized username: ");
                    String userName = scn.nextLine();
                    System.out.println("Enter authorized password: ");

                    char[] password = scn.nextLine().toCharArray();

                    if (userName.equals(authUserName) && Arrays.equals(password, authPassword)) {
                        System.out.println("Enter customers name: ");
                        String customerName = scn.nextLine();
                        int accountNum = accNumValidation(scn, "Enter customers 4-digit account number");
                        scn.nextLine();
                        System.out.println("Enter password: ");
                        char[] customerPassword = scn.nextLine().toCharArray();
                        double balance = moneyValidation(scn, "Enter initial deposit: ");
                        BankAccount_1 bankAccount_1 = new BankAccount_1(accountNum, balance, customerName, customerPassword);
                        userData.add(bankAccount_1);
                        scn.nextLine();
                    } else {
                        System.out.println("Incorrect login information");
                    }

                    break;
                case 2:
                    scn.nextLine();
                    System.out.println("Enter customer name: ");
                    String name = scn.nextLine();
                    int accNum = accNumValidation(scn, "Enter customer account number: ");
                    for (BankAccount_1 bankAccount_1 : userData) {
                        if (accNum == bankAccount_1.getAccountNum() && name.equals(bankAccount_1.getCustomerAccName())) {
                            System.out.println("You have successfully logged in! \n");
                            double depositAmount = moneyValidation(scn, "Please enter the amount you want to deposit: ");
                            double currentBalance = bankAccount_1.getAccountBalance() + depositAmount;
                            bankAccount_1.setAccountBalance(currentBalance);

                        } else {
                            System.out.println("Incorrect login credentials. Please try again!");
                        }

                    }
                    break;
                case 3:
                    scn.nextLine();
                    System.out.println("Enter customer name: ");
                    name = scn.nextLine();
                    System.out.println();
                    accNum = accNumValidation(scn, "Enter customer account number: ");
                    for (BankAccount_1 bankAccount_1 : userData) {
                        if (accNum == bankAccount_1.getAccountNum() && name.equals(bankAccount_1.getCustomerAccName())) {
                            System.out.println("You have successfully logged in! \n");
                            double withdrawAmount = moneyValidation(scn, "Please enter the amount you want to withdraw: ");
                            double currentBalance = bankAccount_1.getAccountBalance() - withdrawAmount;
                            bankAccount_1.setAccountBalance(currentBalance);
                        } else {
                            System.out.println("Incorrect login credentials. Please try again!");
                        }
                    }
                    break;
                case 4:
                    scn.nextLine();
                    System.out.println("Enter customer name: ");
                    name = scn.nextLine();
                    accNum = accNumValidation(scn, "Enter customer account number: ");
                    for (BankAccount_1 bankAccount_1 : userData) {
                        if (accNum == bankAccount_1.getAccountNum() && name.equals(bankAccount_1.getCustomerAccName())) {
                            System.out.println("You have successfully logged in.\n Your account balance is: " + bankAccount_1.getAccountBalance());
                        } else {
                            System.out.println("Incorrect login details!");
                        }
                        System.out.println();
                    }
                    break;
                default:
                    System.out.println("Incorrect choice. Please choose a valid number!");
            }
            System.out.println("Type 'x' to exit! \nTo continue type any other number");
            exit = scn.next().charAt(0);
            scn.nextLine();
        } while (exit != 'x');
    }
}