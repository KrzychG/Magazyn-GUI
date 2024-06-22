import javax.swing.*;

public class DeleteProduct extends JFrame{
    private JPanel panel1;
    private JButton settingsButton;
    private JButton exitButton;
    private int width = 800, height = 800;
    public DeleteProduct(){
        super("Usuwanie produktu");
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    public static void main(String[] args) {
        DeleteProduct usun = new DeleteProduct();
    }
}
