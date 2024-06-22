import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame{
    private JPanel panel1;
    private JButton loginButton;
    private JButton exitButton;
    private JLabel logowanie;
    private JTextField textField1;
    private JPasswordField passwordField1;
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
                if (login1.equals("login") && haslo1.equals("haslo")) {
                    dispose();
                    new Magazyn();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Nie udało się zalogować",
                            "Logowanie", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
    }
    public static void main(String[] args) {
        Login login = new Login();
    }
}
