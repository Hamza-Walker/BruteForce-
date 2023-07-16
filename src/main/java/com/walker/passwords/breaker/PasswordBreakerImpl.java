package com.walker.passwords.breaker;

import java.util.ArrayList;
import java.util.List;

public class PasswordBreakerImpl implements PasswordBreaker{
    @Override
    public List<String> getCombinations(int passwordLength) {
        List<String> combinations = new ArrayList<>();
        List<List<String>> asciiChars = generateAsciiCharLists(passwordLength);

        return getAllPossibleCombos(asciiChars);
    }

    private List<List<String>> generateAsciiCharLists(int passwordLength) {
        List<List<String>> asciiChars = new ArrayList<>();

        for (int i = 0; i < passwordLength; i++) {
            List<String> charList = new ArrayList<>();
            for (int j = 0; j < 128; j++) {
                charList.add(Character.toString((char) j));
            }
            asciiChars.add(charList);
        }

        return asciiChars;
    }

    private List<String> getAllPossibleCombos(List<List<String>> strings) {
        List<String> combos = new ArrayList<>(List.of(""));

        combos = strings
                .stream()
                .reduce(combos, (current, inner) -> current.stream()
                        .flatMap(c -> inner.stream().map(c::concat))
                        .toList(), (a, b) -> {
                    a.addAll(b);
                    return a;
                });

        return combos;
    }
}
