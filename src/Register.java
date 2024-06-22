import javax.print.attribute.standard.DialogOwner;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;

public class Register extends JFrame{
    private JTextField loginField;
    private JPasswordField passwordField;
    private JLabel logowanie;
    private JButton registerButton;
    private JButton exitButton;
    private JTextField emailField;
    private JButton wróćDoLogowaniaButton;
    private JPanel panel1;
    private int width = 400, height = 400;

    public Register(){
        super("logowanie");
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        wróćDoLogowaniaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login();
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String login = loginField.getText();

                String password = String.valueOf(passwordField.getPassword());
                try {
                    if (emailExists(email)) {
                        JOptionPane.showMessageDialog(null, "Użytkownik o podanym Emailu już istnieje.", "Błąd rejestracji", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (loginExists(login)) {
                        JOptionPane.showMessageDialog(null, "Użytkownik o podanym loginie już istnieje.", "Błąd rejestracji", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                String filePath1 = "BazaDanych.txt";
                String filePath2 = "Logowanie.txt";
                try (BufferedWriter writer1 = new BufferedWriter(new FileWriter(filePath1, true))) {
                    writer1.write(emailField.getText());
                    writer1.newLine();
                } catch (IOException a) {
                    a.printStackTrace();
                }

                try (BufferedWriter writer2 = new BufferedWriter(new FileWriter(filePath2, true))) {
                    writer2.write(loginField.getText() + "  " + String.valueOf(passwordField.getPassword()));
                    writer2.newLine();
                } catch (IOException a) {
                    a.printStackTrace();
                }


                JOptionPane.showMessageDialog(null, "Rejestracja przebiegła pomślnie\n " +
                                "Można się zalogować ",
                        "Rejestracja", JOptionPane.INFORMATION_MESSAGE);

                dispose();
                new Login();
            }
            });

        }

    private boolean emailExists(String email) throws IOException {
        String filePath = "BazaDanych.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals(email.trim())) {
                    return true;
                }
            }
        }
        return false;
    }


    private boolean loginExists(String login) throws IOException {
        String filePath = "Logowanie.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("  ");
                if (parts[0].trim().equals(login.trim())) {
                    return true;
                }
            }
        }
        return false;
    }
    public static void main(String[] args) {
        Register register = new Register();
    }

}


