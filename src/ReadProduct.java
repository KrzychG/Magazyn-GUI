import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class ReadProduct extends JFrame{
    private JPanel panel1;
    private JTable table1;
    private JButton exitButton;
    private JButton button1;
    private int width = 500, height = 800;
    public ReadProduct() {
        super("Wyświetlanie przedmiotów");
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(width, height);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        panel1.add(new JScrollPane(table1));
        table1.setModel(new DefaultTableModel());
        table1.setBackground(Color.DARK_GRAY);

        String filePath = "BazaDanych.txt";
        List<String[]> data = ReadTable.readData(filePath);
        ReadTable.updateTableModel(table1, data);
    }






    public static void main(String[] args) {
        ReadProduct czytaj = new ReadProduct();
    }
    }



