import javax.swing.*;

public class Magazyn extends JFrame{
    private int width = 1000, height = 1000;
    private JPanel panel1;
    private JButton addButton;
    private JButton readButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton settingsButton;
    private JButton exitButton;

    public Magazyn(){
        super("Magazyn");
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

    public static void main(String[] args) {
        Magazyn magazyn = new Magazyn();
    }
}
