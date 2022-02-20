package application;

import java.io.File;
import java.util.Scanner;

public class UI {
    private final String PATH;
    private final String fileName;
    private final Database database;
    private final Scanner scanner;
    private final ChartOfAccounts chartOfAccounts;
    private final String user;

    public UI() {
        this.PATH = System.getProperty("user.dir") + "/database/";
        this.fileName = "accounts.db";
        this.database = new Database(PATH, fileName);
        this.scanner = new Scanner(System.in);
        this.chartOfAccounts = new ChartOfAccounts(database);
        this.user = "User"; // Add user account support in a future release
    }

    public void start() {
        initializeDatabaseTable();
        System.out.println("Welcome, " + user + ".");
        mainMenu();
    }

    public void initializeDatabaseTable() {
        File filePath = new File(PATH + fileName);
        if (!filePath.exists()) {
            database.createDatabase();
        }

        database.createTables();
    }

    public void mainMenu() {
        loop: while (true) {
//             Main menu options
            System.out.println("\n* Main Menu *\n");
            System.out.println("Select from the following numbered options:");
            System.out.println("1. View Chart of Accounts");
            System.out.println("2. Create a new account");
            System.out.println("3. Input a transaction");
            System.out.println("4. Remove an account");
            System.out.println("0. Log out");

//             Input user selection
            System.out.print("\n> ");
            int input = Integer.parseInt(scanner.nextLine());
            System.out.println();
            switch (input) {
                case 0 -> {
                    System.out.println("You have been logged out.\nThank you for your patronage.");
                    break loop;
                }
                case 1 -> displayChartOfAccounts();
                case 2 -> createAccount();
                case 3 -> inputTransaction();
                case 4 -> removeAccount();
                default -> System.out.println("ERROR: Invalid selection");
            }
        }
    }

    public void displayChartOfAccounts() {
        chartOfAccounts.printChartOfAccounts();
    }

    public void createAccount() {
        System.out.println("Enter the name for the new account: ");
        System.out.print("> ");
        String name = scanner.nextLine();

//         Check that user chooses one of the three account types recognized by the system
        String type = "";
        boolean validType = false;
        while (!validType) {
            System.out.println("\nSelect a numbered option for your desired account type:");
            System.out.println("1. Asset");
            System.out.println("2. Liability");
            System.out.println("3. Equity");
            System.out.print("\n> ");
            int input = Integer.parseInt(scanner.nextLine());

            if (input == 1) {
                type = "Asset";
            } else if (input == 2) {
                type = "Liability";
            } else if (input == 3) {
                type = "Equity";
            } else {
                System.out.println("\nERROR: Please choose a valid account type\n");
            }

            if (type.equals("Asset") ||
                type.equals("Liability") ||
                type.equals("Equity")) {
                chartOfAccounts.add(name, type);
                validType = true;
            }
        }
    }

    public void inputTransaction() {
        if (database.checkIfAllTablesAreEmpty()) {
            System.out.println("\nERROR: No accounts exist in the Chart of Accounts");
        } else if (database.getAllAccounts().size() < 2) {
            System.out.println("\nERROR: There must be at least two accounts to input a transaction.");
        } else if (database.getAllAccounts().size() >= 2) {
            System.out.println("Enter the account number to debit (Enter 0 to return).");
            System.out.print("> ");
            int firstAccountNumber = Integer.parseInt(scanner.nextLine());
            boolean validAccount = false;

            while (!validAccount) {
//                Break out of loop if 0 is entered
                if (firstAccountNumber == 0) {
                    break;
                }

                if (!database.checkAccountExists(firstAccountNumber)) {
                    System.out.println("\nERROR: Account not found");
                    break;
                }

                System.out.println("\nEnter the account number to credit (Enter 0 to return).");
                System.out.print("> ");
                int secondAccountNumber = Integer.parseInt(scanner.nextLine());

//                Break out of loop if 0 is entered
                if (secondAccountNumber == 0) {
                    break;
                }

                if (!database.checkAccountExists(secondAccountNumber)) {
                    System.out.println("\nERROR: Account not found");
                    break;
                }

                if (secondAccountNumber == firstAccountNumber) {
                    System.out.println("\nERROR: Same account selected");
                    break;
                }

//                Check for account number and then prompt user to update the balance if account number is valid
                if (database.checkAccountExists(firstAccountNumber) &&
                        database.checkAccountExists(secondAccountNumber)) {
                    System.out.println("\nEnter an amount to debit and credit:");
                    System.out.print("> ");
                    double updateAmountInput = Double.parseDouble(scanner.nextLine());
                    chartOfAccounts.doubleEntry(firstAccountNumber, secondAccountNumber, updateAmountInput);
                    validAccount = true;
                } else {
                    System.out.println("\nERROR: Accounts not found");
                    break;
                }
            }
        } else {
            System.out.println("ERROR");
        }
    }

    public void removeAccount() {
        if (database.checkIfAllTablesAreEmpty()) {
            System.out.println("\nERROR: No accounts exist in the Chart of Accounts");
        } else {
            System.out.println("Enter the account number to delete (Enter 0 to return).");
            System.out.print("> ");
            int accountNumber = Integer.parseInt(scanner.nextLine());

            boolean validAccount = false;

            while (!validAccount) {
//                Break out of loop if 0 is entered
                if (accountNumber == 0) {
                    break;
                }

                if (database.checkAccountExists(accountNumber)) {
                    chartOfAccounts.remove(accountNumber);
                    validAccount = true;
                } else {
                    System.out.println("\nERROR: Account not found");
                    break;
                }
            }
        }
    }
}
