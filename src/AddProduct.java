import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class AddProduct extends JFrame {

    private JPanel panel1;
    private JComboBox comboBox1;
    private JTextField nazwaButton;
    private JButton dodawanieButton;
    private JButton exitButton;
    private JLabel cennikNapis;
    private JLabel naglowekDodawanie;
    private JCheckBox do7DniCheckBox;
    private JCheckBox od7Do30CheckBox;
    private JCheckBox powyżej30DniCheckBox;
    private JTextField iloscField1;
    private int width = 1000, height = 800;


    public AddProduct(){
        super("Dodawanie produktu");
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
                String czas = "";

                if(do7DniCheckBox.isSelected()){
                    czas = do7DniCheckBox.getText();
                }
                else if (od7Do30CheckBox.isSelected())
                {
                    czas = od7Do30CheckBox.getText();
                }
                else if (powyżej30DniCheckBox.isSelected())
                {
                    czas = powyżej30DniCheckBox.getText();
                }
                else
                    JOptionPane.showMessageDialog(null, "Proszę wybrać czas przechowania przedmiotu",
                            "Błąd", JOptionPane.ERROR_MESSAGE);

                try (BufferedReader reader = new BufferedReader(new FileReader(filePath1))) {
                    StringBuilder fileContent = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        fileContent.append(line).append("\n");
                    }


                    fileContent.append(login).append(",  ").append(comboBox1.getSelectedItem().toString())
                            .append(",   ").append(nazwaButton.getText()).append(",  ").append(iloscField1.getText()).append(",  ")
                            .append(czas).append("\n");

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
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
    }
    public static void main(String[] args) {
        AddProduct dodaj = new AddProduct();
    }
}
