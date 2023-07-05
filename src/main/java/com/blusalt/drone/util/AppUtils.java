package com.blusalt.drone.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {

    static Logger log = LoggerFactory.getLogger(AppUtils.class);

    public static boolean isAllowedLettersNumbersHypenAndUnderscore(String input) {
        String regex = "^[a-zA-Z0-9-_]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean isAllowedUpperLettersUnderscoreAndNumbers(String input) {
        // Define the regular expression pattern to match uppercase letters, underscores, and numbers
        String regex = "^[A-Z0-9_]+$";

        // Create a Pattern object with the regex pattern
        Pattern pattern = Pattern.compile(regex);

        // Create a Matcher object to match the input against the pattern
        Matcher matcher = pattern.matcher(input);

        // Check if the input string matches the pattern
        return matcher.matches();
    }

    public static void main(String[] args) {

        System.out.println("result: " + isAllowedUpperLettersUnderscoreAndNumbers("ABC_234"));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("encoder 1 password:  " + encoder.encode("blusalt"));
        System.out.println("encoder 2 user:  " + encoder.encode("test-user"));

        if (encoder.matches("blusalt", "$2a$10$6Fdm5P/BQG4VPyIAhxJUyOD8OQZQQrtFnhIdCNQ4uY4sYJgXBr9cW")) {
            System.out.println("encoder: true");
        }
    }


}
