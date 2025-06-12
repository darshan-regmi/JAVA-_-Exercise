# Java CLI Projects Collection

This repository contains **11 standalone, terminal-based Java applications**. Each project is completely independent, lives in its own package/folder, and uses only the Java Standard Library—no external dependencies.

All source code targets **Java 17+**, but will compile on any modern JDK (8+). To build and run a project:

```bash
# compile (replace <Package> with the project folder)
cd <Package>
javac *.java

# run (replace MainClass with the CLI entry class)
java <Package>.MainClass
```

---

## 1. Student Grade Calculator (`Student_Grade`)
A utility to record student names and grades, calculate the class average, determine top student, and list all entries.

**Highlights**
* ArrayLists for dynamic data storage
* Validation of grades (0–100)
* Letter-grade conversion (A–F)
* Menu-driven loop with robust input handling

## 2. Banking Account Manager (`BankAccount`)
Manages multiple bank accounts: create, deposit, withdraw, balance inquiry, list all.

**Highlights**
* `Account` model encapsulates data & rules
* Custom `InsufficientFundsException`
* Stream API for account lookup
* Modular methods for each operation

## 3. Library Book Manager (`LibraryManagement`)
Catalog of library books with search, borrow, return, and availability tracking.

**Highlights**
* `Book` class with auto-increment ID and availability flag
* Keyword search across title, author, ISBN
* Exceptions for already-borrowed / not-found books
* CLI shows results in nicely formatted list

## 4. Expense Tracker (`ExpenseTracker`)
Personal finance tracker supporting expense entry, category totals, monthly views, and budget monitoring.

**Highlights**
* `Expense` model stores amount, category, date, description
* Predefined categories/budgets & over-budget alert
* Date parsing via `java.time` API
* Totals and filters implemented with streams

## 5. Contact Directory (`ContactDirectory`)
Store, search, edit, and list personal contacts.

**Highlights**
* Regex validation for phone and email
* Duplicate-contact prevention
* Edit functionality for phone/email
* Custom validation exceptions

## 6. Quiz Game Engine (`QuizGame`)
Interactive multiple-choice quiz with score, accuracy %, and difficulty level.

**Highlights**
* `Question` model (text, options, answer)
* Shuffled questions per selected category
* Scoring algorithm (10 pts correct)
* Tracks accuracy and elapsed time

## 7. Password Manager (`PasswordManager`)
Generates strong passwords and stores service credentials.

**Highlights**
* Random password generator with optional special chars
* Strength calculation & weak-password detection
* Base64 pseudo-encryption for demo
* CRUD list of saved passwords

## 8. Inventory Management System (`InventoryManagement`)
Warehouse tracker for product stock, reorder alerts, and value calculation.

**Highlights**
* `Product` model with quantity, price, reorder level
* Add/remove stock with `InsufficientStockException`
* Low-stock report & total inventory valuation
* All monetary calculations in double for simplicity

## 9. Task Management System (`TaskManagement`)
Organize tasks by priority, due date, completion status, and view stats.

**Highlights**
* `Task` model using `LocalDate`
* Overdue detection and completion rate metric
* Priority validation (1-5)
* Custom date & completion exceptions

## 10. ATM Simulator (`ATM`)
Simulates an ATM interface with PIN authentication, transactions, and receipt printing.

**Highlights**
* `ATMAccount` with transaction history list
* 4-digit PIN auth; demo account pre-seeded (#10001/1234)
* Deposit, withdraw, balance inquiry, print receipt
* `InsufficientFundsException` & `InvalidPINException`

## 11. Hangman Game (`Hangman`)
Classic word-guessing game featuring three difficulties, ASCII hangman drawing, scoring, and replay.

**Highlights**
* Word banks for easy/medium/hard
* ASCII art stages array
* Set of guessed letters, progress display
* Score based on word length and attempts remaining

---

## Development Notes
* No third-party libraries—ideal for learning core Java.
* Each main class contains a `Scanner`-based input loop; press `Ctrl+C` or follow Exit menu item to quit.

Enjoy exploring and extending these mini-applications!
