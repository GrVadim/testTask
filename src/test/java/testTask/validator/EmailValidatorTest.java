package testTask.validator;

import org.junit.Assert;
import org.junit.Test;

public class EmailValidatorTest {

    private final String[] validEmailIds = new String[] { "journaldev@gmail.com", "journaldev-100@gmail.com",
            "journaldev.100@gmail.com", "journaldev111@journaldev.com", "journaldev-100@journaldev.net"};

    private final String[] invalidEmailIds = new String[] { "journaldev",
            "journaldev123@gmail.a", "journaldev123@com",
            "journaldev()*@gmail.com", "journaldev@%*.com",
            "journaldev@journaldev@gmail.com", "journaldev@gmail.com.1a" };

    private EmailValidator emailValidator;

    @Test
    public void testEmails() {
        emailValidator = new EmailValidator();
        for (String temp : validEmailIds) {
            Assert.assertTrue(emailValidator.validateEmail(temp));
        }
        System.out.println("\n\n");
        for (String temp : invalidEmailIds) {
            Assert.assertFalse(emailValidator.validateEmail(temp));
        }
    }
}
