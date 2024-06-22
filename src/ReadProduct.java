import javax.swing.*;

public class ReadProduct extends JFrame{
    private JButton settingsButton;
    private JButton exitButton;
    private JPanel panel1;
    private int width = 800, height = 800;
    public ReadProduct(){
        super("Wyświetlanie produktów");
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    public static void main(String[] args) {
        ReadProduct czytaj = new ReadProduct();
    }
}
