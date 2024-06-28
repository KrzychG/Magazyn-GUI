import javax.swing.*;

public class Statistics extends JFrame {

    private JPanel panel1;
    private JTable table1;
    private JTable table2;
    private JTable table3;
    private int width = 1000, height = 800;

    public Statistics() {
        super("Statystyki");
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(width,height);
        this.setVisible(true);
        this.setLocationRelativeTo(null);


        PenaltyCheck.checkPenalty();

    }


    public static void main(String[] args) {
        Statistics statictics = new Statistics();
    }

}
