package passwords;

import com.walker.passwords.generator.PasswordGenerator;
import com.walker.passwords.generator.PasswordGeneratorImpl;
import com.walker.passwords.model.AsciiTableRange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PasswordGeneratorImplTest {
    private PasswordGenerator passwordGenerator;

    @BeforeEach
    public void setup() {
        // Define the ASCII table ranges for uppercase letters, lowercase letters, and numbers
        List<AsciiTableRange> ranges = new ArrayList<>();
        ranges.add(new AsciiTableRange(65, 90));  // Uppercase letters (A-Z)
        ranges.add(new AsciiTableRange(97, 122)); // Lowercase letters (a-z)
        ranges.add(new AsciiTableRange(48, 57));  // Numbers (0-9)

        Random random = new Random(); // You can provide a seed value for consistent test results

        passwordGenerator = new PasswordGeneratorImpl(ranges, random);
    }

    @Test
    public void testGeneratePasswordLength() {
        // Specify the desired password length
        int length = 10;

        // Generate a password of the specified length
        String password = passwordGenerator.generate(length);

        // Ensure that the generated password has the expected length
        Assertions.assertEquals(length, password.length());
    }

    @Test
    public void testGeneratePasswordContainsUppercase() {
        // Specify the desired password length
        int length = 8;

        // Generate a password of the specified length
        String password = passwordGenerator.generate(length);

        // Ensure that the generated password contains at least one uppercase letter
        Assertions.assertTrue(password.matches(".*[A-Z].*"));
    }

    @Test
    public void testGeneratePasswordContainsLowercase() {
        // Specify the desired password length
        int length = 8;

        // Generate a password of the specified length
        String password = passwordGenerator.generate(length);

        // Ensure that the generated password contains at least one lowercase letter
        Assertions.assertTrue(password.matches(".*[a-z].*"));
    }

    @Test
    public void testGeneratePasswordContainsNumbers() {
        // Specify the desired password length
        int length = 8;

        // Generate a password of the specified length
        String password = passwordGenerator.generate(length);

        // Ensure that the generated password contains at least one number
        Assertions.assertTrue(password.matches(".*[0-9].*"));
    }

    @Test
    public void testGeneratePasswordWithZeroLength() {
        // Specify the desired password length as zero
        int length = 0;

        // Generate a password of zero length
        String password = passwordGenerator.generate(length);

        // Ensure that an empty string is returned for zero length
        Assertions.assertEquals("", password);
    }
}
