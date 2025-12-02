package com.istad.library.util;

import java.util.regex.Pattern;

public class InputValidator {

    // Check not empty
    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    // Check valid year (e.g., 1000 - current year)
    public static boolean isValidYear(String input) {
        try {
            int year = Integer.parseInt(input);
            int currentYear = java.time.Year.now().getValue();
            return year >= 1000 && year <= currentYear;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Check valid ISBN (basic: 10 or 13 digits)
    public static boolean isValidISBN(String input) {
        return input != null && Pattern.matches("\\d{10}|\\d{13}", input);
    }

    // Check valid email
    public static boolean isValidEmail(String input) {
        return input != null && Pattern.matches("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,6}$", input);
    }

    // Check valid 10-digit phone
    public static boolean isValidPhone(String input) {
        return input != null && Pattern.matches("\\d{10}", input);
    }

    // Check not duplicate username
    public static boolean isUniqueUsername(String username, com.istad.library.service.MemberService memberService) {
        return memberService.findByUsername(username) == null;
    }
}
