import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadTable {
    private JTable table1;
    public static List<String[]> readData(String filePath) {
        List<String[]> data = new ArrayList<>();
        String[] headers = {"Użytkownik", "Kategoria", "Nazwa", "Ilość", "Dni przechowania"};
        data.add(headers);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                if (row.length > 0 && row[0].equals(Login.login1)) {
                    data.add(row);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    public static void updateTableModel(JTable table, List<String[]> data) {
        if (data.isEmpty()) return;

        String[] columnNames = data.getFirst();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(columnNames);
        data.removeFirst();

        model.setRowCount(0);

        for (String[] row : data) {
            model.addRow(row);
        }
    }
}
