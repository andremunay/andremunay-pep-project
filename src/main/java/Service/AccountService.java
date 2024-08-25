package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    // Constructor for creating a new AccountService with a new AccountDAO
    public AccountService() {
        accountDAO = new AccountDAO();
    }
    // Constructor for creating an AccountService with a provided AccountDAO
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    // Use the AccountDAO to add a new account to the database if the username is available
    public Account addAccount(Account account) {
        if (isUsernameTaken(account.getUsername())) {
            return null;
        }
        account = accountDAO.insertAccount(account);
        return account;
    }
    
    // Use the AccountDAO to authenticate an account by username
    private boolean isUsernameTaken(String username) {
        boolean isTaken = accountDAO.isUsernameTaken(username);
        return isTaken;
    }

    // Use the AccountDAO to retrieve an account by its ID and authenticate the username and password
    public Account authenticate(String username, String password) {
        Account existingAccount = accountDAO.getByUsername(username);

        if (existingAccount != null && existingAccount.getPassword().equals(password)) {
            return existingAccount;
        }
        return null;
    }
    
    // Use the AccountDAO to retrieve an account by its ID and validate it's a valid account
    public boolean isValidAccount(int accountId) {
        Account existingAccount = accountDAO.getByAccountId(accountId);
        return existingAccount != null;
    }
    

}