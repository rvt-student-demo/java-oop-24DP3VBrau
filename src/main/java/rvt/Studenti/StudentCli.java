package rvt.Studenti;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import rvt.TablePrinter;

public class StudentCli {
    public static void main(String[] args) {
        FileHandler fh = new FileHandler("data/students.csv");
        Registration reg = new Registration(fh);
        Scanner in = new Scanner(System.in);

        System.out.println("Student Registration (package: rvt.Studenti)");
        while (true) {
            System.out.println();
            System.out.println("Commands: register | show | remove | edit | exit");
            System.out.print("> ");
            String cmd = in.nextLine().trim().toLowerCase();
            try {
                switch (cmd) {
                    case "register":
                        System.out.print("First name: ");
                        String first = in.nextLine();
                        System.out.print("Last name: ");
                        String last = in.nextLine();
                        System.out.print("Email: ");
                        String email = in.nextLine();
                        System.out.print("Personal code: ");
                        String pk = in.nextLine();
                        reg.register(first, last, email, pk);
                        System.out.println("Registered.");
                        break;
                    case "show":
                        List<Student> all = reg.showAll();
                        TablePrinter.printHeader();
                        for (Student s : all) TablePrinter.printRecord(toRecord(s));
                        break;
                    case "remove":
                        System.out.print("Personal code to remove: ");
                        String rpk = in.nextLine();
                        boolean removed = reg.removeByPersonalCode(rpk);
                        System.out.println(removed ? "Removed." : "Not found.");
                        break;
                    case "edit":
                        System.out.print("Personal code to edit: ");
                        String epk = in.nextLine();
                        System.out.print("New first name (leave blank to keep): ");
                        String nf = in.nextLine();
                        System.out.print("New last name (leave blank to keep): ");
                        String nl = in.nextLine();
                        System.out.print("New email (leave blank to keep): ");
                        String ne = in.nextLine();
                        boolean edited = reg.editByPersonalCode(epk, nf, nl, ne);
                        System.out.println(edited ? "Edited." : "Not found.");
                        break;
                    case "exit":
                        System.out.println("Bye");
                        in.close();
                        return;
                    default:
                        System.out.println("Unknown command");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Validation error: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("IO error: " + e.getMessage());
            }
        }
    }

    private static StudentRecord toRecord(Student s) {
        return new StudentRecord(s.getFirstName(), s.getLastName(), s.getEmail(), s.getPersonalCode(), s.getRegisteredAt());
    }
}
