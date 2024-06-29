import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Statistics extends JFrame {

    private JPanel panel1;
    private JTable table1;
    private JButton exitButton;
    private JTable table2;
    private JTable table3;
    private JTable table4;
    private int width = 1000, height = 800;

    public Statistics() {
        super("Statystyki");
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(width, height);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        panel1.add(new JScrollPane(table1));
        table1.setModel(new DefaultTableModel());
        table1.setBackground(Color.DARK_GRAY);

        panel1.add(new JScrollPane(table2));
        table2.setModel(new DefaultTableModel());
        table2.setBackground(Color.DARK_GRAY);

        panel1.add(new JScrollPane(table3));
        table3.setModel(new DefaultTableModel());
        table3.setBackground(Color.DARK_GRAY);

        panel1.add(new JScrollPane(table4));
        table4.setModel(new DefaultTableModel());
        table4.setBackground(Color.DARK_GRAY);

        String filePath = "BazaDanych.txt";
        List<String[]> data = StatisticsTables.readData(filePath);
        StatisticsTables.updateTableModel(table1, data);

        String penaltyFilePath = "Kary.txt";
        List<String[]> penaltyData = StatisticsTables.readPenaltyData(penaltyFilePath);
        StatisticsTables.updateTableModel(table2, penaltyData);

        List<String[]> costData = StatisticsTables.readCosts(filePath);
        StatisticsTables.updateTableModel(table3, costData);

        List<String[]> totalAmountCosts = StatisticsTables.readAmounCosts(filePath);
        StatisticsTables.updateTableModel(table4, totalAmountCosts);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        Statistics statistics = new Statistics();
    }
}
