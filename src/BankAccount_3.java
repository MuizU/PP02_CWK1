public class BankAccount_3 {

    public static int count;
    public int accountNum;
    public double accountBalance;
    public String customerAccName;
    public char[] password;

    public BankAccount_3(int accountNum, double accountBalance, String customerName, char[] password) {
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
        return "BankAccount_3{" +
                "accountNum=" + accountNum +
                ", accountBalance=" + accountBalance +
                '}';
    }
}
