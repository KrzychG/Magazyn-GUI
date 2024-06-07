import javax.swing.*;
import java.awt.*;

public class Login extends JFrame{
    private JPanel panel1;
    private JButton button1;
    private JButton button2;
    private JLabel logowanie;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel haslo;
    private JLabel login;
    private int width = 400, height = 400;

    public Login(){
        super("Wczytywanie");
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
