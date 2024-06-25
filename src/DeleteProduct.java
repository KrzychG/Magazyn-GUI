import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeleteProduct extends JFrame{
    private JPanel panel1;
    private JButton exitButton;
    private JTable table1;
    private int width = 800, height = 800;
    public DeleteProduct(){
        super("Usuwanie przedmiotu");
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(width,height);
        this.setVisible(true);
        this.setLocationRelativeTo(null);


        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        panel1.add(new JScrollPane(table1));
        table1.setModel(new DefaultTableModel());

        String filePath = "BazaDanych.txt";
        List<String[]> data = readData(filePath);
        updateTableModel(data);

    }


    public static List<String[]> readData(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    String[] headers = line.split(",");
                    data.add(headers);
                    isFirstLine = false;
                }
                else {
                    String[] row = line.split(",");
                    if (row.length > 0 && row[0].equals(Login.login1)) {
                        data.add(row);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    private void updateTableModel(List<String[]> data) {
        if (data.isEmpty()) return;

        String[] columnNames = data.getFirst();
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setColumnIdentifiers(columnNames);
        data.removeFirst();

        model.setRowCount(0);

        for (String[] row : data) {
            model.addRow(row);
        }
    }
    public static void main(String[] args) {
        DeleteProduct usun = new DeleteProduct();
    }
}
