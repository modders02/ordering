package user_interface;

import lib.Account;
import lib.LoginData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Login_Form extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3410468184681795646L;
	protected JTextField usernameField;
    protected JPasswordField passwordField;
    protected JButton btnLogin, btnRegister, btnAdmin;

    protected ArrayList<Account> userAccounts;
    protected ArrayList<Account> adminAccounts;

    private Image backgroundImage;

    public Login_Form() {
        setTitle("Chowking Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true); 

        try {
            backgroundImage = ImageIO.read(new File("resources\\bg.png")); 
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Background image not found.");
        }

        JPanel backgroundPanel = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 5300159756534002322L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setLayout(null); 
        setContentPane(backgroundPanel);

        setSize(1000, 600); 
        setLocationRelativeTo(null); 

        LoginData loginData = new LoginData();
        userAccounts = loginData.getUserAccounts();
        adminAccounts = loginData.getAdminAccounts();

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        Font headerFont = new Font("Segoe UI", Font.BOLD, 28);
        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);

        JLabel lblHeader = new JLabel("Chowking Login Portal", SwingConstants.CENTER);
        lblHeader.setFont(headerFont);
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setBounds(0, 40, getWidth(), 40);
        add(lblHeader);

        JPanel loginPanel = new JPanel(null);
        loginPanel.setBackground(new Color(255, 255, 255, 180));
        loginPanel.setBounds(300, 150, 400, 250);
        add(loginPanel);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(labelFont);
        lblUsername.setBounds(30, 30, 100, 30);
        loginPanel.add(lblUsername);

        usernameField = new JTextField();
        usernameField.setBounds(140, 30, 200, 30);
        loginPanel.add(usernameField);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(labelFont);
        lblPassword.setBounds(30, 80, 100, 30);
        loginPanel.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(140, 80, 200, 30);
        loginPanel.add(passwordField);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(140, 130, 200, 35);
        loginPanel.add(btnLogin);

        btnRegister = new JButton("Register");
        btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnRegister.setBorderPainted(false);
        btnRegister.setContentAreaFilled(false);
        btnRegister.setForeground(Color.BLUE);
        btnRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRegister.setBounds(250, 170, 80, 20);
        loginPanel.add(btnRegister);

        btnAdmin = new JButton("Admin Login");
        btnAdmin.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnAdmin.setBounds(830, 500, 130, 30);
        add(btnAdmin);

        btnLogin.addActionListener(e -> onLogin(usernameField.getText(), new String(passwordField.getPassword())));
        btnRegister.addActionListener(e -> onRegister());
        btnAdmin.addActionListener(e -> onAdminLogin());
        getRootPane().setDefaultButton(btnLogin);

    }

    protected void onAdminLogin() {
        String username = JOptionPane.showInputDialog(this, "Enter Admin Username:");
        JPasswordField passwordField = new JPasswordField();
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(new JLabel("Enter Admin Password:"), BorderLayout.NORTH);
        panel.add(passwordField, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(this, panel, "Admin Login",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String password = new String(passwordField.getPassword());

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
                adminFrame.add(new AdminPanel());
                adminFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid admin credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



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

    protected abstract void onRegister();
}
