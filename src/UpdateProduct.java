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
    private JTextField textField2;
    private JTextField textField3;
    private JButton button1;
    private JTextField czasField4;
    private JComboBox comboBox1;
    private int width = 700, height = 800;

    public UpdateProduct() {
        super("Edycja przedmiotów");
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

        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nie posiadasz aktualnie żadnych przedmiotów", "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }

        table1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    comboBox1.setSelectedItem(table1.getValueAt(selectedRow, 1).toString());
                    textField2.setText(table1.getValueAt(selectedRow, 2).toString());
                    textField3.setText(table1.getValueAt(selectedRow, 3).toString());
                    czasField4.setText(table1.getValueAt(selectedRow, 4).toString());
                }
            }
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = table1.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Proszę wybrać przedmiot do edycji poprzez kliknięcie odpowiedniego wiersza.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                String newCategory = comboBox1.getSelectedItem().toString();
                String newName = textField2.getText();
                String newAmount = textField3.getText();
                String newTime = czasField4.getText();

                try {

                    if (newCategory.isEmpty() || newName.isEmpty() || newAmount.isEmpty() || newTime.isEmpty()) {
                        throw new Exception("Proszę wypełnić wszystkie wymagane pola.");
                    }

                    if (newName.isEmpty() || !newName.matches("[\\p{L} ]+")) {
                        throw new Exception("Niepoprawnie określona nazwa przedmiotu.");
                    }

                    int amount, time;
                    try {
                        amount = Integer.parseInt(newAmount);
                        if (amount <= 0) {
                            throw new Exception("Ilość musi być liczbą większą od zera.");
                        }
                    } catch (NumberFormatException nfe) {
                        throw new Exception("Ilość przedmiotów musi być liczbą całkowitą dodatnią.");
                    }
                    try {
                        time = Integer.parseInt(newTime);
                        if (time <= 0) {
                            throw new Exception("Czas przechowania musi być liczbą większą od zera.");
                        }
                    } catch (NumberFormatException nfe) {
                        throw new Exception("Czas przechowania musi być liczbą całkowitą dodatnią.");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String newPrice = CalculatePrice.calculatePrice(Integer.parseInt(newAmount), Integer.parseInt(newTime));

                table1.setValueAt(newCategory, selectedRow, 1);
                table1.setValueAt(newName, selectedRow, 2);
                table1.setValueAt(newAmount, selectedRow, 3);
                table1.setValueAt(newTime, selectedRow, 4);
                table1.setValueAt(newPrice, selectedRow, 5);

                updateFile(filePath);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public static List<String[]> readData(String filePath) {
        List<String[]> data = new ArrayList<>();
        String[] headers = {"Użytkownik", "Kategoria", "Nazwa", "Ilość", "Dni przechowania", "Cena"};
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
