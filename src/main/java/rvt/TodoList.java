package rvt;

import java.io.*;
import java.util.ArrayList;

public class TodoList {
    private ArrayList<String> tasks;
    private final String filePath = "data/todo.csv";

    public TodoList() {
        this.tasks = new ArrayList<>();
        loadFromFile();
    }

    // Izlasīt todo.csv failu
    // Un papildinât tasks ArrayList ar datiem
    // no faila
    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tasks.add(line);
            }
        } catch (IOException e) {
            System.out.println("Kļūda lasot failu: " + e.getMessage());
        }
    }

    // Pievienot jauno uzdevumu
    // Rediģēt esošo add() metodi
    public void add(String task) {
        this.tasks.add(task);
        // Papildus add metode, pievienojot iepriekš izveidoto metodi
        // Jāsauc arī .csv failā.
        // Izmantojam šeit arī iepriekš izveidoto metodi
        // getLastId(), lai ierakstītu failā sarūpētu datus:
        updateFile();
    }

    // Pievienot updateFile() metodi
    // Kura atjaunotu/pārrakstistu .csv failu ar jauniem datiem
    // Izmantojot esošo tasks ArrayList masīvu
    private boolean updateFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < tasks.size(); i++) {
                writer.write((i + 1) + "," + tasks.get(i));
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Kļūda atjauninot failu: " + e.getMessage());
            return false;
        }
    }

    // Pievienot jauniem aktivitātes "id" vērtību
    public int getTaskLength() {
        return tasks.size();
    }

    // Izmantojot RegEx - pārbaudīt lai aktivitāte saturētu
    // burtus, ciparus un atstarpes
    // aktivitātes garums - min. 3 simboli (.length() metode)
    public boolean checkEventString(String value) {
        if (value == null || value.length() < 3) {
            return false;
        }
        return value.matches("[a-zA-Z0-9\\s]+");
    }

    // Rediģēt remove() metodi
    public void remove(int id) {
        // Izdzēst elementu no ArrayList pēc <id>
        // kolonnas vērtības
        // Atjaunot .csv failu
        if (id > 0 && id <= tasks.size()) {
            tasks.remove(id - 1);
            updateFile();
        }
    }

    public ArrayList<String> getTasks() {
        return tasks;
    }

    public static void main(String[] args) {
        TodoList todoList = new TodoList();
        
        // Pārbaudīt funkcionalitāti
        System.out.println("Pašreizējie uzdevumi: " + todoList.getTasks().size());
        
        // Pievienot jaunu uzdevumu
        if (todoList.checkEventString("jauns uzdevums")) {
            todoList.add("jauns uzdevums");
            System.out.println("Uzdevums pievienots. Kopā uzdevumu: " + todoList.getTaskLength());
        }
    }
}
