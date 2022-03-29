package application;

import java.io.File;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Database {

    private final String PATH;
    private final String url;

    public Database(String PATH, String fileName) {
        this.PATH = PATH;
        this.url = "jdbc:sqlite:" + PATH + fileName;

        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
        df.applyPattern("#,##0.00;(#,##0.00)");
    }

    // Connect to the database
    public Connection connect() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    // Check for existing database and create a new one if an existing one is not found
    public void createDatabase() {
        File directory = new File(PATH);

        if (!directory.exists()) {
            directory.mkdir();
        }

        try (Connection conn = this.connect()) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("A new database has been created with the "
                    + meta.getDriverName() + " driver.\n");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTables() {
        String sql = """
                CREATE TABLE IF NOT EXISTS account (
                accountNumber INTEGER PRIMARY KEY,
                name varchar(255) NOT NULL,
                type varchar(255) NOT NULL,
                balance double DEFAULT 0.00
                );""";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createAccount(int accountNumber, String name, String type) {
        String sql = "INSERT INTO account(accountNumber,name,type) VALUES(?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, accountNumber);
            pstmt.setString(2, name);
            pstmt.setString(3, type);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAccount(int accountNumber) {
        String sql = "DELETE FROM account WHERE accountNumber = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Returns a list of all accounts
    public ArrayList<Account> selectAllAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        String sql = "SELECT accountNumber, name, type, balance FROM account";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                accounts.add(new Account(
                        rs.getInt("accountNumber"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getDouble("balance")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return accounts;
    }

    // Prints each account in the specified account type table for the printChartOfAccounts() method
    public void printByAccountType(String type) {
        ArrayList<Account> list = selectByAccountType(type);

        if (list.isEmpty()) {
            System.out.println("* No " + type + " accounts exist *");
        } else {
            for (Account account : list) {
                System.out.println(account + ": " + account.formattedBalance());
            }
        }
    }

    // Returns a list of accounts based on type (Asset, Liability, Equity, Revenue, or Expense)
    public ArrayList<Account> selectByAccountType(String type) {
        ArrayList<Account> accounts = new ArrayList<>();
        String sql = "SELECT accountNumber, name, balance, type FROM account WHERE type = '" + type + "'";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                accounts.add(new Account(
                        rs.getInt("accountNumber"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getDouble("balance")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return accounts;
    }

    // Returns a list based on the name provided
    public ArrayList<String> selectByAccountName(String name) {
        ArrayList<String> accounts = new ArrayList<>();
        String sql = "SELECT name FROM account WHERE name = '" + name + "'";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                accounts.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return accounts;
    }

    public ArrayList<Account> getAllAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();

        accounts.addAll(selectByAccountType("Asset"));
        accounts.addAll(selectByAccountType("Liability"));
        accounts.addAll(selectByAccountType("Equity"));
        accounts.addAll(selectByAccountType("Revenue"));
        accounts.addAll(selectByAccountType("Expense"));

        return accounts;
    }

    public String getAccountName(int accountNumber) {
        String sql = "SELECT name FROM account WHERE accountNumber = " + accountNumber;
        String name = "";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            name = rs.getString("name");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return name;
    }

    public Double getAccountBalance(int accountNumber) {
        String sql = "SELECT balance FROM account WHERE accountNumber = " + accountNumber;
        double balance = 0;

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            balance = rs.getDouble("balance");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return balance;
    }

    public String getAccountType(int accountNumber) {
        String sql = "SELECT type FROM account WHERE accountNumber = " + accountNumber;
        String type = "";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            type = rs.getString("type");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return type;
    }

    // Returns last account added to the specified account type table
    public void selectLastRecord(Integer accountNumber) {
        String sql = "SELECT accountNumber, name, balance FROM account WHERE accountNumber = " + accountNumber;

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            System.out.println(
                rs.getInt("accountNumber") +  " - " +
                rs.getString("name"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Returns the account number for the last account added to the specified account type table
    public Integer getLastAccountNumber(String type) {
        int lastAccountNumber = -1;

        String sql = "SELECT accountNumber, name, balance, type FROM account" +
                " WHERE type = '" + type + "' ORDER BY accountNumber DESC LIMIT 1";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            lastAccountNumber = rs.getInt("accountNumber");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lastAccountNumber;
    }

    // Enables updating account balances for the double entry method
    public void updateBalance(int accountNumber, double balance) {
        String sql = """
                UPDATE account
                SET balance = ?
                WHERE accountNumber = ?
                """;

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, balance);
            pstmt.setInt(2, accountNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Returns whether an account with the specified account number exists in the specified type table
    public boolean checkAccountExists(int accountNumber) {
        String sql = "SELECT * FROM account WHERE accountNumber = " + accountNumber;

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    // Returns whether any accounts exist in the Chart of Accounts
    public boolean checkIfAllTablesAreEmpty() {
        return selectAllAccounts().isEmpty();
    }

    // Returns whether any accounts exist in the specified account type table
    public boolean checkIfTableIsEmpty(String type) {
        return !selectByAccountType(type).isEmpty();
    }
}
