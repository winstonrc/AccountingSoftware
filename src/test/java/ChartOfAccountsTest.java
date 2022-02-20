import application.ChartOfAccounts;
import application.Database;
import application.UI;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;

import java.io.File;
import java.util.Objects;

import static org.junit.Assert.*;

public class ChartOfAccountsTest {
    private Database database;
    private ChartOfAccounts chartOfAccounts;

    private final String PATH = System.getProperty("user.dir") + "/database/";

    @Before
    public void initialize() {
        String fileName = "accounts.db";
        this.database = new Database(PATH, fileName);
        this.chartOfAccounts = new ChartOfAccounts(database);
        UI ui = new UI();
        ui.initializeDatabaseTable();
    }

//     Check that the account list created is empty initially and when called upon
    @Test
    public void accountsListIsEmpty() {
        assertEquals(0, database.selectAllAccounts().size(), 0.0);
    }

//    Check that account type cannot be something other than Asset, Liability, or Equity
    @Test
    public void newAccountMustBeAssetLiabilityOrEquity() {
        String type = "Fraud";
        chartOfAccounts.add("Test Account", type);
        assertEquals(0, database.selectAllAccounts().size());
        chartOfAccounts.remove(1000);
        accountsListIsEmpty();
    }

    @Test
    public void newAccountIsNotADuplicate() {
        String type = "Asset";
        chartOfAccounts.add("Account 1", type);
        assertFalse(chartOfAccounts.checkDuplicateAccount("Account 1"));
        assertTrue(chartOfAccounts.checkDuplicateAccount("Account 2"));
        chartOfAccounts.remove(1000);
        accountsListIsEmpty();
    }

    @Test
    public void accountCanBeDeleted() {
        chartOfAccounts.add("Test Account 1", "Asset");
        chartOfAccounts.add("Test Account 2", "Liability");
        chartOfAccounts.add("Test Account 3", "Equity");
        assertEquals(3, database.selectAllAccounts().size());
        chartOfAccounts.remove(2000);
        assertEquals(2, database.selectAllAccounts().size());
        assertFalse(chartOfAccounts.checkAccountExists(2000));
        chartOfAccounts.remove(1000);
        chartOfAccounts.remove(3000);
        accountsListIsEmpty();
    }

    @Test
    public void getAccountNumberSuccessfullyRetrievesNumber() {
        String type = "Asset";
        chartOfAccounts.add("Test Account 1", type);
        assertEquals(1000, database.getLastAccountNumber(type), 0);
        chartOfAccounts.remove(1000);
        accountsListIsEmpty();
    }

//     Tests for Asset accounts list
    @Test
    public void assetListIsEmptyAtBeginning() {
        String type = "Asset";
        assertEquals(0, database.selectByAccountType(type).size());
    }

    @Test
    public void addingAccountGrowsAssetListByOne() {
        String type = "Asset";
        chartOfAccounts.add("Asset Account", type);
        assertEquals(1, database.selectAllAccounts().size());
        chartOfAccounts.remove(1000);
        accountsListIsEmpty();
    }

    @Test
    public void addedAssetAccountIsInList() {
        String type = "Asset";
        chartOfAccounts.add("Asset Account", type);
        assertEquals("Asset Account", database.getAccountName(
                database.getLastAccountNumber("Asset")));
        chartOfAccounts.remove(1000);
        accountsListIsEmpty();
    }

    @Test
    public void accountNumberIsAppropriateForAssetAccounts() {
        String type = "Asset";
        chartOfAccounts.add("Asset Account", type);
        assertEquals(1000, database.getLastAccountNumber(type), 0.0);
        chartOfAccounts.remove(1000);
        accountsListIsEmpty();
    }

    @Test
    public void balanceForAssetAccountsCanBeSet() {
        String type = "Asset";
        chartOfAccounts.add("Asset Account", type);
        chartOfAccounts.updateBalance(database.getLastAccountNumber(type), 1.23);
        assertEquals(1.23, database.getAccountBalance(
                database.getLastAccountNumber(type)), 0.0);
        chartOfAccounts.updateBalance(1000, -1.23);
        chartOfAccounts.remove(1000);
        accountsListIsEmpty();
    }

//     Tests for all Liability accounts list
    @Test
    public void liabilityListIsEmptyAtBeginning() {
        String type = "Liability";
        assertEquals(0, database.selectByAccountType(type).size());
    }

    @Test
    public void addingAccountGrowsLiabilityListByOne() {
        String type = "Liability";
        chartOfAccounts.add("Liability Account", type);
        assertEquals(1, database.selectByAccountType(type).size());
        chartOfAccounts.remove(2000);
        accountsListIsEmpty();
    }

    @Test
    public void addedLiabilityAccountIsInList() {
        String type = "Liability";
        chartOfAccounts.add("Liability Account", type);
        assertEquals("Liability Account", database.getAccountName(
                database.getLastAccountNumber(type)));
        chartOfAccounts.remove(2000);
        accountsListIsEmpty();
    }

