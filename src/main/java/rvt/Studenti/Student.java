package rvt.Studenti;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Student {
	private String firstName;
	private String lastName;
	private String email;
	private String personalCode;
	private LocalDateTime registeredAt;

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public Student(String firstName, String lastName, String email, String personalCode, LocalDateTime registeredAt) {
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

	public void setFirstName(String firstName) { this.firstName = firstName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	public void setEmail(String email) { this.email = email; }

	public String toCsv() {
		return escape(firstName) + "," + escape(lastName) + "," + escape(email) + "," + escape(personalCode) + "," + registeredAt.format(FORMATTER);
	}

	private static String escape(String s) {
		if (s == null) return "";
		if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
			return '"' + s.replace("\"", "\"\"") + '"';
		}
		return s;
	}

	public static Student fromCsv(String line) {
		// simple CSV parser that handles quoted fields
		String[] parts = parseCsvLine(line);
		if (parts.length < 5) return null;
		String f = parts[0];
		String l = parts[1];
		String e = parts[2];
		String pk = parts[3];
		LocalDateTime dt = LocalDateTime.parse(parts[4], FORMATTER);
		return new Student(f, l, e, pk, dt);
	}

	private static String[] parseCsvLine(String line) {
		java.util.List<String> cols = new java.util.ArrayList<>();
		StringBuilder cur = new StringBuilder();
		boolean inQuotes = false;
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (c == '"') {
				if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
					cur.append('"');
					i++; // skip escaped quote
				} else {
					inQuotes = !inQuotes;
				}
			} else if (c == ',' && !inQuotes) {
				cols.add(cur.toString());
				cur.setLength(0);
			} else {
				cur.append(c);
			}
		}
		cols.add(cur.toString());
		return cols.toArray(new String[0]);
	}
}
