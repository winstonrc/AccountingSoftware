package application;

import java.text.DecimalFormat;

public class Account {
    private final DecimalFormat df;

    private final int accountNumber;
    private final String name;
    private final String type;
    private double balance;

    public Account(int accountNumber, String name, String type) {
        this.df = (DecimalFormat) DecimalFormat.getInstance();
        df.applyPattern("#,##0.00;(#,##0.00)");

        this.accountNumber = accountNumber;
        this.name = name;
        this.type = type;
        this.balance = 0.00;
    }

    public Account(int accountNumber, String name, String type, double balance) {
        this.df = (DecimalFormat) DecimalFormat.getInstance();
        df.applyPattern("#,##0.00;(#,##0.00)");

        this.accountNumber = accountNumber;
        this.name = name;
        this.type = type;
        this.balance = balance;
    }

    public int getAccountNumber() {return accountNumber;}

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

    public String formattedBalance() {
        return df.format(balance);
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void updateBalance (double balance) {
        this.balance += balance;
    }

    @Override
    public String toString() {
        return accountNumber + " - " + name;
    }

    @Override
    public boolean equals(Object comparedObject) {
        // if the variables are located in the same place, then they're the same
        if (this == comparedObject) {
            return true;
        }

        // if comparedObject is not of type Account, the objects are not the same
        if (!(comparedObject instanceof Account comparedAccount)) {
            return false;
        }

        // convert the object to an Account-object

        // if the instance variables of the objects are the same, then so are the objects
        return this.accountNumber == comparedAccount.accountNumber &&
                this.name.equals(comparedAccount.name) &&
                this.type.equals(comparedAccount.type);
    }
}
