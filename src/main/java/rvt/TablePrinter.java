package rvt;

import java.time.format.DateTimeFormatter;
import java.util.List;

import rvt.Studenti.StudentRecord;

public class TablePrinter {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void printHeader() {
        String[] headers = {"First Name", "Last Name", "Email", "Personal Code", "Registered At"};
        int[] widths = {15, 15, 30, 15, 20};
        printSeparator(widths);
        printRow(headers, widths);
        printSeparator(widths);
    }

    public static void printRecord(StudentRecord r) {
        String[] row = {r.getFirstName(), r.getLastName(), r.getEmail(), r.getPersonalCode(), r.getRegisteredAt().format(FORMATTER)};
        int[] widths = {15, 15, 30, 15, 20};
        printRow(row, widths);
        printSeparator(widths);
    }

    private static void printSeparator(int[] widths) {
        StringBuilder sb = new StringBuilder();
        for (int w : widths) {
            sb.append('+');
            for (int i = 0; i < w + 2; i++) sb.append('-');
        }
        sb.append('+');
        System.out.println(sb.toString());
    }

    private static void printRow(String[] cols, int[] widths) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cols.length; i++) {
            sb.append("| ");
            String s = cols[i] == null ? "" : cols[i];
            if (s.length() > widths[i]) s = s.substring(0, widths[i]-3) + "...";
            sb.append(String.format("%-" + widths[i] + "s ", s));
        }
        sb.append('|');
        System.out.println(sb.toString());
    }
}
