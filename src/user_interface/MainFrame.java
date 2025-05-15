package user_interface;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {
    private final List<MenuItem> menuItems = new ArrayList<>();
    private final List<MenuItem> cart = new ArrayList<>();

    public MainFrame() {
        setTitle("Chowking Order System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel for Logout button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.PINK);
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to log out?",
                    "Logout Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose(); // Close MainFrame
                new logins(); // <-- Replace with your actual login form class
            }
        });
        topPanel.add(logoutButton);
        add(topPanel, BorderLayout.NORTH);

        // Manually assigned menu item IDs
        menuItems.add(new MainDish(1, "Chao Fan", 59));
        menuItems.add(new MainDish(2, "Siomai-rice", 70));
        menuItems.add(new MainDish(3, "Lauriat", 120));
        menuItems.add(new MainDish(4, "Sweet and Sour Pork", 110));
        menuItems.add(new MainDish(5, "Beef Mami", 95));
        menuItems.add(new MainDish(6, "Wonton Mami", 85));
        menuItems.add(new MainDish(7, "Lumpiang Shanghai", 60));
        menuItems.add(new MainDish(8, "Yang Chow Fried Rice", 75));
        menuItems.add(new MainDish(9, "Pork Chao Fan", 69));
        menuItems.add(new MainDish(10, "Chicken Chao Fan", 75));
        menuItems.add(new MainDish(11, "Spicy Chao Fan", 80));
        menuItems.add(new MainDish(12, "Egg Fried Rice", 50));
        menuItems.add(new MainDish(13, "Spicy Siomai Rice", 78));
        menuItems.add(new MainDish(14, "Beef Wanton Noodles", 90));
        menuItems.add(new MainDish(15, "Tofu Rice Bowl", 65));
        menuItems.add(new MainDish(16, "Fish Fillet Lauriat", 130));
        menuItems.add(new MainDish(17, "Pancit Canton", 60));
        menuItems.add(new MainDish(18, "Pancit Bihon", 65));
        menuItems.add(new MainDish(19, "Chopsuey", 70));
        menuItems.add(new MainDish(20, "Chicken Curry", 115));
        menuItems.add(new MainDish(21, "Spicy Wings", 120));
        menuItems.add(new MainDish(22, "Steamed Rice", 40));
        menuItems.add(new MainDish(23, "Beef Broccoli", 130));

        // Beverages
        menuItems.add(new Beverage(24, "Coke", 25));
        menuItems.add(new Beverage(25, "Sprite", 25));
        menuItems.add(new Beverage(26, "Iced-Tea", 30));
        menuItems.add(new Beverage(27, "Royal", 25));
        menuItems.add(new Beverage(28, "Mountain Dew", 25));
        menuItems.add(new Beverage(29, "Bottled Water", 20));
        menuItems.add(new Beverage(30, "Pineapple Juice", 30));
        menuItems.add(new Beverage(31, "Mango Shake", 40));
        menuItems.add(new Beverage(32, "Buko Juice", 35));
        menuItems.add(new Beverage(33, "Cucumber Lemonade", 38));
        menuItems.add(new Beverage(34, "Sarsi", 25));
        menuItems.add(new Beverage(35, "Green Tea", 32));
        menuItems.add(new Beverage(36, "Red Iced Tea", 30));
        menuItems.add(new Beverage(37, "Wintermelon Milk Tea", 55));
        menuItems.add(new Beverage(38, "Taro Milk Tea", 55));
        menuItems.add(new Beverage(39, "Matcha Milk Tea", 58));
        menuItems.add(new Beverage(40, "Strawberry Smoothie", 50));
        menuItems.add(new Beverage(41, "Chocolate Milk", 35));
        menuItems.add(new Beverage(42, "Hot Coffee", 20));
        menuItems.add(new Beverage(43, "Hot Tea", 18));
        menuItems.add(new Beverage(44, "Calamansi Juice", 28));
        menuItems.add(new Beverage(45, "Iced Coffee", 40));
        menuItems.add(new Beverage(46, "Cocoa Drink", 38));

        // Desserts
        menuItems.add(new Dessert(47, "Halo-Halo", 60));
        menuItems.add(new Dessert(48, "Buchi", 40));
        menuItems.add(new Dessert(49, "Leche-flan", 50));
        menuItems.add(new Dessert(50, "Mais con Yelo", 55));
        menuItems.add(new Dessert(51, "Sago't Gulaman", 45));
        menuItems.add(new Dessert(52, "Ice Cream Sundae", 35));
        menuItems.add(new Dessert(53, "Chocolate Cake", 60));
        menuItems.add(new Dessert(54, "Ube Cake", 65));
        menuItems.add(new Dessert(55, "Taho", 25));
        menuItems.add(new Dessert(56, "Macapuno Delight", 50));
        menuItems.add(new Dessert(57, "Fruit Salad", 55));
        menuItems.add(new Dessert(58, "Banana Turon", 40));
        menuItems.add(new Dessert(59, "Mango Float", 60));
        menuItems.add(new Dessert(60, "Cassava Cake", 50));
        menuItems.add(new Dessert(61, "Kutsinta", 30));
        menuItems.add(new Dessert(62, "Puto", 25));
        menuItems.add(new Dessert(63, "Egg Pie", 45));
        menuItems.add(new Dessert(64, "Buko Pandan", 55));
        menuItems.add(new Dessert(65, "Brownies", 40));
        menuItems.add(new Dessert(66, "Crema de Fruta", 60));
        menuItems.add(new Dessert(67, "Sticky Rice with Mango", 65));
        menuItems.add(new Dessert(68, "Banana Split", 70));
        menuItems.add(new Dessert(69, "Gelatin Cup", 20));

        // Tabbed menu categories
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Main Dish", createCategoryPanel(MainDish.class));
        tabbedPane.addTab("Beverage", createCategoryPanel(Beverage.class));
        tabbedPane.addTab("Desserts", createCategoryPanel(Dessert.class));
        add(tabbedPane, BorderLayout.CENTER);

        // Bottom buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton cartButton = new JButton("Go to Cart");
        cartButton.setBackground(Color.PINK);
        JButton newOrderButton = new JButton("New Order");
        newOrderButton.setBackground(Color.PINK);

        cartButton.addActionListener(e -> showCart());
        newOrderButton.addActionListener(e -> clearCart());

        bottomPanel.add(cartButton);
        bottomPanel.add(newOrderButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createCategoryPanel(Class<? extends MenuItem> type) {
        JPanel panel = new JPanel(new GridLayout(0, 4, 20, 20));

        for (MenuItem item : menuItems) {
            if (type.isInstance(item)) {
                panel.add(new ItemPanel(item));
            }
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        JPanel container = new JPanel(new BorderLayout());
        container.add(scrollPane, BorderLayout.CENTER);

        return container;
    }

    private class ItemPanel extends JPanel {
        private final MenuItem item;

        public ItemPanel(MenuItem item) {
            this.item = item;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createLineBorder(Color.GRAY));
            setPreferredSize(new Dimension(150, 200));

            // Load image
            JLabel itemImageLabel;
            File imageFile = null;
            String baseName = item.getName().toLowerCase().replace(" ", "_");
            String[] extensions = { ".png", ".jpg", ".jpeg", ".gif", ".jfif" };

            for (String ext : extensions) {
                File testFile = new File("resources/" + baseName + ext);
                if (testFile.exists()) {
                    imageFile = testFile;
                    break;
                }
            }

            ImageIcon itemIcon;
            if (imageFile != null) {
                itemIcon = new ImageIcon(imageFile.getAbsolutePath());
                Image scaledImage = itemIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                itemIcon = new ImageIcon(scaledImage);
            } else {
                itemIcon = new ImageIcon();
            }

            add(itemImageLabel = new JLabel(itemIcon));
            itemImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel nameLabel = new JLabel(item.getName());
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(nameLabel);

            JLabel priceLabel = new JLabel("Php " + item.getPrice());
            priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(priceLabel);

            JButton addButton = new JButton("Add to Cart");
            addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            addButton.setBackground(Color.PINK);
            addButton.addActionListener(e -> addToCart(item));
            add(Box.createVerticalStrut(5));
            add(addButton);
        }

        private void addToCart(MenuItem item) {
            String quantityStr = JOptionPane.showInputDialog(MainFrame.this,
                    "Enter quantity for " + item.getName() + ":", "Quantity", JOptionPane.QUESTION_MESSAGE);

            if (quantityStr == null)
                return;

            int quantity;
            try {
                quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0)
                    throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MainFrame.this, "Invalid quantity. Please enter a positive number.");
                return;
            }

            String[] options = { "Dine In", "Take Out" };
            int choice = JOptionPane.showOptionDialog(MainFrame.this, "Choose order type", "Order Type",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (choice == -1)
                return;

            for (int i = 0; i < quantity; i++) {
                cart.add(item);
            }

            JOptionPane.showMessageDialog(MainFrame.this,
                    quantity + " x " + item.getName() + " added to cart (" + options[choice] + ").");
        }
    }

    private void showCart() {
        printReceipt();
    }

    private void printReceipt() {
        StringBuilder receipt = new StringBuilder("Receipt:\n\n");
        double total = 0;

        for (MenuItem item : cart) {
            receipt.append(item.getName()).append(" - Php ").append(item.getPrice()).append("\n");
            total += item.getPrice();
        }

        receipt.append("\nTotal: Php ").append(total);

        // Show in popup
        JOptionPane.showMessageDialog(this, receipt.toString(), "Receipt", JOptionPane.INFORMATION_MESSAGE);

        // === Save to Desktop Folder ===
        try {
            // Get Desktop folder path
            String userHome = System.getProperty("user.home");
            File receiptFolder = new File(userHome, "Desktop/Chowking_Receipts");

            // Create folder if it doesn't exist
            if (!receiptFolder.exists()) {
                receiptFolder.mkdirs();
            }

            // Save receipt to file with timestamp
            String fileName = "Receipt_" + System.currentTimeMillis() + ".txt";
            File receiptFile = new File(receiptFolder, fileName);

            java.nio.file.Files.write(receiptFile.toPath(), receipt.toString().getBytes());

            System.out.println("Receipt saved to: " + receiptFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save receipt: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearCart() {
        int confirm = JOptionPane.showConfirmDialog(this, "Clear cart?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            cart.clear();
            JOptionPane.showMessageDialog(this, "Cart cleared.");
        }
    }

    public interface MenuItem {
        int getId();
        String getName();
        double getPrice();
    }

    public static class MainDish implements MenuItem {
        private final int id;
        private final String name;
        private final double price;

        public MainDish(int id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public double getPrice() { return price; }
    }

    public static class Beverage implements MenuItem {
        private final int id;
        private final String name;
        private final double price;

        public Beverage(int id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public double getPrice() { return price; }
    }

    public static class Dessert implements MenuItem {
        private final int id;
        private final String name;
        private final double price;

        public Dessert(int id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public double getPrice() { return price; }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
