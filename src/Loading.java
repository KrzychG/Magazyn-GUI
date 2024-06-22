import javax.swing.*;
import java.util.Random;

public class Loading extends JFrame {
    private int width = 400, height = 400;
    private javax.swing.JPanel JPanel;
    private JProgressBar progressBar1;
    private JLabel naglowek;
    private JLabel wczywywanie;
    private JLabel icon;

    public Loading() {
        super("Wczytywanie");
        this.setContentPane(this.JPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        loading();
    }
    private void loading(){
        Random random = new Random();
            int counter = 0;
            while (counter <= 100){
                wczywywanie.setText("Wczytywanie...");
                progressBar1.setValue(counter);
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                counter+=random.nextInt((10-5)+1)+5;
            }
            dispose();
            new Login();
        }
        public static void main(String[] args) {
        Loading okno = new Loading();
        }
}
