import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class AddProduct extends JFrame {

    private JPanel panel1;
    private JComboBox comboBox1;
    private JTextField nazwaField;
    private JButton dodawanieButton;
    private JButton exitButton;
    private JTextField iloscField1;
    private JTextField dniField1;
    private int width = 1000, height = 800;


    public AddProduct(){
        super("Dodawanie przedmiotów");
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(width,height);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        dodawanieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath1 = "BazaDanych.txt";
                String login = Login.login1;
                String timeText = dniField1.getText();
                String nazwa = nazwaField.getText();
                String iloscText = iloscField1.getText();
                int ilosc;
                int time;

                try {
                    if (nazwa.isEmpty() || iloscText.isEmpty() || timeText.isEmpty()) {
                        throw new Exception("Proszę wypełnić wszystkie wymagane pola.");
                    }

                    try {
                        ilosc = Integer.parseInt(iloscText);
                        if (ilosc <= 0) {
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

                    if (nazwa.isEmpty() || !nazwa.matches("[\\p{L} ]+")) {
                        throw new Exception("Niepoprawnie określona nazwa przedmiotu.");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String price = calculatePrice(ilosc, time);

                try (BufferedReader reader = new BufferedReader(new FileReader(filePath1))) {
                    StringBuilder fileContent = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        fileContent.append(line).append("\n");
                    }


                    fileContent.append(login).append(",").append(comboBox1.getSelectedItem().toString())
                            .append(",").append(nazwa).append(",").append(iloscText).append(",")
                            .append(timeText).append(",").append(price).append("\n");

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath1))) {
                        writer.write(fileContent.toString());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(null,
                            "Przedmiot został dodany.\n Cena dodania przedmiotu: " + price+" zł",
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

    private String calculatePrice(int ilosc, int time) {
        int calculatedPrice = (10 * time) + (ilosc * 5);

        return Integer.toString(calculatedPrice);
    }


    public static void main(String[] args) {
        AddProduct dodaj = new AddProduct();
    }
}
