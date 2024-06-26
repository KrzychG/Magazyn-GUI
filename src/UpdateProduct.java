import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UpdateProduct extends JFrame {
    private JPanel panel1;
    private JButton exitButton;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JCheckBox do7DniCheckBox;
    private JCheckBox powyżej30DniCheckBox;
    private JCheckBox od7Do30CheckBox;
    private JButton button1;
    private int width = 800, height = 800;

    public UpdateProduct() {
        super("Edycja produktu");
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(width, height);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        panel1.add(new JScrollPane(table1));
        table1.setModel(new DefaultTableModel());
        table1.setBackground(Color.DARK_GRAY);

        String filePath = "BazaDanych.txt";
        List<String[]> data = readData(filePath);
        updateTableModel(data);

        table1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    textField1.setText(table1.getValueAt(selectedRow, 1).toString());
                    textField2.setText(table1.getValueAt(selectedRow, 2).toString());
                    textField3.setText(table1.getValueAt(selectedRow, 3).toString());
                    String czas = table1.getValueAt(selectedRow, 4).toString();
                    do7DniCheckBox.setSelected(czas.equals("Do 7 dni"));
                    powyżej30DniCheckBox.setSelected(czas.equals("Powyżej 30 dni"));
                    od7Do30CheckBox.setSelected(czas.equals("Od 7 do 30 dni"));
                }
            }
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    String newCategory = textField1.getText();
                    String newName = textField2.getText();
                    String newAmount = textField3.getText();
                    String newTime = getSelectedTime();

                    if (newCategory.isEmpty() || newName.isEmpty() || newAmount.isEmpty() || newTime.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Wszystkie pola muszą być wypełnione", "Błąd", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    table1.setValueAt(newCategory, selectedRow, 1);
                    table1.setValueAt(newName, selectedRow, 2);
                    table1.setValueAt(newAmount, selectedRow, 3);
                    table1.setValueAt(newTime, selectedRow, 4);

                    updateFile(filePath);
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private String getSelectedTime() {
        if (do7DniCheckBox.isSelected()) return "Do 7 dni";
        if (powyżej30DniCheckBox.isSelected()) return "Powyżej 30 dni";
        if (od7Do30CheckBox.isSelected()) return "Od 7 do 30 dni";
        return "";
    }

    public static List<String[]> readData(String filePath) {
        List<String[]> data = new ArrayList<>();
        String[] headers = {"Użytkownik", "Kategoria", "Nazwa", "Ilość", "Czas przechowania"};
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


    private void updateTableModel(List<String[]> data) {
        if (data.isEmpty()) return;

        String[] columnNames = data.get(0);
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setColumnIdentifiers(columnNames);
        data.remove(0);

        model.setRowCount(0);

        for (String[] row : data) {
            model.addRow(row);
        }
    }

    private void updateFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < model.getColumnCount(); j++) {
                    line.append(model.getValueAt(i, j).toString());
                    if (j < model.getColumnCount() - 1) {
                        line.append(",");
                    }
                }
                writer.write(line.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UpdateProduct edytuj = new UpdateProduct();
    }
}
