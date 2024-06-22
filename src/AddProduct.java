import javax.swing.*;

public class AddProduct extends JFrame {

    private JButton settingsButton;
    private JButton exitButton;
    private JPanel panel1;
    private JComboBox comboBox1;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JCheckBox checkBox3;
    private JTextField textField1;
    private int width = 800, height = 800;

    public AddProduct(){
        super("Dodawanie produktu");
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(width,height);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    public static void main(String[] args) {
        AddProduct dodaj = new AddProduct();
    }
}
