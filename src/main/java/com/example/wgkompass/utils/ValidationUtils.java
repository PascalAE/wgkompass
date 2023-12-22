package com.example.wgkompass.utils;

/**
 * The ValidationUtils class provides utility methods for validating strings.
 * It includes methods to check for illegal characters in a string. This class is
 * designed to be used as a utility with static methods and cannot be instantiated.
 */
public class ValidationUtils {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ValidationUtils() {
        // Private Constructor to prevent instantiation
    }

    /**
     * Checks if a given string contains only legal characters (letters and spaces).
     *
     * @param input The string to be checked.
     * @return true if the string contains only letters and spaces, false otherwise.
     *         Returns false if the string is null.
     */
    public static boolean containsIllegalCharacters(String input) {
        String regex = "^[a-zA-ZäöüÄÖÜ0-9\\s]+$";
        return input != null && !input.matches(regex);
    }
}