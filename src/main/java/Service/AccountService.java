package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account) {
        if (isUsernameTaken(account.getUsername())) {
            return null;
        }
        account = accountDAO.insertAccount(account);
        return account;
    }
    
    private boolean isUsernameTaken(String username) {
        boolean isTaken = accountDAO.isUsernameTaken(username);
        return isTaken;
    }

    public Account authenticate(String username, String password) {
        Account existingAccount = accountDAO.getByUsername(username);

        if (existingAccount != null && existingAccount.getPassword().equals(password)) {
            return existingAccount;
        }
        return null;
    }
    
    public boolean isValidAccount(int accountId) {
        Account existingAccount = accountDAO.getByAccountId(accountId);
        return existingAccount != null;
    }
    

}