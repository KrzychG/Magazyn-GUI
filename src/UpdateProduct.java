import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UpdateProduct extends JFrame {
    private JPanel panel1;
    private JButton exitButton;
    private JTable table1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton button1;
    private JComboBox<String> comboBox1;
    private int width = 700, height = 800;
    private List<String[]> originalData;

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
        originalData = readData(filePath);
        updateTableModel(originalData);

        if (originalData.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nie posiadasz aktualnie żadnych przedmiotów", "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }

        table1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    comboBox1.setSelectedItem(table1.getValueAt(selectedRow, 1).toString());
                    textField2.setText(table1.getValueAt(selectedRow, 2).toString());
                    textField3.setText(table1.getValueAt(selectedRow, 3).toString());
                }
            }
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null,
                            "Proszę wybrać przedmiot do edycji poprzez kliknięcie odpowiedniego wiersza.",
                            "Informacja", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                String newCategory = comboBox1.getSelectedItem().toString();
                String newName = textField2.getText();
                String newAmount = textField3.getText();

                try {
                    if (newCategory.isEmpty() || newName.isEmpty() || newAmount.isEmpty()) {
                        throw new Exception("Proszę wypełnić wszystkie wymagane pola.");
                    }

                    if (!newName.matches("[\\p{L} ]+")) {
                        throw new Exception("Niepoprawnie określona nazwa przedmiotu.");
                    }

                    int amount;
                    try {
                        amount = Integer.parseInt(newAmount);
                        if (amount <= 0) {
                            throw new Exception("Ilość musi być liczbą większą od zera.");
                        }
                    } catch (NumberFormatException nfe) {
                        throw new Exception("Ilość przedmiotów musi być liczbą całkowitą dodatnią.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String currentStorageTime = table1.getValueAt(selectedRow, 4).toString();
                String newPrice = CalculatePrice.calculatePrice(Integer.parseInt(newAmount), Integer.parseInt(currentStorageTime));
                String originalDate = table1.getValueAt(selectedRow, 6).toString();

                table1.setValueAt(newCategory, selectedRow, 1);
                table1.setValueAt(newName, selectedRow, 2);
                table1.setValueAt(newAmount, selectedRow, 3);
                table1.setValueAt(currentStorageTime, selectedRow, 4);
                table1.setValueAt(newPrice, selectedRow, 5);
                table1.setValueAt(originalDate, selectedRow, 6);

                updateFile(filePath, selectedRow);
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

        String[] columnNames = {"Użytkownik", "Kategoria", "Nazwa", "Ilość", "Dni przechowania", "Koszt (zł)", "Data dodania"};
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setColumnIdentifiers(columnNames);

        model.setRowCount(0);

        for (String[] row : data) {
            model.addRow(row);
        }
    }

    private void updateFile(String filePath, int selectedRow) {
        try {
            List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get(filePath)));
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            StringBuilder updatedLine = new StringBuilder();
            for (int j = 0; j < model.getColumnCount(); j++) {
                updatedLine.append(model.getValueAt(selectedRow, j).toString());
                if (j < model.getColumnCount() - 1) {
                    updatedLine.append(",");
                }
            }

            String user = Login.login1;
            for (int i = 0; i < fileContent.size(); i++) {
                String[] parts = fileContent.get(i).split(",");
                if (parts.length > 0 && parts[0].equals(user)) {
                    if (originalData.get(selectedRow)[1].equals(parts[1]) &&
                            originalData.get(selectedRow)[2].equals(parts[2]) &&
                            originalData.get(selectedRow)[3].equals(parts[3]) &&
                            originalData.get(selectedRow)[4].equals(parts[4]) &&
                            originalData.get(selectedRow)[5].equals(parts[5]) &&
                            originalData.get(selectedRow)[6].equals(parts[6])) {
                        fileContent.set(i, updatedLine.toString());
                        break;
                    }
                }
            }

            Files.write(Paths.get(filePath), fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UpdateProduct update = new UpdateProduct();
    }
}
