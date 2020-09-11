package testTask.validator;

import org.junit.Assert;
import org.junit.Test;

public class PhoneValidatorTest {
    private final String[] validEmailIds = new String[] { "+380674565445", "+380673444445",
            "+380572245336"};

    private final String[] invalidEmailIds = new String[] { "+38067 4565445", "+38-0674565445", "+38067-4565445",
            "+380674" };

    private PhoneValidator phoneValidator;

    @Test
    public void testPhones() {
        phoneValidator = new PhoneValidator();
        for (String temp : validEmailIds) {
            Assert.assertTrue(phoneValidator.validatePhone(temp));
        }
        System.out.println("\n\n");
        for (String temp : invalidEmailIds) {
            Assert.assertFalse(phoneValidator.validatePhone(temp));
        }
    }
}
