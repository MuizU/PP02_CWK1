import java.io.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.err;

public class BankAccountTester {


    public static void main(String[] args) throws IOException {
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

                    int years = yearValidation(scanner, "Enter the amount of years to" +
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
                    for (Map.Entry<Integer, BankAccount> entry : userData.entrySet())
                        if (entry.getKey() != bankAccount.getAccountNum()) {
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
                for (Map.Entry<Integer, BankAccount> entry : userData.entrySet()) {
                    ifLoop:
                    if (entry.getKey() == accNum && Arrays.equals(usersPassword, entry.getValue().getPassword())) {//To check if the login credentials are correct
                        //Validation of the user's account number
                        int transferToAccNum = accNumValidation(scanner, "Enter the account number to transfer money to: ");
                        for (Map.Entry<Integer, BankAccount> Entry : userData.entrySet()) {
                            if (transferToAccNum != accNum && transferToAccNum == Entry.getValue().getAccountNum()) {
                                //validating the transfer account number is not the users number and is valid
                                BigDecimal transferAmount = moneyValidation(scanner, "Enter the amount of money to transfer: ");
                                int counter = 0;
                                for (Map.Entry<Integer, BankAccount> eNtry : userData.entrySet()) {


                                    if (eNtry.getKey() == accNum && (eNtry.getValue().getAccountBalance().doubleValue() -
                                            transferAmount.doubleValue()) < 0)
                                    //Checking if the account balance falls below $10
                                    {
                                        System.out.println("Error! Account balance is less than $0.00");
                                        //Error message when account Balance falls below $0.00
                                        break doLoop; //program ends
                                    } else { //If transfer amount is greater than account balance
                                        counter++;

                                        if (eNtry.getKey() == accNum) {//
                                            eNtry.getValue().setAccountBalance(new BigDecimal(eNtry.getValue().getAccountBalance().doubleValue() - transferAmount.doubleValue()));
                                            //removing the transfer amount from the sender
                                            RandomAccessFile sendersFile = new RandomAccessFile(fileName(eNtry.getValue()), "rw");
                                            sendersFile.writeBytes(entry.getValue().displayAccount());
                                            System.out.println("Transfer success!");
                                            if (entry.getValue().getAccountNum() == accNum && eNtry.getValue().getAccountBalance().doubleValue() < 10) {
                                                //if senders account balance falls below $10
                                                System.out.println("Warning! Balance has fallen below $10");//Warning message
                                            }
                                            System.out.println("Account number: " + entry.getValue().getAccountNum() + ", Account Balance: $" +
                                                    eNtry.getValue().getAccountBalance() + "\nTransferred Amount: $" + transferAmount);//Displays transfer amount
                                            for (Map.Entry<Integer, BankAccount> entRy : userData.entrySet()) {
                                                if (entRy.getKey() == transferToAccNum) {
                                                    if (entRy.getValue().getAccountBalance().doubleValue() > 100000) {
                                                        System.out.println("Warning Balance is above $100,000 which is above federally insured amount");
                                                    } else {
                                                        entRy.getValue().setAccountBalance(new BigDecimal(entRy.getValue().getAccountBalance().doubleValue() + transferAmount.doubleValue()));
                                                    /*Adding the transferred money
                                                    to the receiver's account  */
                                                        RandomAccessFile receiversFile = new RandomAccessFile(fileName(entRy.getValue()),
                                                                "rw");

                                                        receiversFile.writeBytes(entRy.getValue().displayAccount());//updates the account information

                                                        receiversFile.close();
                                                        dataPersistency(fileName(entRy.getValue()));
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
                                if (transferToAccNum == accNum && transferToAccNum != Entry.getKey()) {/*To check the transferee's
                           account number     */
                                    counter++;
                                    if (counter == userData.size()) {//if all users have been checked
                                        System.out.println(Entry.getKey());
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
