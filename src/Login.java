import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Login extends JFrame{
    private JPanel panel1;
    private JButton loginButton;
    private JButton exitButton;
    private JLabel logowanie;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton zarejestrujSięButton;
    private JTextField textField2;
    private int width = 400, height = 400;
    String login1 = "login", haslo1 = "password";

    public Login(){
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
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login1 = textField1.getText();
                haslo1 = passwordField1.getText();
                try {
                    if (correctLogin(login1, haslo1)) {
                        dispose();
                        new Magazyn();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Nie udało się zalogować",
                                "Logowanie", JOptionPane.ERROR_MESSAGE);

                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        zarejestrujSięButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Register();
            }
        });
    }
    private boolean correctLogin(String login, String haslo) throws IOException {
        String filePath = "Logowanie.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("  ");
                if (parts[0].trim().equals(login.trim()) && parts[1].trim().equals(haslo.trim())) {
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
        Login login = new Login();
    }
}