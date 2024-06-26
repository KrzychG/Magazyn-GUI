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
    private JTable table1;
    private JButton exitButton;
    private JTextField nazwaField1;
    private JTextField ileField2;
    private JButton usuńButton;
    private int width = 700, height = 800;
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


        if (data.isEmpty() || (data.size() == 1 && data.get(0).length == 0)) {
            JOptionPane.showMessageDialog(this, "Nie posiadasz aktualnie żadnych przedmiotów", "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }

        usuńButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> fileContent = new ArrayList<>();
                String filePath1 = "BazaDanych.txt";
                String name = nazwaField1.getText();
                boolean flag = true;

                int num;
                try {
                    num = Integer.parseInt(ileField2.getText());
                    if (num <= 0) {
                        JOptionPane.showMessageDialog(null, "Liczba przedmiotów do usunięcia nie może być ujemna.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Proszę wprowadzić prawidłową liczbę.");
                    return;
                }

                boolean itemFound = false;
                boolean enoughItem = true;

                try (BufferedReader reader = new BufferedReader(new FileReader(filePath1))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts[0].trim().equals(Login.login1.trim()) && parts[2].trim().equals(name.trim())) {
                            itemFound = true;
                            int currentQuantity = Integer.parseInt(parts[3].trim());
                            if (flag) {
                                if (currentQuantity >= num) {
                                    int newQuantity = currentQuantity - num;
                                    if (newQuantity == 0) {
                                        continue;
                                    }
                                    parts[3] = String.valueOf(newQuantity);
                                    flag = false;
                                } else {
                                    enoughItem = false;
                                    break;
                                }
                            }
                        }
                        fileContent.add(String.join(",", parts));
                    }

                    if (!itemFound) {
                        JOptionPane.showMessageDialog(null, "Nie znaleziono przedmiotu o podanej nazwie.");
                    } else if (!enoughItem) {
                        JOptionPane.showMessageDialog(null, "Nie masz wystarczającej ilości przedmiotów do usunięcia.");
                    } else {
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
