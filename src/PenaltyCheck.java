import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class PenaltyCheck {
    public static String filePath = "BazaDanych.txt";
    public static String penaltiesPath = "penalties.txt";
    public static int penalty = 200;

    public static void checkPenalty() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            Set<String> penalizedUsers = loadPenalizedUsers();

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String login = data[0];
                int storageDays = Integer.parseInt(data[4]);
                LocalDate entryDate = LocalDate.parse(data[6], formatter);

                if (currentDate.isAfter(entryDate.plusDays(storageDays)) && !penalizedUsers.contains(login)) {
                    addPenalty(login);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void addPenalty(String login) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(penaltiesPath, true))) {
            writer.write(login + "," + penalty + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public static Set<String> loadPenalizedUsers() {
        Set<String> penalizedUsers = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(penaltiesPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                penalizedUsers.add(data[0]);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return penalizedUsers;
    }

}
