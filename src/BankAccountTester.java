import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import static java.lang.System.err;

public class BankAccountTester {


    public static void main(String[] args) throws IOException, ConcurrentModificationException {
        int exitKey;
        Scanner scanner = new Scanner(System.in);
        Map<Integer, BankAccount> userData = new HashMap<>();
        Map<Integer, String> interest = new HashMap<>();
        BankAccount bankAccount;
        doLoop:
        do {
            System.out.println("-------------------------------------------------");
            System.out.println("\nWelcome to InterBanking PTY");
            System.out.println("\n--------------------------------------------------");
            System.out.println("Type '0' as the account Number to exit");
            bankAccount = BankAccount.enterAccountData();
            exitKey = bankAccount.getAccountNum();
            if (exitKey != 0 && userData.size() < 10) {
                if (userData.size() == 0) {

                    int years = yearValidation(scanner, "Enter the amount of years to" +//Number of years to get validation
                            " receive interest: ");
                    userData.put(bankAccount.getAccountNum(), bankAccount);
                    System.out.println("\n" + bankAccount.displayAccount() + "\n");
                    interest.put(bankAccount.getAccountNum(), BankAccount.computeInterest(years, bankAccount));
                    dataPersistency(bankAccount.displayAccount() + "\n\n" + BankAccount.computeInterest(years, bankAccount),
                            fileName(bankAccount));
                    dataPersistency(fileName(bankAccount));
                } else {
                    int years = yearValidation(scanner, "Enter the amount of years to" +
                            " receive interest: ");
                    for (int key : userData.keySet()) {
                        if (key != bankAccount.getAccountNum()) {
                            userData.put(bankAccount.getAccountNum(), bankAccount);
                            interest.put(bankAccount.getAccountNum(), BankAccount.computeInterest(years, bankAccount));
                            System.out.println("\n" + bankAccount.displayAccount() + "\n");
                            dataPersistency(bankAccount.displayAccount() + "\n\n" + BankAccount.computeInterest(years, bankAccount),
                                    fileName(bankAccount));
                            dataPersistency(fileName(bankAccount));

                        } else {
                            System.err.println("Bank Account number is already taken!\nPlease try again");
                            System.out.println();
                        }
                    }
                }
            }
        } while (exitKey != 0 && userData.size() < 10);
        int count = 0;
        while (userData.size() >= 2 && count == 0) {
            System.out.println("-------------------------------------------------");
            System.out.println("\nWelcome to the Account portal!");
            System.out.println("\n--------------------------------------------------");
            int exit;
            doLoop:
            do {
                count++;
                int accNum = accNumValidation(scanner, "Enter your account number: or type 0 to exit: ");
                System.out.print("Enter your password: ");
                char usersPassword[] = scanner.next().toCharArray();
                validationLoop:
                for (int key : userData.keySet()) {
                    ifLoop:
                    if (key == accNum && Arrays.equals(usersPassword, userData.get(key).getPassword())) {//To check
                        // if the login credentials are correct
                        //Validation of the user's account number
                        int transferToAccNum = accNumValidation(scanner, "Enter the account number to transfer money to: ");
                        for (int keyValue : userData.keySet()) {
                            if (transferToAccNum != accNum && transferToAccNum == keyValue) {
                                //validating the transfer account number is not the users number and is valid
                                BigDecimal transferAmount = moneyValidation(scanner, "Enter the amount of money to transfer: ");
                                int counter = 0;
                                for (int keyVal : userData.keySet()) {


                                    if (keyVal == accNum && (userData.get(keyVal).getAccountBalance().doubleValue() -
                                            transferAmount.doubleValue()) < 0)
                                    //Checking if the account balance falls below $10
                                    {
                                        System.out.println("Error! Account balance is less than $0.00");
                                        //Error message when account Balance falls below $0.00
                                        break doLoop; //program ends
                                    } else { //If transfer amount is greater than account balance
                                        counter++;

                                        if (keyVal == accNum) {//
                                            userData.get(keyVal).setAccountBalance(new BigDecimal(userData.get(keyVal).getAccountBalance().doubleValue()
                                                    - transferAmount.doubleValue()));
                                            //removing the transfer amount from the sender
                                            RandomAccessFile sendersFile = new RandomAccessFile(fileName(userData.get(keyVal)), "rw");
                                            sendersFile.writeBytes(userData.get(key).displayAccount());
                                            System.out.println("Transfer success!");
                                            if (userData.get(keyVal).getAccountBalance().doubleValue() < 10) {
                                                //if senders account balance falls below $10
                                                System.out.println("Warning! Balance has fallen below $10");//Warning message
                                            }
                                            System.out.println("Account number: " + userData.get(key).getAccountNum() + ", Account Balance: $" +
                                                    userData.get(keyVal).getAccountBalance() + "\nTransferred Amount: $" + transferAmount);//Displays
                                            // transfer amount
                                            for (int keyvalue : userData.keySet()) {
                                                if (keyvalue == transferToAccNum) {
                                                    if (userData.get(keyvalue).getAccountBalance().doubleValue() > 100000) {
                                                        System.out.println("Warning Balance is above $100,000 which is above federally insured amount");
                                                    } else {
                                                        userData.get(keyvalue).setAccountBalance(new BigDecimal(userData.get(keyvalue).getAccountBalance().doubleValue()
                                                                + transferAmount.doubleValue()));
                                                    /*Adding the transferred money
                                                    to the receiver's account  */
                                                        RandomAccessFile receiversFile = new RandomAccessFile(fileName(userData.get(keyvalue)),
                                                                "rw");

                                                        receiversFile.writeBytes(userData.get(keyvalue).displayAccount());//updates the account information

                                                        receiversFile.close();
                                                        dataPersistency(fileName(userData.get(keyvalue)));
                                                        break ifLoop;
                                                    }
                                                }
                                            }
                                        } else {
                                            int accNumCount = 0;//Iterator to check the user's who have been iterated
                                            for (Map.Entry<Integer, BankAccount> entrySet : userData.entrySet()) {
                                                if (entrySet.getKey() != accNum) {
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
                                if (transferToAccNum == accNum && transferToAccNum != keyValue) {/*To check the transferee's
                           account number     */
                                    counter++;
                                    if (counter == userData.size()) {//if all users have been checked
                                        System.out.println(keyValue);
                                        System.out.println("Invalid Bank Account transfer Number!");
                                        break validationLoop;
                                    }

                                }

                            }

                        }


                    }

                }
                exit = accNum;
                scanner.nextLine();
            } while (exit != 0);
        }
    }

    private static String fileName(BankAccount bankAccount) {
        return bankAccount.getAccountNum() + " - " + bankAccount.getCustomerAccName() + "'s Account details.txt";
    }

    public static BigDecimal moneyValidation(Scanner scanner, String message) {
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

    public static int accNumValidation(Scanner scanner, String message) {
        int accNum;
        do {
            System.out.print(message);
            while (!scanner.hasNextInt()) {
                err.println("Invalid input! Try again.");
                System.out.print("Enter your account number: ");
                scanner.next();
            }
            accNum = scanner.nextInt();
            if (String.valueOf(accNum).length() != 4 && accNum != 0) {
                System.out.println("Error! Invalid account number!\nPlease enter a number within the range of 1000 to 9999");
            }
        }

        while ((String.valueOf(accNum).length() != 4) && accNum != 0);
        return accNum;
    }

    public static int yearValidation(Scanner scanner, String message) {//Method to validate the years
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

    public static void dataPersistency(String fileName) throws IOException {//Method to read from the file
        String line = null;
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        System.out.println("\n");
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        bufferedReader.close();

    }

    public static void dataPersistency(String data, String fileName) throws IOException {//Method to write to the file
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        writer.println(data);
        writer.close();
    }
}
