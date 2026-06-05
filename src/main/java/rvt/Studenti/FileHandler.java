package rvt.Studenti;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private final Path filePath;

    public FileHandler(String path) {
        this.filePath = Path.of(path);
        ensureFile();
    }

    private void ensureFile() {
        try {
            Path parent = filePath.getParent();
            if (parent != null && !Files.exists(parent)) Files.createDirectories(parent);
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                try (BufferedWriter w = new BufferedWriter(new FileWriter(filePath.toFile(), true))) {
                    w.write("FirstName,LastName,Email,PersonalCode,RegisteredAt");
                    w.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void append(Student s) throws IOException {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(filePath.toFile(), true))) {
            w.write(s.toCsv());
            w.newLine();
        }
    }

    public synchronized List<Student> readAll() throws IOException {
        List<Student> out = new ArrayList<>();
        List<String> lines = Files.readAllLines(filePath);
        if (lines.size() <= 1) return out;
        for (int i = 1; i < lines.size(); i++) {
            String l = lines.get(i).trim();
            if (l.isEmpty()) continue;
            Student s = Student.fromCsv(l);
            if (s != null) out.add(s);
        }
        return out;
    }

    public synchronized void writeAll(List<Student> students) throws IOException {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(filePath.toFile(), false))) {
            w.write("FirstName,LastName,Email,PersonalCode,RegisteredAt");
            w.newLine();
            for (Student s : students) {
                w.write(s.toCsv());
                w.newLine();
            }
        }
    }
}
