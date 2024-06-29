import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsTables {

    public static List<String[]> readData(String filePath) {
        Map<String, Integer> userAmountItems = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                if (row.length > 3) {
                    String user = row[0];
                    int amount;
                    try {
                        amount = Integer.parseInt(row[3]);
                    } catch (NumberFormatException e) {
                        continue;
                    }

                    userAmountItems.put(user, userAmountItems.getOrDefault(user, 0) + amount);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String[]> data = new ArrayList<>();
        String[] headers = {"Użytkownik", "Łączna Ilość przedmiotów"};
        data.add(headers);

        for (Map.Entry<String, Integer> entry : userAmountItems.entrySet()) {
            data.add(new String[]{entry.getKey(), entry.getValue().toString()});
        }

        return data;
    }

    public static void updateTableModel(JTable table, List<String[]> data) {
        if (data.isEmpty()) return;

        String[] columnNames = data.get(0);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(columnNames);
        data.remove(0);

        model.setRowCount(0);

        for (String[] row : data) {
            model.addRow(row);
        }
    }
    public static List<String[]> readPenaltyData(String filePath) {
        List<String[]> penaltyData = new ArrayList<>();
        String[] headers = {"Użytkownik", "Kara"};

        penaltyData.add(headers);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                if (row.length == 2) {
                    penaltyData.add(row);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return penaltyData;
    }


    public static List<String[]> readCosts(String filePath) {
        double totalCost = 0.0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                if (row.length > 5) {
                    double cost;
                    try {
                        cost = Double.parseDouble(row[5]);
                    } catch (NumberFormatException e) {
                        continue;
                    }

                    totalCost += cost;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String[]> data = new ArrayList<>();
        String[] headers = {"Łączny Zysk (zł)"};
        data.add(headers);
        data.add(new String[]{String.format("%.2f", totalCost)});

        return data;
    }

    public static List<String[]> readAmounCosts(String filePath) {
        int totalAmount = 0;
        double totalCost = 0.0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                if (row.length > 5) {
                    int quantity;
                    double cost;
                    try {
                        quantity = Integer.parseInt(row[3]);
                        cost = Double.parseDouble(row[5]);
                    } catch (NumberFormatException e) {
                        continue;
                    }

                    totalAmount += quantity;
                    totalCost += cost;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String[]> data = new ArrayList<>();
        String[] headers = {"Łączna Ilość przedmiotów", "Łączny Koszt (zł)"};
        data.add(headers);
        data.add(new String[]{String.valueOf(totalAmount), String.format("%.2f", totalCost)});

        return data;
    }

}
