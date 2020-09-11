package testTask.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator {
    private static final String PHONE_REGEX = "\\+\\d{12}$";

    private static Pattern pattern;

    private Matcher matcher;

    public PhoneValidator() {
        pattern = Pattern.compile(PHONE_REGEX, Pattern.CASE_INSENSITIVE);
    }

    public boolean validatePhone(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}