    @Test
    public void accountNumberIsAppropriateForLiabilityAccounts() {
        String type = "Liability";
        chartOfAccounts.add("Liability Account", type);
        assertEquals(2000, database.getLastAccountNumber(type), 0.0);
        chartOfAccounts.remove(2000);
        accountsListIsEmpty();
    }

    @Test
    public void balanceForLiabilityAccountsCanBeSet() {
        String type = "Liability";
        chartOfAccounts.add("Liability Account", type);
        chartOfAccounts.updateBalance(database.getLastAccountNumber(type), 1.23);
        assertEquals(1.23, database.getAccountBalance(
                database.getLastAccountNumber(type)), 0.0);
        chartOfAccounts.updateBalance(2000, -1.23);
        chartOfAccounts.remove(2000);
        accountsListIsEmpty();
    }

//     Tests for all Equity accounts list
    @Test
    public void equityListIsEmptyAtBeginning() {
        String type = "Equity";
        assertEquals(0, database.selectByAccountType(type).size());
    }

    @Test
    public void addingAccountGrowsEquityListByOne() {
        String type = "Equity";
        chartOfAccounts.add("Equity Account", type);
        assertEquals(1, database.selectByAccountType(type).size());
        chartOfAccounts.remove(3000);
        accountsListIsEmpty();
    }

    @Test
    public void addedEquityAccountIsInList() {
        String type = "Equity";
        chartOfAccounts.add("Equity Account", type);
        assertEquals("Equity Account", database.getAccountName(
                database.getLastAccountNumber(type)));
        chartOfAccounts.remove(3000);
        accountsListIsEmpty();
    }

    @Test
    public void accountNumberIsAppropriateForEquityAccounts() {
        String type = "Equity";
        chartOfAccounts.add("Equity Account", type);
        assertEquals(3000, database.getLastAccountNumber(type), 0.0);
        chartOfAccounts.remove(3000);
        accountsListIsEmpty();
    }

    @Test
    public void balanceForEquityAccountsCanBeSet() {
        String type = "Equity";
        chartOfAccounts.add("Equity Account", type);
        chartOfAccounts.updateBalance(database.getLastAccountNumber(type), 1.23);
        assertEquals(1.23, database.getAccountBalance(
                database.getLastAccountNumber(type)), 0.0);
        chartOfAccounts.updateBalance(3000, -1.23);
        chartOfAccounts.remove(3000);
        accountsListIsEmpty();
    }

    @Test
    public void accountBalancesCanBeUpdatedByUsingAccountNumber() {
        String type = "Asset";
        chartOfAccounts.add("Account 1", type);
        chartOfAccounts.add("Account 2", type);
        chartOfAccounts.updateBalance(1000,1.23);
        chartOfAccounts.updateBalance(1010, 2.46);
        assertEquals(1.23, database.getAccountBalance(1000), 0.0);
        assertEquals(2.46, database.getAccountBalance(1010), 0.0);
        chartOfAccounts.updateBalance(1000, -1.23);
        chartOfAccounts.updateBalance(1010, -2.46);
        chartOfAccounts.remove(1000);
        chartOfAccounts.remove(1010);
        accountsListIsEmpty();
    }

    @Test
    public void doubleEntryFunctionWorks() {
        chartOfAccounts.add("Cash", "Asset");
        chartOfAccounts.add("Accounts Payable", "Liability");
        chartOfAccounts.doubleEntry(1000, 2000, 12345);
        assertEquals(12345, database.getAccountBalance(1000), 0.0);
        assertEquals(12345, database.getAccountBalance(2000), 0.0);
        chartOfAccounts.doubleEntry(1000, 2000, -12345);
        chartOfAccounts.remove(1000);
        chartOfAccounts.remove(2000);
        accountsListIsEmpty();
    }

    @Test
    public void checkForAccountExistsFunctionsProperly() {
        chartOfAccounts.add("Cash", "Asset");
        assertTrue(chartOfAccounts.checkAccountExists(1000));
        assertFalse(chartOfAccounts.checkAccountExists(2000));
        chartOfAccounts.remove(1000);
        accountsListIsEmpty();
    }

    public static void delete(File file) {
        if (file.isDirectory()) {

            // Directly delete the file if directory is empty
            if (Objects.requireNonNull(file.list()).length==0) {

                file.delete();
                System.out.println("Deleting folder : "
                        + file.getAbsolutePath());

            } else {

                // List all the files in directory
                File[] files = file.listFiles();

                assert files != null;
                for (File temp : files) {
                    // Recursive delete
                    delete(temp);
                }

                // Check directory again, if we find it empty, delete it
                if (Objects.requireNonNull(file.list()).length==0) {
                    file.delete();
                    System.out.println("Deleting folder : "
                            + file.getAbsolutePath());
                }
            }

        } else {
            // If a file exists, then we can directly delete it
            file.delete();
            System.out.println("Deleting file  : " + file.getAbsolutePath());
        }
    }

    @After
    public void endTesting() {
        File directory = new File(PATH);

        delete(directory);
    }
}