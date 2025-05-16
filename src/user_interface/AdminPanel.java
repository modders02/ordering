package user_interface;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.util.List;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class AdminPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5249744851877461511L;
	private static final String RECEIPT_FOLDER_PATH = System.getProperty("user.home") + "/Desktop/Chowking_Receipts";
    private JTabbedPane tabbedPane;

    public AdminPanel() {
        setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();

        tabbedPane.add("Daily Sales Report", createDailySalesPanel());
        tabbedPane.add("Most Ordered Items", createMostOrderedPanel());
        tabbedPane.add("Customer Order History", createOrderHistoryPanel());
        tabbedPane.add("Kitchen", createKitchenPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createDailySalesPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> fileList = new JList<>(listModel);

        JTextArea fileContentArea = new JTextArea();
        fileContentArea.setEditable(false);
        JScrollPane contentScrollPane = new JScrollPane(fileContentArea);

        JTextArea summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        summaryArea.setLineWrap(true);
        summaryArea.setWrapStyleWord(true);
        JScrollPane summaryScrollPane = new JScrollPane(summaryArea);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(summaryScrollPane, BorderLayout.CENTER);

        File folder = new File(RECEIPT_FOLDER_PATH);
        Map<String, Integer> orderCountsByDate = new TreeMap<>();
        int yearlyTotal = 0;

        if (!folder.exists() || !folder.isDirectory()) {
            JOptionPane.showMessageDialog(panel, "Receipt folder not found: " + RECEIPT_FOLDER_PATH);
        } else {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && !file.getName().equalsIgnoreCase("receipt_counter.txt")) {
                        String date = extractDateFromFile(file);
                        if (date == null) date = "Unknown Date";
                        orderCountsByDate.put(date, orderCountsByDate.getOrDefault(date, 0) + 1);
                    }
                }

                for (Map.Entry<String, Integer> entry : orderCountsByDate.entrySet()) {
                    listModel.addElement(entry.getKey());
                    File[] filesAgain = folder.listFiles();
                    if (filesAgain != null) {
                        for (File file : filesAgain) {
                            String date = extractDateFromFile(file);
                            if (entry.getKey().equals(date)) {
                                listModel.addElement("  " + file.getName());
                            }
                        }
                    }
                }

                for (int count : orderCountsByDate.values()) yearlyTotal += count;

                StringBuilder summary = new StringBuilder();
                for (Map.Entry<String, Integer> entry : orderCountsByDate.entrySet()) {
                    summary.append("We have total of ").append(entry.getValue())
                            .append(" orders from date of ").append(entry.getKey()).append(".\n");
                }
                summary.append("With total of ").append(yearlyTotal)
                        .append(" orders this year and our dear customer is loved it.\n\n");

                summary.append("Marineth Ramos\n\n");
                summary.append("I had an amazing experience ordering from this Chowking system. The interface was so smooth,\n");
                summary.append("and it felt like I was ordering in person! Everything arrived hot and exactly the way I wanted — 10/10 experience.\n\n\n");

                summary.append("Lyka Rosal\n\n");
                summary.append("What I love most is how user-friendly the menu layout is.\n");
                summary.append("It made it easy to order for my whole family without confusion.\n");
                summary.append("Thank you for bringing restaurant-quality service right to our fingertips!\n\n\n");

                summary.append("Grace Fortich\n\n");
                summary.append("The service was incredibly fast and the food was absolutely delicious.\n");
                summary.append("I didn’t expect the system to be this efficient, but it completely exceeded my expectations.\n");
                summary.append("Kudos to the developers — you made our meal time so special.\n");

                summaryArea.setText(summary.toString());
            }
        }

        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = fileList.getSelectedValue();
                if (selected != null && !selected.equals("No receipt files found.") && !isDateHeader(selected)) {
                    String fileName = selected.trim();
                    File selectedFile = new File(RECEIPT_FOLDER_PATH, fileName);
                    try {
                        String content = new String(Files.readAllBytes(selectedFile.toPath()));
                        fileContentArea.setText(content);
                    } catch (IOException ex) {
                        fileContentArea.setText("Error reading file: " + ex.getMessage());
                    }
                } else {
                    fileContentArea.setText("");
                }
            }
        });

        JSplitPane splitPaneCenter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(fileList), contentScrollPane);
        splitPaneCenter.setDividerLocation(250);

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                splitPaneCenter, rightPanel);
        mainSplitPane.setDividerLocation(700);

        panel.add(mainSplitPane, BorderLayout.CENTER);
        return panel;
    }

    private boolean isDateHeader(String text) {
        return text.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    private String extractDateFromFile(File file) {
        String name = file.getName();
        if (name.startsWith("receipt_") && name.endsWith(".txt")) {
            int underscore1 = name.indexOf('_');
            int underscore2 = name.indexOf('_', underscore1 + 1);
            if (underscore2 > underscore1) {
                String datePart = name.substring(underscore1 + 1, underscore2);
                if (datePart.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    return datePart;
                }
            }
        }
        return null;
    }


    private JPanel createMostOrderedPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columns = {"Item Name", "Quantity", "Total Price (Php)"};
        Map<String, int[]> itemStats = new HashMap<>();

        File folder = new File(RECEIPT_FOLDER_PATH);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".txt") && !file.getName().equalsIgnoreCase("receipt_counter.txt")) {
                        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                            String line;
                            while ((line = br.readLine()) != null) {
                                line = line.trim();
                                if (line.matches("\\d+x .+ - Php \\d+(\\.\\d{2})?")) {
                                    int xIndex = line.indexOf('x');
                                    int phpIndex = line.indexOf("Php");

                                    int quantity = Integer.parseInt(line.substring(0, xIndex).trim());
                                    String itemName = line.substring(xIndex + 1, phpIndex - 1).trim();
                                    double unitPrice = Double.parseDouble(line.substring(phpIndex + 3).trim());

                                    int[] stats = itemStats.getOrDefault(itemName, new int[2]);
                                    stats[0] += quantity; // total quantity
                                    stats[1] += (int)(quantity * unitPrice * 100); // store in cents
                                    itemStats.put(itemName, stats);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        List<Map.Entry<String, int[]>> sortedItems = new ArrayList<>(itemStats.entrySet());
        sortedItems.sort((a, b) -> Integer.compare(b.getValue()[0], a.getValue()[0]));

        // Fill data array
        Object[][] tableData = new Object[sortedItems.size()][3];
        int i = 0;
        for (Map.Entry<String, int[]> entry : sortedItems) {
            String itemName = entry.getKey();
            int quantity = entry.getValue()[0];
            double totalPrice = entry.getValue()[1] / 100.0;

            tableData[i][0] = itemName;
            tableData[i][1] = quantity;
            tableData[i][2] = String.format("%.2f", totalPrice);
            i++;
        }

        JTable table = new JTable(tableData, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }



    private JPanel createOrderHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Order History");
        JTree tree = new JTree(root);

        File folder = new File(RECEIPT_FOLDER_PATH);
        if (!folder.exists() || !folder.isDirectory()) {
            root.add(new DefaultMutableTreeNode("No receipt folder found."));
        } else {
            File[] files = folder.listFiles();
            if (files == null || files.length == 0) {
                root.add(new DefaultMutableTreeNode("No receipt files found."));
            } else {
                for (File file : files) {
                    if (file.isFile() && !file.getName().equalsIgnoreCase("receipt_counter.txt")) {
                        root.add(new DefaultMutableTreeNode(file.getName()));
                    }
                }
            }
        }

        JScrollPane treeScroll = new JScrollPane(tree);
        JTextArea fileContent = new JTextArea();
        fileContent.setEditable(false);
        JScrollPane contentScroll = new JScrollPane(fileContent);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScroll, contentScroll);
        splitPane.setDividerLocation(300);

        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node == null) return;

            if (node.isLeaf() && !node.toString().equals("Order History")) {
                File selectedFile = new File(RECEIPT_FOLDER_PATH, node.toString());
                if (selectedFile.exists()) {
                    try {
                        String content = new String(Files.readAllBytes(selectedFile.toPath()));
                        fileContent.setText(content);
                    } catch (IOException ex) {
                        fileContent.setText("Error reading file: " + ex.getMessage());
                    }
                }
            }
        });

        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createKitchenPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] orderColumns = {"Order ID", "Item Name", "Quantity", "Status"};
        Object[][] orderData = {
            {"001", "Chowking Fried Rice", 2, "Preparing"},
            {"002", "Lumpiang Shanghai", 3, "Ready"},
            {"003", "Pork Siomai", 5, "Cooking"},
            {"004", "Halo-Halo", 1, "Served"}
        };
        JTable orderTable = new JTable(orderData, orderColumns);
        JScrollPane orderScrollPane = new JScrollPane(orderTable);

        String[] staffColumns = {"Name", "Position", "Details"};
        String[][] kitchenData = {
            {"Elmer Pobadora", "Kitchen Manager", "10+ years of experience in kitchen operations and team management."},
            {"Marineth Ramos", "Head Chef", "Expert in Chinese-Filipino fusion cuisine with 8 years of culinary leadership."},
            {"Ezekiel Alinsunurin", "Branch Kitchen Manager", "Oversees kitchen safety and inventory across multiple branches."},
            {"Jayson Inot", "Kitchen Staff", "Specializes in meal prep and sauce blending with 3 years of experience."},
            {"Grace Fortich", "Kitchen Staff", "Experienced in deep frying and maintaining cleanliness in the kitchen."},
            {"Lyka Rosal", "Kitchen Staff", "Handles dessert preparation and plating with precision and care."},
            {"Japhet Delmundo", "Kitchen Staff", "Expert in rice and noodle dishes, efficient in high-pressure environments."},
            {"Jerico Papagayo", "Kitchen Staff", "Maintains kitchen hygiene and assists in bulk meal preparations."},
            {"Katrina Carrillo", "Kitchen Staff", "Prepares ingredients and ensures fast service during peak hours."},
            {"Raymar Garnica", "Kitchen Staff", "Focused on grilling and marination, trained in fast-paced cooking tasks."}
        };
        JTable staffTable = new JTable(kitchenData, staffColumns);
        JScrollPane staffScrollPane = new JScrollPane(staffTable);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, orderScrollPane, staffScrollPane);
        splitPane.setDividerLocation(150);
        splitPane.setResizeWeight(0.5);  
        splitPane.setContinuousLayout(true);

        panel.add(splitPane, BorderLayout.CENTER);

        return panel;
    }

}
