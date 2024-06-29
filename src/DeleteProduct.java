import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteProduct extends JFrame {
    private JPanel panel1;
    private JTable table1;
    private JButton exitButton;
    private JTextField numberField2;
    private JButton delButton;
    private int width = 700, height = 800;

    public DeleteProduct() {
        super("Usuwanie przedmiotów");
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
            JOptionPane.showMessageDialog(this, "Nie posiadasz aktualnie żadnych przedmiotów",
                    "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }

        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && table1.getSelectedRow() != -1) {
                    numberField2.setText("");
                }
            }
        });

        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Proszę wybrać przedmiot z tabeli.",
                            "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String number = numberField2.getText();
                int num;
                try {
                    if (number.isEmpty()) {
                        throw new Exception("Proszę wypełnić pole ilości.");
                    }
                    num = Integer.parseInt(number);
                    if (num <= 0) {
                        throw new Exception("Ilość przedmiotów do usunięcia musi być dodatnia.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Nie można wprowadzić tekstu. Proszę wprowadzić liczbę.",
                            "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String filePath1 = "BazaDanych.txt";
                List<String> fileContent = new ArrayList<>();
                boolean enoughItem = true;

                try (BufferedReader reader = new BufferedReader(new FileReader(filePath1))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts[0].trim().equals(Login.login1.trim())) {
                            String tableName = table1.getValueAt(selectedRow, 2).toString().trim();
                            String tableCategory = table1.getValueAt(selectedRow, 1).toString().trim();
                            if (parts[2].trim().equals(tableName) && parts[1].trim().equals(tableCategory)) {
                                int currentAmount = Integer.parseInt(parts[3].trim());
                                if (currentAmount >= num) {
                                    int newAmount = currentAmount - num;
                                    if (newAmount == 0) {
                                        continue;
                                    }
                                    parts[3] = String.valueOf(newAmount);
                                    parts[5] = CalculatePrice.calculatePrice(newAmount, Integer.parseInt(parts[4].trim()));
                                } else {
                                    enoughItem = false;
                                    break;
                                }
                            }
                        }
                        fileContent.add(String.join(",", parts));
                    }

                    if (!enoughItem) {
                        JOptionPane.showMessageDialog(null,
                                "Nie masz wystarczającej ilości przedmiotów do usunięcia.",
                                "Błąd", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
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
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
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

    public List<String[]> readData(String filePath) {
        List<String[]> data = new ArrayList<>();
        String[] headers = {"Użytkownik", "Kategoria", "Nazwa", "Ilość", "Dni przechowania", "Koszt (zł)", "Data dodania"};
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

    public void updateTableModel(List<String[]> data) {
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

    public static void main(String[] args) {
        DeleteProduct delete = new DeleteProduct();
    }
}