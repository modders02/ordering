package user_interface;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReceiptSaver {

    private static final String RECEIPT_FOLDER_PATH = System.getProperty("user.home") + "/Desktop/receipts";
    private static final String RECEIPT_COUNTER_FILE = RECEIPT_FOLDER_PATH + "/receipt_counter.txt";
    public static void saveReceiptToFile(List<MainFrame.MenuItem> cartItems, double total) {
        try {
            File receiptFolder = new File(RECEIPT_FOLDER_PATH);
            if (!receiptFolder.exists()) {
                receiptFolder.mkdirs();
            }

            int receiptId = getNextReceiptId();
            String receiptIdFormatted = String.format("%04d", receiptId);
            String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String fileName = RECEIPT_FOLDER_PATH + "/receipt_" + dateString + "_" + receiptIdFormatted + ".txt";
            PrintWriter writer = new PrintWriter(new FileWriter(fileName));
            writer.println("Receipt: " + receiptIdFormatted);
            writer.println();

            for (MainFrame.MenuItem item : cartItems) {
                writer.println(item.getName() + " - Php " + item.getPrice());
            }

            writer.println();
            writer.println("Total: Php " + total);
            writer.println("Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            writer.close();

            System.out.println("Receipt saved as " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getNextReceiptId() {
        int currentId = 0;

        File file = new File(RECEIPT_COUNTER_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                currentId = Integer.parseInt(reader.readLine());
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }

        int nextId = currentId + 1;

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println(nextId);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nextId;
    }
}
