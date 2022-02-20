import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;

import application.Account;
import org.junit.Test;

public class AccountTest {
    private Account account;

    @Test
    public void newAccountSuccessfullyCreatesAccount() {
        account = new Account(1000, "Test application.Account", "Asset");
        assertEquals("Test application.Account", account.getName());
        assertEquals(1000, account.getAccountNumber());
    }

    @Test
    public void setBalanceFunctionsProperly() {
        account = new Account(1000, "Asset application.Account", "Asset");
        account.setBalance(123.45);
        assertEquals(123.45, account.getBalance(), 0.0);
    }

    @Test
    public void newAccountTypeIsAsset() {
        account = new Account(1000, "Asset application.Account", "Asset");
        assertEquals("Asset", account.getType());
    }

    @Test
    public void newAccountTypeIsLiability() {
        account = new Account(2000, "Liability application.Account", "Liability");
        assertEquals("Liability", account.getType());
    }

    @Test
    public void newAccountTypeIsEquity() {
        account = new Account(3000, "Equity application.Account", "Equity");
        assertEquals("Equity", account.getType());
    }

    @Test
    public void checkAccountEqualsOverrideMethodWorks() {
        account = new Account(2000, "Test application.Account", "Liability");
        assertEquals(new Account(2000, "Test application.Account", "Liability"), account);
        assertNotEquals(new Account(1000, "Test application.Account", "Asset"), account);
        assertNotEquals(new Account(1000, "Test application.Account", "Asset"), account);
    }
}