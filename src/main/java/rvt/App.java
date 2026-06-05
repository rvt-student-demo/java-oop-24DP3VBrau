package rvt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        File file = new File("data/ToDo.csv");
        try {
            Scanner scanner = new Scanner(file);
            int printed = 0;
            while (scanner.hasNextLine() && printed < 3) {
                System.out.println(scanner.nextLine());
                printed++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
            
    }
    
}
