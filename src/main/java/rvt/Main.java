package rvt;

import java.util.ArrayList;

public class Main {

    public static void printPersons(ArrayList<Person> persons) {
        for (Person p : persons) {
            System.out.println(p);
        }
    }

    public static void main(String[] args) {
        ArrayList<Person> persons = new ArrayList<>();

        persons.add(new Person("Ada Lovelace", "24 Maddox St. London W1S 2QN"));
        persons.add(new Person("Esko Ukkonen", "Mannerheimintie 15 00100 Helsinki"));
        persons.add(new Teacher("Ada Lovelace", "24 Maddox St. London W1S 2QN", 1200));

        Student ollie = new Student("Ollie", "6381 Hollywood Blvd. Los Angeles 90028");
        for (int i = 0; i < 25; i++) {
            ollie.study();
        }
        persons.add(ollie);

        printPersons(persons);
    }
}
