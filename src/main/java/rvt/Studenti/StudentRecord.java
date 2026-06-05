package rvt.Studenti;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StudentRecord {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String personalCode;
    private final LocalDateTime registeredAt;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public StudentRecord(String firstName, String lastName, String email, String personalCode, LocalDateTime registeredAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.personalCode = personalCode;
        this.registeredAt = registeredAt;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPersonalCode() { return personalCode; }
    public LocalDateTime getRegisteredAt() { return registeredAt; }

    public String toCsv() {
        return String.format("%s,%s,%s,%s,%s", escapeCsv(firstName), escapeCsv(lastName), escapeCsv(email), escapeCsv(personalCode), registeredAt.format(FORMATTER));
    }

    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
