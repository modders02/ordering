package user_interface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class MainFrame extends JFrame {
	private final List<MenuItem> menuItems = new ArrayList<>();
	private final List<MenuItem> cart = new ArrayList<>();

	public MainFrame() {
		setTitle("Chowking Order System");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		menuItems.add(new MainDish("Chao Fan", 59));
		menuItems.add(new MainDish("Siomai-rice", 70));
		menuItems.add(new MainDish("Lauriat", 120));
		menuItems.add(new MainDish("Sweet and Sour Pork", 110));
		menuItems.add(new MainDish("Beef Mami", 95));
		menuItems.add(new MainDish("Wonton Mami", 85));
		menuItems.add(new MainDish("Lumpiang Shanghai", 60));
		menuItems.add(new MainDish("Yang Chow Fried Rice", 75));
		menuItems.add(new MainDish("Pork Chao Fan", 69));
		menuItems.add(new MainDish("Chicken Chao Fan", 75));
		menuItems.add(new MainDish("Spicy Chao Fan", 80));
		menuItems.add(new MainDish("Egg Fried Rice", 50));
		menuItems.add(new MainDish("Spicy Siomai Rice", 78));
		menuItems.add(new MainDish("Beef Wanton Noodles", 90));
		menuItems.add(new MainDish("Tofu Rice Bowl", 65));
		menuItems.add(new MainDish("Fish Fillet Lauriat", 130));
		menuItems.add(new MainDish("Pancit Canton", 60));
		menuItems.add(new MainDish("Pancit Bihon", 65));
		menuItems.add(new MainDish("Chopsuey", 70));
		menuItems.add(new MainDish("Chicken Curry", 115));
		menuItems.add(new MainDish("Spicy Wings", 120));
		menuItems.add(new MainDish("Steamed Rice", 40));
		menuItems.add(new MainDish("Beef Broccoli", 130));

		// Beverages
		menuItems.add(new Beverage("Coke", 25));
		menuItems.add(new Beverage("Sprite", 25));
		menuItems.add(new Beverage("Iced-Tea", 30));
		menuItems.add(new Beverage("Royal", 25));
		menuItems.add(new Beverage("Mountain Dew", 25));
		menuItems.add(new Beverage("Bottled Water", 20));
		menuItems.add(new Beverage("Pineapple Juice", 30));
		menuItems.add(new Beverage("Mango Shake", 40));
		menuItems.add(new Beverage("Buko Juice", 35));
		menuItems.add(new Beverage("Cucumber Lemonade", 38));
		menuItems.add(new Beverage("Sarsi", 25));
		menuItems.add(new Beverage("Green Tea", 32));
		menuItems.add(new Beverage("Red Iced Tea", 30));
		menuItems.add(new Beverage("Wintermelon Milk Tea", 55));
		menuItems.add(new Beverage("Taro Milk Tea", 55));
		menuItems.add(new Beverage("Matcha Milk Tea", 58));
		menuItems.add(new Beverage("Strawberry Smoothie", 50));
		menuItems.add(new Beverage("Chocolate Milk", 35));
		menuItems.add(new Beverage("Hot Coffee", 20));
		menuItems.add(new Beverage("Hot Tea", 18));
		menuItems.add(new Beverage("Calamansi Juice", 28));
		menuItems.add(new Beverage("Iced Coffee", 40));
		menuItems.add(new Beverage("Cocoa Drink", 38));

		// Desserts
		menuItems.add(new Dessert("Halo-Halo", 60));
		menuItems.add(new Dessert("Buchi", 40));
		menuItems.add(new Dessert("Leche-flan", 50));
		menuItems.add(new Dessert("Mais con Yelo", 55));
		menuItems.add(new Dessert("Sago't Gulaman", 45));
		menuItems.add(new Dessert("Ice Cream Sundae", 35));
		menuItems.add(new Dessert("Chocolate Cake", 60));
		menuItems.add(new Dessert("Ube Cake", 65));
		menuItems.add(new Dessert("Taho", 25));
		menuItems.add(new Dessert("Macapuno Delight", 50));
		menuItems.add(new Dessert("Fruit Salad", 55));
		menuItems.add(new Dessert("Banana Turon", 40));
		menuItems.add(new Dessert("Mango Float", 60));
		menuItems.add(new Dessert("Cassava Cake", 50));
		menuItems.add(new Dessert("Kutsinta", 30));
		menuItems.add(new Dessert("Puto", 25));
		menuItems.add(new Dessert("Egg Pie", 45));
		menuItems.add(new Dessert("Buko Pandan", 55));
		menuItems.add(new Dessert("Brownies", 40));
		menuItems.add(new Dessert("Crema de Fruta", 60));
		menuItems.add(new Dessert("Sticky Rice with Mango", 65));
		menuItems.add(new Dessert("Banana Split", 70));
		menuItems.add(new Dessert("Gelatin Cup", 20));

		// Create tabbed pane with categories
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
	    JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(0, 8, 10, 10));  // Set GridLayout with 8 columns, 10px horizontal and vertical gap

	    for (MenuItem item : menuItems) {
	        if (type.isInstance(item)) {
	            panel.add(new ItemPanel(item));  // Add item panel to the grid
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
			String[] extensions = { ".png", ".jpg", ".jpeg", ".gif" };

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
		JOptionPane.showMessageDialog(this, receipt.toString(), "Receipt", JOptionPane.INFORMATION_MESSAGE);
	}

	private void clearCart() {
		int confirm = JOptionPane.showConfirmDialog(this, "Start a new order? This will clear the cart.",
				"Confirm New Order", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			cart.clear();
			JOptionPane.showMessageDialog(this, "Cart cleared. Ready for new order.");
		}
	}

	// Base MenuItem interface
	public interface MenuItem {
		String getName();

		double getPrice();
	}

	public static class MainDish implements MenuItem {
		private final String name;
		private final double price;

		public MainDish(String name, double price) {
			this.name = name;
			this.price = price;
		}

		public String getName() {
			return name;
		}

		public double getPrice() {
			return price;
		}
	}

	public static class Beverage implements MenuItem {
		private final String name;
		private final double price;

		public Beverage(String name, double price) {
			this.name = name;
			this.price = price;
		}

		public String getName() {
			return name;
		}

		public double getPrice() {
			return price;
		}
	}

	public static class Dessert implements MenuItem {
		private final String name;
		private final double price;

		public Dessert(String name, double price) {
			this.name = name;
			this.price = price;
		}

		public String getName() {
			return name;
		}

		public double getPrice() {
			return price;
		}
	}

}
