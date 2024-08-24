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
        return accountDAO.isUsernameTaken(username);
    }
    

}