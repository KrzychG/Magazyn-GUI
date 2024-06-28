import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddProduct extends JFrame {

    private JPanel panel1;
    private JComboBox comboBox1;
    private JTextField nameField;
    private JButton addButton;
    private JButton exitButton;
    private JTextField numberField1;
    private JTextField daysField1;
    private int width = 700, height = 500;


    public AddProduct() {
        super("Dodawanie przedmiotów");
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(width, height);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = Login.login1;
                String timeText = daysField1.getText();
                String name = nameField.getText();
                String amountText = numberField1.getText();
                String filePath = "BazaDanych.txt";
                int amount;
                int time;

                try {
                    if (name.isEmpty() || amountText.isEmpty() || timeText.isEmpty()) {
                        throw new Exception("Proszę wypełnić wszystkie wymagane pola.");
                    }

                    try {
                        amount = Integer.parseInt(amountText);
                        if (amount <= 0) {
                            throw new Exception("Ilość musi być liczbą dodatnią.");
                        }
                    } catch (NumberFormatException nfe) {
                        throw new Exception("Ilość przedmiotu musi musi być liczbą całkowitą dodatnią.");
                    }

                    try {
                        time = Integer.parseInt(timeText);
                        if (time <= 0) {
                            throw new Exception("Czas przechowania musi być liczbą dodatnią.");
                        }
                    } catch (NumberFormatException nfe) {
                        throw new Exception("Czas przechowania przedmiotu musi być liczbą całkowitą dodatnią.");
                    }

                    if (name.isEmpty() || !name.matches("[\\p{L} ]+")) {
                        throw new Exception("Niepoprawnie określona name przedmiotu.");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String price = CalculatePrice.calculatePrice(amount, time);


                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    StringBuilder fileContent = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        fileContent.append(line).append("\n");
                    }

                    LocalDate entryDate = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    String formattedDate = entryDate.format(formatter);

                    fileContent.append(login).append(",").append(comboBox1.getSelectedItem().toString())
                            .append(",").append(name).append(",").append(amountText).append(",")
                            .append(timeText).append(",").append(price).append(",").append(formattedDate).append("\n");

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                        writer.write(fileContent.toString());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(null,
                            "Przedmiot został dodany.\n Cena dodania przedmiotu: " + price + " zł",
                            "Informacja", JOptionPane.INFORMATION_MESSAGE);

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



    public static void main(String[] args) {
        AddProduct add = new AddProduct();
    }
}
