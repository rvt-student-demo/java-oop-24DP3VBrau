package rvt.Studenti;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class Registration {
    private final FileHandler handler;

    public Registration(FileHandler handler) {
        this.handler = handler;
    }

    public void register(String first, String last, String email, String pk) throws IllegalArgumentException, IOException {
        if (!Validator.validName(first)) throw new IllegalArgumentException("Invalid first name");
        if (!Validator.validName(last)) throw new IllegalArgumentException("Invalid last name");
        if (!Validator.validEmail(email)) throw new IllegalArgumentException("Invalid email");
        if (!Validator.validPersonalCode(pk)) throw new IllegalArgumentException("Invalid personal code");

        Student s = new Student(first.trim(), last.trim(), email.trim(), pk.trim(), LocalDateTime.now());
        handler.append(s);
    }

    public List<Student> showAll() throws IOException {
        return handler.readAll();
    }

    public boolean removeByPersonalCode(String pk) throws IOException {
        List<Student> all = handler.readAll();
        boolean removed = all.removeIf(s -> s.getPersonalCode().equals(pk.trim()));
        if (removed) handler.writeAll(all);
        return removed;
    }

    public boolean editByPersonalCode(String pk, String newFirst, String newLast, String newEmail) throws IOException {
        List<Student> all = handler.readAll();
        boolean found = false;
        for (Student s : all) {
            if (s.getPersonalCode().equals(pk.trim())) {
                if (newFirst != null && !newFirst.isBlank()) s.setFirstName(newFirst.trim());
                if (newLast != null && !newLast.isBlank()) s.setLastName(newLast.trim());
                if (newEmail != null && !newEmail.isBlank()) s.setEmail(newEmail.trim());
                found = true;
                break;
            }
        }
        if (found) handler.writeAll(all);
        return found;
    }
}
