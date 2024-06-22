import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Magazyn extends JFrame{
    private int width = 1000, height = 1000;
    private String login = Login.login1;
    private JPanel panel1;
    private JButton addButton;
    private JButton readButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton settingsButton;
    private JButton exitButton;
    private JLabel nazwaUzytkownika;
    private JPanel panel2;

    public Magazyn(){
        super("Magazyn");
        this.nazwaUzytkownika.setText(login);
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setVisible(true);
        this.setLocationRelativeTo(null);


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login();
            }

        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddProduct();
            }
        });
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReadProduct();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateProduct();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteProduct();
            }
        });
    }

    public static void main(String[] args) {
        Magazyn magazyn = new Magazyn();
    }

}
