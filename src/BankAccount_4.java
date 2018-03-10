import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class BankAccount_4 {

    private static int count;
    private int accountNum;
    private double accountBalance;
    private String customerAccName;
    private char[] password;
    private double interestRate;

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
        return "BankAccount details{" +
                "Account Number =" + accountNum +
                ", Account Balance=" + accountBalance +
                ", Account Name='" + customerAccName + '\'' +
                ", password=" + Arrays.toString(password).replace(",", "").replace("[", "")
                .replace(" ", "")
                .replace("]", "").trim() +
                ", interestRate=" + interestRate + "%" +
                '}';
    }
}

class BankAccTester {

    static BankAccount_4 enterAccountData() {
        Scanner scanner = new Scanner(System.in);
        int accNum = accNumValidation(scanner, "Enter your account number: ");
        scanner.nextLine();
        System.out.print("Enter your account name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your password: ");
        char[] password = scanner.nextLine().toCharArray();
        double openingDeposit = moneyValidation(scanner, "Enter your opening deposit: ");
        double intRate = interestRateValidator(scanner, "Enter the interest rate: ");

        return new BankAccount_4(accNum, openingDeposit, name, password, intRate);
    }

    static String computeInterest(BankAccount_4 bankAccount4) {
        Scanner scanner = new Scanner(System.in);
        int years = yearValidation(scanner, "Enter the amount of years to earn interest: ");
        StringBuilder compoundInterest = new StringBuilder();
        compoundInterest.append("Compound interest for " + years + " years\n");
        for (int x = 1; x <= years; x++) {
            double amount = bankAccount4.getAccountBalance() * Math.pow(1 + bankAccount4.getInterestRate(), x);
            compoundInterest.append(String.format("Year " + x + ": %s %.2f \n", "$", amount));
        }
        return compoundInterest.toString();
    }

    static void dataPersistency(String fileName) throws IOException {
        String line = null;

        FileReader fileReader = new FileReader(fileName);

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        System.out.println("\n");
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
        }
        bufferedReader.close();

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
            System.out.print(message);
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
            System.out.print(output);
            while (!scn.hasNextInt()) {
                System.out.println("Invalid input! Try again.");
                scn.next();
            }
            accNum = scn.nextInt();
        }
        while ((String.valueOf(accNum).length() != 4) && accNum != 0);

        return accNum;
    }//Method to validate the account number
    private static double interestRateValidator(Scanner scanner, String message) {
        double rate;
        do {
            System.out.print(message);
            while (!scanner.hasNextDouble()) {
                System.out.println("invalid interest rate!\nPlease enter a valid rate");
                scanner.next();
            }
            rate = scanner.nextDouble();
        }
        while (rate < 0.01 || rate >= 15);
        return rate;
    }

    public static double moneyValidation(Scanner scanner, String message) {
        double sum;
        do {
            System.out.print(message + "$");
            int count = 0;
            while (!scanner.hasNextDouble() && count == 0) {
                System.out.println("Invalid Balance has reached maximum");
                count++;
            }
            sum = scanner.nextDouble();
        } while (sum < 0 || sum > 100000);

        return sum;
    } //Validator for cash inputs


    public static void main(String[] args) throws NoSuchElementException, IOException {
        int exit;
        Scanner scanner = new Scanner(System.in);
        List<BankAccount_4> userData = new ArrayList<>();
        BankAccount_4 bankAccount_4;
        //Label for the do While Loop
        doLoop:
        do {//Start of the do loop
            System.out.println("\nType 0 as the account number to exit! \nTo continue type any other 4 digit number\n");
            bankAccount_4 = enterAccountData();
            exit = bankAccount_4.getAccountNum();
            if (exit != 0) {
                if (userData.size() == 0) {
                    userData.add(bankAccount_4);
                    String fileName = bankAccount_4.getAccountNum() + " - " + bankAccount_4.getCustomerAccName() + "'s Account details";
                    dataPersistency(displayAccount(bankAccount_4) + "\n\n" + computeInterest(bankAccount_4), fileName);
                } else {
                    ListIterator<BankAccount_4> userDataIterator = userData.listIterator();
                    userDataIterator.hasNext();
                    BankAccount_4 bankAccount4 = userDataIterator.next();
                    if (bankAccount4.getAccountNum() != bankAccount_4.getAccountNum()) {
                        userDataIterator.add(bankAccount_4);
                        String fileName = bankAccount_4.getAccountNum() + " - " + bankAccount_4.getCustomerAccName() + "'s Account details";
                        dataPersistency(displayAccount(bankAccount_4) + "\n\n" + computeInterest(bankAccount_4), fileName);
                    } else {
                        System.out.println("Bank account number matches an already existing number!\n" +
                                "Please Try again!");
                    }


                }
            }

        } while (exit != 0);
        for (BankAccount_4 bankAccount4 : userData) {
            dataPersistency(bankAccount4.getAccountNum() + " - " + bankAccount4.getCustomerAccName() + "'s Account details.txt");
        }
        doLoop:
        do {
            System.out.println("-------------------------------------------------");
            System.out.println("\nWelcome to the Account portal!");
            System.out.println("\n--------------------------------------------------");
            int accNum = accNumValidation(scanner, "Enter your account number: or type 0 to exit: ");
            validationLoop:
            //Foreach loop name
            for (BankAccount_4 account_4 : userData) {
                ifLoop:
                if (account_4.getAccountNum() == accNum && account_4.getAccountBalance() > 0) {//Validation of the user's account number
                    int transferToAccNum = accNumValidation(scanner, "Enter the account number to transfer money to: ");
                    for (BankAccount_4 bnkAccount : userData) {
                        if (transferToAccNum != accNum && transferToAccNum == bnkAccount.getAccountNum()) {
                            //validating the transfer account number is not the users number and is valid
                            double transferAmount = moneyValidation(scanner, "Enter the amount of money to transfer: ");
                            int count = 0;
                            for (BankAccount_4 bankAccount_ : userData) {


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
                                        if (account_4.getAccountNum() == accNum && bankAccount_.getAccountBalance() < 10) {//if senders account balance falls below $10
                                            System.out.println("Warning! Balance has fallen below $10");//Warning message
                                        }
                                        System.out.println("Account number: " + account_4.getAccountNum() + ", Account Balance: $" +
                                                bankAccount_.getAccountBalance() + "\nTransferred Amount: $" + transferAmount);//Displays transfer amount
                                        for (BankAccount_4 bankAccount_1_ : userData) {
                                            if (bankAccount_1_.getAccountNum() == transferToAccNum) {
                                                if (bankAccount_1_.getAccountBalance() > 100000) {
                                                    System.out.println("Warning Balance is above $100,000 which is above federally insured amount");
                                                } else {
                                                    bankAccount_1_.setAccountBalance(bankAccount_1_.getAccountBalance() + transferAmount);/*Adding the transferred money
                                                    to the receiver's account  */
                                                    //  Scanner fileScanner = new Scanner(bankAccount_1_.getAccountNum() + " - " + bankAccount_1_.getCustomerAccName() + "'s Account details";);
                                                    //  fileScanner.nextLine();
                                                    System.out.println("Account number: " +//Displays Balance and account number
                                                            bankAccount_1_.getAccountNum() + ", Account Balance: $" +
                                                            bankAccount_1_.getAccountBalance());
                                                    break ifLoop;
                                                }
                                            }
                                        }
                                    } else {
                                        int accNumCount = 0;//Iterator to check the user's who have been iterated
                                        for (BankAccount_4 bankAccount3 : userData) {
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
            exit = accNum;
        } while (exit != 0);
    }
}



