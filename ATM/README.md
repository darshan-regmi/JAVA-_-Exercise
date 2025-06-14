# ATM Simulator CLI – Java Practice Project

Welcome, budding Java developer! 👋 In this mini–project you will build a **command-line ATM simulator** consisting of two files: `ATMAccount.java` and `ATMSimulatorCLI.java`. By the end, you will have a tiny banking app you can run from any terminal – just like the software that powers real ATM machines (minus the money-spitting hardware).

---

## Why Build This?

* **Real-world relevance** – Every time you use an ATM, software very similar to this project is performing deposits, withdrawals, and balance checks.
* **OOP fundamentals** – Sharpen your skills with classes, objects, encapsulation, and simple error handling.
* **Instant feedback** – A CLI gives immediate input/output, so you can iteratively test each feature.

---

## Quick Demo

```bash
❯ javac ATMSimulatorCLI.java ATMAccount.java # compile the files
❯ java ATMSimulatorCLI # run the CLI
===== ATM Simulator =====
Enter account number (0 to exit): 
>>
```

---

## Prerequisites

* JDK **8+** installed (`java -version` to confirm).
* A terminal / command prompt.
* A text editor or IDE (VS Code, IntelliJ, or even Notepad++ – your choice!).

---

## Setup

1. **Clone or download** this repository.
2. Open a terminal *inside the `ATM` folder*.
3. Compile:
   ```bash
   javac ATMAccount.java ATMSimulatorCLI.java
   ```
4. Run:
   ```bash
   java ATMSimulatorCLI
   ```
   That’s it! You’re talking to your ATM.

---

## Step-by-Step Walkthrough

Below is a mentor-style roadmap. Treat each step like a checkpoint – compile and run after every change so bugs stay small and friendly.

1. **Create the `ATMAccount` class**  
   - *What you do:* Declare a new class with private fields `accountNumber` and `balance`.  
   - *Real-world analogy:* Think of a bank folder that stores a unique customer number and their current money tally.  
   - *Tips:* Mark fields `private` and add descriptive comments.  
   - *Watch-outs:* Forgetting `private` means anyone can modify balance directly – not good for security.

2. **Add a constructor & getters**  
   - *What you do:* Write a constructor accepting an `accountNumber` and an initial `balance`. Provide `getBalance()` and `getAccountNumber()`.  
   - *Analogy:* Opening a fresh bank account with a starting deposit.  
   - *Tips:* Use `this.` to disambiguate field names.  
   - *Common mistake:* Accidentally shadowing fields (`balance = balance;`). Always reference `this.balance`.

3. **Implement `deposit(double amount)`**  
   - *What you do:* Increase `balance` by `amount` **only if** `amount > 0`.  
   - *Analogy:* Sliding bills into the ATM slot; the machine rejects Monopoly money (negative or zero).  
   - *Tips:* Return a boolean to signal success/failure – the CLI can then print a friendly message.  
   - *Watch-outs:* Using `==` for floating-point comparisons; prefer `amount <= 0`.

4. **Implement `withdraw(double amount)` with validation**  
   - *What you do:* Decrease `balance` when there is enough money *and* `amount > 0`.  
   - *Analogy:* The ATM checks if your wallet actually contains those bills before handing them over.  
   - *Tips:* Return `false` for insufficient funds and print a concise error message.  
   - *Common mistake:* Updating balance *before* checking funds – always validate first!

5. **Add `displayBalance()`**  
   - *What you do:* Simple `System.out.printf` showing the current balance to two decimal places.  
   - *Analogy:* The ATM screen flashing your remaining balance.  
   - *Tip:* Centralising display logic means consistent formatting everywhere.

6. **Design the `ATMSimulatorCLI` main loop**  
   - *What you do:* In `main()`, instantiate an `ATMAccount`, display a numbered menu, and read user input in a `while` loop.  
   - *Analogy:* The never-ending ATM service cycle waiting for the next customer.  
   - *Tips:* Use `Scanner` for input and a `switch` to route actions.  
   - *Common mistake:* Forgetting to close `Scanner` on exit; it’s polite to release resources.

7. **Wire menu options to account methods**  
   - *What you do:* For each menu number call `deposit`, `withdraw`, or `displayBalance`. Exit gracefully on `4`.  
   - *Analogy:* Pressing physical buttons on an ATM to trigger internal banking operations.  
   - *Tip:* Confirm every action back to the user – people like receipts.  
   - *Watch-outs:* Input mismatch exceptions (e.g., user types letters). Wrap `nextDouble()` calls in `try/catch` or use `hasNextDouble()`.

8. **Polish & test edge cases**  
   - *What you do:* Try zero, negative, and overflow amounts. Handle invalid menu numbers.  
   - *Analogy:* Quality-assurance engineers attacking an ATM prototype to break it *before* customers do.  
   - *Tip:* Add log messages for rejected operations.  
   - *Common mistake:* Not resetting the scanner buffer – call `nextLine()` after numeric reads.

---

## Common Errors & Fixes

| Error message | Likely cause | Quick fix |
|---------------|-------------|-----------|
| `java.util.InputMismatchException` | User typed letters where `double` expected | Use `scanner.hasNextDouble()` before reading or catch the exception and prompt again. |
| `Cannot access private field balance` | Trying to use `account.balance` directly | Call `getBalance()` or create a public method inside `ATMAccount`. |
| `symbol not found: Scanner` | Forgot import | Add `import java.util.Scanner;` at the top of `ATMSimulatorCLI.java`. |
| `Exception in thread "main" java.lang.NumberFormatException` | Parsing string to number without validation | Wrap `Double.parseDouble()` in a `try/catch`. |
| Balance becomes negative after withdrawal | Validation logic missing | Before subtracting, check `amount <= balance`. |

---

## Bonus Extension Ideas

- **Multiple accounts** – Store a `Map<Integer, ATMAccount>` and let users “insert” different cards.
- **PIN authentication** – Add security before transactions.
- **Persistence** – Save balances to a file or small database so money survives program restarts.
- **Unit tests** – Use JUnit to ensure deposits and withdrawals never misbehave.
- **GUI upgrade** – Swing or JavaFX for a windowed ATM.

---

## You Got This! 🚀

Every successful software engineer started with tiny projects just like this one. Celebrate each bug you squash – it means you learned something new today. Keep iterating, keep experimenting, and most importantly **have fun**. The ATM world (and the real one) awaits your next creation!
