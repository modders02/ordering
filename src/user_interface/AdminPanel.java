package user_interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class AdminPanel extends JPanel {
    private static final String RECEIPT_FOLDER_PATH = System.getProperty("user.home") + "/Desktop/Chowking_Receipts";

    private JTabbedPane tabbedPane;

    public AdminPanel() {
        setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();

        tabbedPane.add("Daily Sales Report", createDailySalesPanel());
        tabbedPane.add("Most Ordered Items", createMostOrderedPanel());
        tabbedPane.add("Customer Order History", createOrderHistoryPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    // Tab 1 - Daily Sales Report
    private JScrollPane createDailySalesPanel() {
        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportArea);

        File folder = new File("receipts/");
        if (folder.exists()) {
            Map<String, Integer> salesPerDay = new TreeMap<>();
            for (File file : folder.listFiles()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("Date: ")) {
                            String date = line.substring(6, 16); // yyyy-MM-dd
                            salesPerDay.put(date, salesPerDay.getOrDefault(date, 0) + 1);
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            salesPerDay.forEach((date, count) -> reportArea.append(date + " : " + count + " items\n"));
        }

        return scrollPane;
    }

    // Tab 2 - Most Ordered Items
    private JPanel createMostOrderedPanel() {
        Map<String, Integer> itemCounts = getItemOrderCounts(new File(RECEIPT_FOLDER_PATH));
        return new MostOrderedGraphPanel();
    }

    // Helper method to count ItemID occurrences
    private Map<String, Integer> getItemOrderCounts(File folder) {
        Map<String, Integer> itemCounts = new HashMap<>();

        if (!folder.exists() || !folder.isDirectory()) {
            JOptionPane.showMessageDialog(this, "Receipt folder not found: " + RECEIPT_FOLDER_PATH);
            return itemCounts;
        }

        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("ItemID:")) {
                            String itemId = line.split(":")[1].trim();
                            itemCounts.put(itemId, itemCounts.getOrDefault(itemId, 0) + 1);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return itemCounts;
    }

    // Custom panel for bar graph
    class MostOrderedGraphPanel extends JPanel {
        private static final String RECEIPT_FOLDER_PATH = System.getProperty("user.home") + "/Desktop/Chowking_Receipts";
        private final Map<String, Integer> itemCounts = new HashMap<>();

        public MostOrderedGraphPanel() {
            setPreferredSize(new Dimension(800, 500));
            loadItemCountsFromReceipts();
        }

        private void loadItemCountsFromReceipts() {
            File folder = new File(RECEIPT_FOLDER_PATH);
            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile() && !file.getName().equalsIgnoreCase("receipt_counter.txt")) {
                            processReceiptFile(file);
                        }
                    }
                }
            }
        }

        private void processReceiptFile(File file) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                boolean inOrderSection = false;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.contains("Order Summary:")) {
                        inOrderSection = true;
                        continue;
                    }
                    if (inOrderSection) {
                        if (line.isEmpty() || line.startsWith("Total") || line.contains("Date:")) continue;
                        // Example line: "Chao Fan x2 = Php80"
                        String[] parts = line.split("x");
                        if (parts.length >= 2) {
                            String itemName = parts[0].trim();
                            itemCounts.put(itemName, itemCounts.getOrDefault(itemName, 0) + 1);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (itemCounts.isEmpty()) {
                g.drawString("No data available.", 50, 50);
                return;
            }

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int barWidth = 60;
            int gap = 30;
            int bottomPadding = 80;
            int topPadding = 50;
            int x = 50;

            int maxCount = Collections.max(itemCounts.values());

            g.setFont(new Font("SansSerif", Font.BOLD, 16));
            g.drawString("Most Ordered Items", 50, 30);

            List<Map.Entry<String, Integer>> entries = new ArrayList<>(itemCounts.entrySet());
            entries.sort((a, b) -> b.getValue() - a.getValue());

            for (Map.Entry<String, Integer> entry : entries) {
                String itemId = entry.getKey();
                int count = entry.getValue();
                int barHeight = (int) (((double) count / maxCount) * (height - topPadding - bottomPadding));

                g.setColor(new Color(255, 99, 71)); // Tomato Red
                g.fillRect(x, height - bottomPadding - barHeight, barWidth, barHeight);

                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 12));
                g.drawString(String.valueOf(count), x + 15, height - bottomPadding - barHeight - 5);

                g.setFont(new Font("Arial", Font.PLAIN, 11));
                g.drawString(itemId, x + 5, height - bottomPadding + 15);

                x += barWidth + gap;
            }
        }
    }

    // Tab 3 - Customer Order History
    private JPanel createOrderHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> fileList = new JList<>(listModel);

        JTextArea fileContentArea = new JTextArea();
        fileContentArea.setEditable(false);
        JScrollPane contentScrollPane = new JScrollPane(fileContentArea);

        File folder = new File(RECEIPT_FOLDER_PATH);
        if (!folder.exists() || !folder.isDirectory()) {
            JOptionPane.showMessageDialog(panel, "Receipt folder not found: " + RECEIPT_FOLDER_PATH);
        } else {
            File[] files = folder.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    if (file.isFile() && !file.getName().equals("receipt_counter.txt")) {
                        listModel.addElement(file.getName());
                    }
                }
            } else {
                listModel.addElement("No receipt files found.");
            }
        }

        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = fileList.getSelectedValue();
                if (selected != null && !selected.equals("No receipt files found.")) {
                    File selectedFile = new File(RECEIPT_FOLDER_PATH + "/" + selected);
                    try {
                        String content = new String(Files.readAllBytes(selectedFile.toPath()));
                        fileContentArea.setText(content);
                    } catch (IOException ex) {
                        fileContentArea.setText("Error reading file: " + ex.getMessage());
                    }
                }
            }
        });

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(fileList), contentScrollPane);
        splitPane.setDividerLocation(200);

        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
    }
}
