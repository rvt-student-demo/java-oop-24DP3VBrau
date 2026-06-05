package rvt;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDoList {
    private ArrayList<String> tasks;
    private final String filePath = "data/todo.csv";

    public ToDoList() {
        this.tasks = new ArrayList<>();
        loadFromFile();
    }

    private void loadFromFile(){
    try (Scanner reader = new Scanner(new File(filePath))){
        reader.nextLine(); //Ignore header row
        while(reader.hasNextLine()){
            String line = reader.nextLine();
            String[] parts = line.split(",", 2); // Split on first comma only
            if(parts.length > 1) {
                tasks.add(parts[1].trim()); // Add just the task part
            }
        }
    }
    catch (FileNotFoundException e){
        System.out.println("Error" + e.getMessage());
    }
    }

    //pievienojot jauno aktivitāti
    // Reģidēt esošo add() metodi
    public void add(String task) {
        tasks.add(task);
        //Papildus add metode, pievieno ierakstu
        //Jusu .csv failā.
        //Izmantojam šeit arī iepriekš izveidoto metodi
        //getLastId(), lai ieraksts failā saturātu tādus datus:
        //<id>, <task>
        int newId = getLastId() + 1; // Ja nav uzdevumu, sāksies ar 1
        String newLine = System.lineSeparator() + newId + "," + task;
        try {
            Files.write(Paths.get(filePath), newLine.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

    }

    //pievienot updateFile() metodi
    // Kura atjauno/pārraksta .csv failu ar jauniem datiem
    // Izmantojot esošo task ArrayList masīvu
    private boolean updateFile(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("id,task" + System.lineSeparator()); // Write header
            for (int i = 0; i < tasks.size(); i++) {
                String line = (i + 1) + "," + tasks.get(i) + System.lineSeparator();
                writer.write(line);
            }
            return true; // Return true if update is successful
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
        return false;

    }

    // Overloaded method to update a specific task and persist the changes
    public boolean updateFile(int number, String newTask) {
        if (number < 1 || number > tasks.size()) {
            return false;
        }
        tasks.set(number - 1, newTask);
        return updateFile();
    }

    // Izmantojot RegEx - pārbaudīt lai aktivitāte saturētu tikai burtus, ciparus un atstarpes
    // Aktivitātes garums - min. 3 simboli, (.lenght() metode)
    public boolean checkEventString(String value){
        if(value.length() < 3){
            return false;
        }
        return value.matches("[a-zA-Z0-9 ]+");
    }

    public void print() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ": " + tasks.get(i));
        }
        System.out.println();
    }

    private int getLastId(){
        //pievienot mtodi todolist klasei, kas atgriez pēdējo id, lai varētu to izmantot pievienojot jaunu uzdevumu
        //ja nav uzdevumu, atgriezt 0
        if(tasks.isEmpty()){
            return 0;
    }
        return tasks.size();
    }

    public void remove(int number) {
        // number is 1-based, ArrayList is 0-based
        tasks.remove(number - 1);
        //izdzēst elementu no ArrayList pēc <id>
        // Kollonas vērtības
        //Atjaunot .csv failu
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("id,task" + System.lineSeparator()); // Write header
            for (int i = 0; i < tasks.size(); i++) {
                String line = (i + 1) + "," + tasks.get(i) + System.lineSeparator();
                writer.write(line);
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
