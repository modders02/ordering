package lib;

import java.util.ArrayList;

public class LoginData {

    private final ArrayList<Account> userAccounts = new ArrayList<>();
    private final ArrayList<Account> adminAccounts = new ArrayList<>();

    public LoginData() {
        seedAccounts();
    }

    private void seedAccounts() {
        userAccounts.add(new Account("elmer", "elmer4321"));
        userAccounts.add(new Account("marineth", "aneth4321"));
        adminAccounts.add(new Account("admin", "dangalngbayan"));
    }

    public ArrayList<Account> getUserAccounts() {
        return userAccounts;
    }

    public ArrayList<Account> getAdminAccounts() {
        return adminAccounts;
    }
}