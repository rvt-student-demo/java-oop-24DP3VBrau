package rvt.Studenti;

import java.util.regex.Pattern;

public class Validator {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\p{L} '-]{3,}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PERSONAL_CODE_PATTERN = Pattern.compile("^\\d{6}-?\\d{5}$");

    public static boolean validName(String name) {
        return name != null && NAME_PATTERN.matcher(name.trim()).matches();
    }

    public static boolean validEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean validPersonalCode(String code) {
        return code != null && PERSONAL_CODE_PATTERN.matcher(code.trim()).matches();
    }
}
