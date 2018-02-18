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
        int accNum;
        do {
            System.out.println(output);
            while (!scn.hasNextInt()) {
                System.out.println("Invalid input! Try again.");
                scn.next();
            }
            accNum = scn.nextInt();

        } while (accNum > 9999 || accNum < 1000);

        return accNum;
    }

    static int count = 0;

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
        Scanner scn = new Scanner(System.in);
        ArrayList<BankAccount_1_2> userData = new ArrayList<>();
        BankAccount_1_2 bankAccount_1_2 = new BankAccount_1_2(1234, 0, "Muiz Uvais", "Hello".toCharArray());
        BankAccount_1_2 bankAccount_1_21 = new BankAccount_1_2(1111, 0, "Muiz Uvais", "Hello".toCharArray());
        userData.add(bankAccount_1_2);
        userData.add(bankAccount_1_21);

        char exit;
        doLoop:
        do {
            System.out.println("_______________________________");
            System.out.println("Welcome to the InterBanking Pty");
            System.out.println("_______________________________");

            int userAccNum = accNumValidation(scn, "Enter your account number: ");
            loginLoop:
            for (BankAccount_1_2 bankAccount : userData) {
                if (bankAccount.getAccountNum() == userAccNum) {
                    double openingDeposit = moneyValidation(scn, "Enter your opening deposit: ");

                    bankAccount.setAccountBalance((bankAccount.getAccountBalance() + openingDeposit));

                    for (BankAccount_1_2 bankAccount_1_22 : userData) {

                        System.out.println("Account number: " + bankAccount_1_22.getAccountNum() + " Opening balance " + bankAccount_1_22.getAccountBalance() + "\n");
                    }
                    double transferToAccNum = accNumValidation(scn, "Enter the account number you want to transfer money to:");
                    for (BankAccount_1_2 bnkAccount : userData) {
                        if (transferToAccNum != userAccNum && transferToAccNum == bnkAccount.getAccountNum()) {//validating the transfer account number is not the users number and is valid
                            double transferAmount = moneyValidation(scn, "Enter the amount of money to transfer: $");
                            for (BankAccount_1_2 bankAccount_ : userData) {


                                if (bankAccount_.getAccountNum() == userAccNum && (bankAccount_.getAccountBalance() - transferAmount) < 0) //Checking if the account balance falls below 10
                                {
                                    System.out.println(bankAccount_.getAccountBalance());
                                    System.out.println("Error! Account balance is less than $0.00");
                                    break doLoop;
                                } else if (bankAccount_.getAccountNum() == userAccNum && ((bankAccount_.getAccountBalance() - transferAmount) >= 0)) {
                                    System.out.println("Transfer success!");

                                    if (bankAccount_.getAccountNum() == userAccNum) {
                                        bankAccount_.setAccountBalance(bankAccount_.getAccountBalance() - transferAmount);
                                        if (bankAccount.getAccountNum() == userAccNum && bankAccount_.getAccountBalance() < 10) {
                                                System.out.println("Warning! Balance has fallen below $10");
                                            }
                                        System.out.println("Account number: " + bankAccount.getAccountNum() + " Account Balance: $" + bankAccount_.getAccountBalance());
                                            for (BankAccount_1_2 bankAccount_1_ : userData) {
                                                if (bankAccount_1_.getAccountNum() == transferToAccNum) {
                                                    bankAccount_1_.setAccountBalance(bankAccount_1_.getAccountBalance() + transferAmount);
                                                    System.out.println("Account number: " + bankAccount_1_.getAccountNum() + " Account Balance: $" + bankAccount_1_.getAccountBalance());

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