# Building a ATM Simulator in Java

---

## 🛠 What You Need

1. **Java 17+** (or any recent version). Install from Oracle or OpenJDK.
2. A text editor (VS Code, IntelliJ, or even Notepad).
3. A terminal / command prompt to run `javac` and `java`.

---

## 1. Create the Project Folder

```bash
mkdir ATM
cd ATM
```

You will keep every `.java` file in this `ATM` folder. In Java a folder is called a **package**; here the package name will be `ATM`.

---

## 2. Writing the `ATMAccount` Class

`ATMAccount.java` describes **one bank account**. Create the file and paste the code pieces as we go.

### 2.1 Boilerplate: package & imports

```java
package ATM;              // tells Java the file lives in the ATM folder
import java.util.*;       // List & ArrayList live here
```

### 2.2 Fields (Variables that belong to every account)

```java
public class ATMAccount {
    private static int NEXT_ACC_NUMBER = 10001;  // shared counter for new IDs

    private final int accountNumber;  // unique id -> never changes (final)
    private final int pin;            // 4-digit secret number
    private double balance;           // money we keep, can change, so NOT final
    private final List<String> history = new ArrayList<>(); // list of actions
```

**Why these data-types?**

* `int`  → whole numbers (no decimals). Perfect for IDs & PINs.
* `double` → decimal numbers. Quick & easy for money in tiny demos. (Banks use `BigDecimal`, but that is advanced.)
* `List<String>` → an ordered collection; lets us push messages like *"Deposit 100"*.
* `final`  → means the variable can be set **once**. Protects us from accidental changes.

### 2.3 Constructor – code that runs when we `new ATMAccount(...)`

```java
    public ATMAccount(int pin, double initialBalance) {
        if (pin < 1000 || pin > 9999)               // condition check
            throw new IllegalArgumentException("PIN must be 4 digits");
        if (initialBalance < 0)
            throw new IllegalArgumentException("Initial balance cannot be negative");

        this.accountNumber = NEXT_ACC_NUMBER++;     // auto-assign & increment
        this.pin = pin;
        this.balance = initialBalance;
        history.add(String.format("Account created with %.2f", initialBalance));
    }
```

Why **conditions** (`if`) & exceptions? They *guard* against bad data. Instead of letting the program continue in a broken state, we immediately stop and show a helpful message.

### 2.4 Public Methods – the account’s actions

```java
    public int getAccountNumber() { return accountNumber; }

    public boolean authenticate(int pin) {              // returns true/false
        return this.pin == pin;                         // simple equality check
    }

    public double getBalance() { return balance; }

    public void deposit(double amt) {
        if (amt <= 0) throw new IllegalArgumentException("Deposit must be positive");
        balance += amt;
        history.add(String.format("Deposit %.2f | New balance %.2f", amt, balance));
    }

    public void withdraw(double amt) throws InsufficientFundsException {
        if (amt <= 0) throw new IllegalArgumentException("Withdrawal must be positive");
        if (amt > balance) throw new InsufficientFundsException("Not enough money");
        balance -= amt;
        history.add(String.format("Withdraw %.2f | New balance %.2f", amt, balance));
    }

    public List<String> getHistory() { return history; }

    @Override                   // lets System.out.println(account) show nice info
    public String toString() {
        return String.format("#%d | Balance: %.2f", accountNumber, balance);
    }
}
```

**Return types explained**

* `boolean` – only `true` or `false`, great for questions like *“Is the PIN correct?”*
* `void` – means the method just **does something**; no answer is returned.

### 2.5 Custom Exceptions

Add **tiny** classes at the bottom of the same file:

```java
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String msg) { super(msg); }
}
```

Exceptions are just Java classes that signal *something went wrong*.

---

## 3. Building the Command-Line Interface (`ATMSimulatorCLI.java`)

This class owns `main()` – the starting point when you type `java ...`.

### 3.1 Skeleton

```java
package ATM;
import java.util.*;

public class ATMSimulatorCLI {
    private static final Scanner SCANNER = new Scanner(System.in);  // reads user input
    private static final List<ATMAccount> ACCOUNTS = new ArrayList<>();

    public static void main(String[] args) {
        ACCOUNTS.add(new ATMAccount(1234, 500)); // demo account so users can play
```

*`Scanner`* reads what you type. It’s like a keyboard listener.

### 3.2 Login Loop

```java
        System.out.println("=== ATM ===");
        while (true) {
            System.out.print("Account number (0 to exit): ");
            int accNum = Integer.parseInt(SCANNER.nextLine().trim());
            if (accNum == 0) return;              // 0 quits program
            ATMAccount acc = findAccount(accNum);
            if (acc == null) { System.out.println("Not found\n"); continue; }
            System.out.print("PIN: ");
            int pin = Integer.parseInt(SCANNER.nextLine().trim());
            try {
                if (!acc.authenticate(pin)) throw new InvalidPINException("Wrong PIN");
                session(acc);
            } catch (InvalidPINException e) {
                System.out.println(e.getMessage());
            }
        }
    }
```

*Notes:* `Integer.parseInt` turns text → number.

### 3.3 Helper Methods

```java
    private static ATMAccount findAccount(int num) {
        return ACCOUNTS.stream()
                       .filter(a -> a.getAccountNumber() == num)
                       .findFirst()
                       .orElse(null);
    }
```

`stream()` is library magic that scans the list. Don’t worry if it looks alien – it’s just shorter than a loop.

#### session()

```java
    private static void session(ATMAccount acc) {
        while (true) {
            printMenu();
            int choice = readInt("Choose: ");
            try {
                switch (choice) {
                    case 1 -> System.out.printf("Balance: %.2f%n", acc.getBalance());
                    case 2 -> {
                        double amt = readDouble("Deposit: ");
                        acc.deposit(amt);
                    }
                    case 3 -> {
                        double amt = readDouble("Withdraw: ");
                        acc.withdraw(amt);
                    }
                    case 4 -> acc.getHistory().forEach(System.out::println);
                    case 5 -> { System.out.println("Bye!\n"); return; }
                    default -> System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
```

`case 1 ->` is a **switch expression** (Java 17). Cleaner than classic `break` syntax.

Input helpers:

```java
    private static int readInt(String prompt) { /* loop until user types a whole number */ }
    private static double readDouble(String prompt) { /* similar for decimals */ }
}
```

These loops show how to **validate** input: keep asking until the user gives a number.

---

## 4. Compile & Run

```bash
javac ATM/*.java          # compiles all files
java ATM.ATMSimulatorCLI  # run the program
```

Login with **10001 / 1234** and test deposit, withdraw, history.

---

## 5. Recap – Why Each Piece Matters

| Concept | Example | Why it’s useful |
|---------|---------|-----------------|
| `int` | `pin`, `accountNumber` | small whole numbers, fast |
| `double` | `balance` | supports decimals for money |
| `List<String>` | `history` | growable log, keeps order |
| `final` | `accountNumber` | stops accidental re-assignment |
| `if` + `throw` | check negative deposit | blocks invalid states early |
| `Exception` subclass | `InsufficientFundsException` | specific error → specific message |
| `static` field | `NEXT_ACC_NUMBER` | shared counter across all objects |

---

## 6. Next Steps (When You’re Ready)

* Replace `double` with `BigDecimal` for exact cents.
* Save accounts to a file so they persist after closing.
* Build a graphical interface with JavaFX or Swing.
* Write **unit tests** to automate checks (JUnit).

Happy coding! 🎉
