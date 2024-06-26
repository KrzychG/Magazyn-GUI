import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteProduct extends JFrame{
    private JPanel panel1;
    private JButton exitButton;
    private JTable table1;
    private JTextField nazwaField1;
    private JTextField ileField2;
    private JButton usuńButton;
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
        table1.setBackground(Color.DARK_GRAY);

        String filePath = "BazaDanych.txt";
        List<String[]> data = readData(filePath);
        updateTableModel(data);

        usuńButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> fileContent = new ArrayList<>();
                String filePath1 = "BazaDanych.txt";
                String name = nazwaField1.getText();
                boolean flag = true;

                int num = Integer.parseInt(ileField2.getText());

                try (BufferedReader reader = new BufferedReader(new FileReader(filePath1))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts[0].trim().equals(Login.login1.trim()) && parts[2].trim().equals(name.trim()) && flag) {
                            int num2 = Integer.parseInt(parts[3]) - num;
                            parts[3] = String.valueOf(num2);
                            flag = false;
                        }
                        fileContent.add(String.join(",", parts));
                    }

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath1))) {
                        for (String fileLine : fileContent) {
                            writer.write(fileLine);
                            writer.newLine();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    List<String[]> updatedData = readData(filePath1);
                    updateTableModel(updatedData);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
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
