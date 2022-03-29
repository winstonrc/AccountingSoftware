package application;

import java.text.DecimalFormat;

public class ChartOfAccounts {
    private final Database database;

    private final DecimalFormat df;

    private int assetAccountNumber;
    private int liabilityAccountNumber;
    private int equityAccountNumber;
    private int revenueAccountNumber;
    private int expenseAccountNumber;

    public ChartOfAccounts(Database database) {
        this.df = (DecimalFormat) DecimalFormat.getInstance();
        df.applyPattern("#,##0.00;(#,##0.00)");

        this.database = database;

        this.assetAccountNumber = 1000;
        this.liabilityAccountNumber = 2000;
        this.equityAccountNumber = 3000;
        this.revenueAccountNumber = 4000;
        this.expenseAccountNumber = 5000;
    }

//    Print a formatted and comprehensive Chart of Accounts REMOVE
    public void printChartOfAccounts() {
        System.out.println("* Chart of Accounts *");

//        Print Asset accounts
        System.out.println("\nASSETS");
        database.printByAccountType("Asset");

//        Print Liability accounts
        System.out.println("\nLIABILITIES");
        database.printByAccountType("Liability");

//        Print Equity accounts
        System.out.println("\nEQUITY");
        database.printByAccountType("Equity");

//        Print Revenue accounts
        System.out.println("\nREVENUES");
        database.printByAccountType("Revenue");

//        Print Expense accounts
        System.out.println("\nEXPENSES");
        database.printByAccountType("Expense");
    }

//    Search a hash map of all accounts and return true if found
    public boolean checkAccountExists(int accountNumber) {
        return database.checkAccountExists(accountNumber);
    }

//    Add an account to the relevant account type list
    public void add(String name, String accountType) {
//        Add Asset account
        if (accountType.equals("Asset")) {
            if (database.checkIfTableIsEmpty("Asset")) {
                assetAccountNumber = database.getLastAccountNumber("Asset") + 10;
            }

            if (assetAccountNumber < 2000 && checkDuplicateAccount(name)) {
                database.createAccount(assetAccountNumber, name, accountType);
                displayAccountAddedMessage(assetAccountNumber);
                assetAccountNumber += 10;
            } else {
                System.out.println("ERROR: Too many Asset accounts already exist.");
                System.out.println("Please delete an Asset account before adding a new one.");
            }
        }

//        Add Liability account
        if (accountType.equals("Liability")) {
            if (database.checkIfTableIsEmpty("Liability")) {
                liabilityAccountNumber = database.getLastAccountNumber("Liability") + 10;
            }


            if (liabilityAccountNumber < 3000 && checkDuplicateAccount(name)) {
                database.createAccount(liabilityAccountNumber, name, accountType);
                displayAccountAddedMessage(liabilityAccountNumber);
            } else {
                System.out.println("ERROR: Too many Liability accounts already exist.");
                System.out.println("Please delete an Liability account before adding a new one.");
            }
        }

//        Add Equity account
        if (accountType.equals("Equity")) {
            if (database.checkIfTableIsEmpty("Equity")) {
                equityAccountNumber = database.getLastAccountNumber("Equity") + 10;

            }

            if (equityAccountNumber < 4000 && checkDuplicateAccount(name)) {
                database.createAccount(equityAccountNumber, name, accountType);
                displayAccountAddedMessage(equityAccountNumber);
            } else {
                System.out.println("ERROR: Too many Equity accounts already exist.");
                System.out.println("Please delete an Equity account before adding a new one.");
            }
        }

//        Add Revenue account
        if (accountType.equals("Revenue")) {
            if (database.checkIfTableIsEmpty("Revenue")) {
                revenueAccountNumber = database.getLastAccountNumber("Revenue") + 10;

            }

            if (revenueAccountNumber < 5000 && checkDuplicateAccount(name)) {
                database.createAccount(revenueAccountNumber, name, accountType);
                displayAccountAddedMessage(revenueAccountNumber);
            } else {
                System.out.println("ERROR: Too many Revenue accounts already exist.");
                System.out.println("Please delete a Revenue account before adding a new one.");
            }
        }

//        Add Expense account
        if (accountType.equals("Expense")) {
            if (database.checkIfTableIsEmpty("Expense")) {
                expenseAccountNumber = database.getLastAccountNumber("Expense") + 10;

            }

            if (expenseAccountNumber < 6000 && checkDuplicateAccount(name)) {
                database.createAccount(expenseAccountNumber, name, accountType);
                displayAccountAddedMessage(expenseAccountNumber);
            } else {
                System.out.println("ERROR: Too many Expense accounts already exist.");
                System.out.println("Please delete an Expense account before adding a new one.");
            }
        }
    }

    // UPDATE to pull recent account from Database table rather than the array
    public void displayAccountAddedMessage(Integer accountNumber) {
        System.out.println("\nAccount created: ");
        database.selectLastRecord(accountNumber);
    }

//    Remove an account from the relevant account type list
    public void remove(int accountNumber) {
        String name = database.getAccountName(accountNumber);
        String type = database.getAccountType(accountNumber);
        Account account = new Account(accountNumber, name, type);

//        Check that the account balance is empty before allowing removal of the account
        if (database.getAccountBalance(accountNumber) == 0) {
            database.deleteAccount(accountNumber);
            System.out.println("\nAccount removed: ");
            System.out.println(account);
        } else {
            System.out.println("\nERROR: Account balance must be empty before deleting the account.");
            System.out.println("Debit/Credit the account as necessary first.");
        }
    }

//    Perform double entry function for inputting a transaction
    public void doubleEntry(int firstAccount, int secondAccount, double amount) {
        String firstAccountType = database.getAccountType(firstAccount);
        String secondAccountType = database.getAccountType(secondAccount);
        double firstUpdateAmount = amount;
        double secondUpdateAmount = amount;

        if (!firstAccountType.equals("Asset") && !firstAccountType.equals("Expense")) {
            firstUpdateAmount *= -1.0;
        }

        if (secondAccountType.equals("Asset") || secondAccountType.equals("Expense")) {
            secondUpdateAmount *= -1.0;
        }

        updateBalance(firstAccount, firstUpdateAmount);
        updateBalance(secondAccount, secondUpdateAmount);
        System.out.println("\nTransaction recorded:");
        System.out.println(database.getAccountName(firstAccount) + ": " + df.format(firstUpdateAmount));
        System.out.print("        ");
        System.out.println(database.getAccountName(secondAccount) + ": " + df.format(secondUpdateAmount));
    }

    public void updateBalance(int accountNumber, double updateAmount) {
        double currentBalance = database.getAccountBalance(accountNumber);
        double newBalance = currentBalance + updateAmount;
        database.updateBalance(accountNumber, newBalance);
    }

    public boolean checkDuplicateAccount(String name) {
        return !database.selectByAccountName(name).contains(name);
    }
}
