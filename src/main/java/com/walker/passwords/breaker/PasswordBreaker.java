package com.walker.passwords.breaker;

import java.util.List;

public interface PasswordBreaker {
    List<String> getCombinations(int passwordLength);
}
