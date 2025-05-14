package user_interface;

import javax.swing.*;

import lib.Account;

public class logins extends Login_Form {

    @Override
    protected void onLogin(String username, String password) {
        boolean isValid = false;

        for (Account account : userAccounts) {
            if (account.getUsername().equals(username) && account.checkPassword(password)) {
                isValid = true;
                break;
            }
        }

        if (!isValid) {
            for (Account account : adminAccounts) {
                if (account.getUsername().equals(username) && account.checkPassword(password)) {
                    isValid = true;
                    break;
                }
            }
        }

        if (isValid) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            this.dispose(); 
            new MainFrame().setVisible(true); 
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }


    @Override
    protected void onRegister() {
        JOptionPane.showMessageDialog(this, "Go to registration.");
    }

    @Override
    protected void onAdminLogin() {
        String username = JOptionPane.showInputDialog(this, "Enter Admin Username:");
        String password = JOptionPane.showInputDialog(this, "Enter Admin Password:");

        if (username == null || password == null) return;

        boolean isAdminValid = false;
        for (Account account : adminAccounts) {
            if (account.getUsername().equals(username) && account.checkPassword(password)) {
                isAdminValid = true;
                break;
            }
        }

        if (isAdminValid) {
            JOptionPane.showMessageDialog(this, "Admin login successful!");
            this.dispose();
            JFrame adminFrame = new JFrame("Admin Panel");
            adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            adminFrame.setSize(1000, 600);
            adminFrame.setLocationRelativeTo(null);
            adminFrame.add(new AdminPanel()); // You should implement AdminPanel as a JPanel with tabbed panes
            adminFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid admin credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(logins::new);
    }
}