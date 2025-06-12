package BankAccount;
/**
 * Simple Account class representing a bank account.
 */
public class Account {
    private static int NEXT_ACC_NUMBER = 1001; // starting account number

    private final int accountNumber;
    private String holderName;
    private double balance;

    public Account(String holderName, double initialBalance) {
        if (initialBalance < 0) throw new IllegalArgumentException("Initial balance cannot be negative.");
        this.accountNumber = NEXT_ACC_NUMBER++;
        this.holderName = holderName;
        this.balance = initialBalance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit amount must be positive.");
        balance += amount;
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal amount must be positive.");
        if (amount > balance) throw new InsufficientFundsException("Insufficient funds.");
        balance -= amount;
    }

    @Override
    public String toString() {
        return String.format("Account #%d | %s | Balance: %.2f", accountNumber, holderName, balance);
    }
}

/**
 * Custom exception for insufficient funds.
 */
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
