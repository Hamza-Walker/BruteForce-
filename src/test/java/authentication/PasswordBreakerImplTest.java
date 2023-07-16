package authentication;

import com.walker.passwords.breaker.PasswordBreaker;
import com.walker.passwords.breaker.PasswordBreakerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PasswordBreakerImplTest {

    @Test
    public void testGetCombinations_OneCharPassword() {
        PasswordBreaker passwordBreaker = new PasswordBreakerImpl();
        int passwordLength = 1;

        List<String> combinations = passwordBreaker.getCombinations(passwordLength);

        Assertions.assertEquals(128, combinations.size());
    }

    @Test
    public void testGetCombinations_TwoCharPassword() {
        PasswordBreaker passwordBreaker = new PasswordBreakerImpl();
        int passwordLength = 2;

        List<String> combinations = passwordBreaker.getCombinations(passwordLength);

        Assertions.assertEquals(16_384, combinations.size());
    }

    @Test
    public void testGetCombinations_ThreeCharPassword() {
        PasswordBreaker passwordBreaker = new PasswordBreakerImpl();
        int passwordLength = 3;

        List<String> combinations = passwordBreaker.getCombinations(passwordLength);

        Assertions.assertEquals(2_097_152, combinations.size());
    }

    @Test
    public void testGetCombinations_FiveCharPassword() {
        PasswordBreaker passwordBreaker = new PasswordBreakerImpl();
        int passwordLength = 5;

        List<String> combinations = passwordBreaker.getCombinations(passwordLength);

        Assertions.assertEquals(33_554_432, combinations.size());
    }
}
