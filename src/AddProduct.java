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
    private JCheckBox do7DniCheckBox;
    private JCheckBox od7Do30CheckBox;
    private JCheckBox powyżej30DniCheckBox;
    private JTextField iloscField1;
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
                String time = "";
                String nazwa = nazwaField.getText();
                String iloscText = iloscField1.getText();
                int ilosc;

                try {
                    if (nazwa.isEmpty() || iloscText.isEmpty() ||
                            (!do7DniCheckBox.isSelected() && !od7Do30CheckBox.isSelected() && !powyżej30DniCheckBox.isSelected())) {
                        throw new Exception("Proszę wypełnić wszystkie wymagane pola.");
                    }

                    if (do7DniCheckBox.isSelected()) {
                        time = do7DniCheckBox.getText();
                    } else if (od7Do30CheckBox.isSelected()) {
                        time = od7Do30CheckBox.getText();
                    } else if (powyżej30DniCheckBox.isSelected()) {
                        time = powyżej30DniCheckBox.getText();
                    }

                    ilosc = Integer.parseInt(iloscText);
                    if (ilosc < 0) {
                        throw new NumberFormatException("Ilość musi być liczbą nieujemną");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (BufferedReader reader = new BufferedReader(new FileReader(filePath1))) {
                    StringBuilder fileContent = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        fileContent.append(line).append("\n");
                    }


                    fileContent.append(login).append(",").append(comboBox1.getSelectedItem().toString())
                            .append(",").append(nazwaField.getText()).append(",").append(iloscField1.getText()).append(",")
                            .append(time).append("\n");

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath1))) {
                        writer.write(fileContent.toString());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
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
        AddProduct dodaj = new AddProduct();
    }
}
