package com.walker.passwords.generator;

import com.walker.passwords.model.AsciiTableRange;

import java.util.List;
import java.util.Random;

public class PasswordGeneratorImpl implements PasswordGenerator{
    private final List<AsciiTableRange> ranges;
    private final Random random;

    public PasswordGeneratorImpl(List<AsciiTableRange> ranges, Random random) {
        this.ranges = ranges;
        this.random = new Random();
    }


    @Override
    public String generate(int length) {
    StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            AsciiTableRange range = getRandomRange();
            password.append(generateRandomCharacter(range.start(), range.end()));

        }
        return password.toString();
    }

    private AsciiTableRange getRandomRange() {
        int index = random.nextInt(ranges.size());
        return ranges.get(index);
    }
    private char generateRandomCharacter(int start, int end) {
        int range = end - start + 1;
        return (char) (start + random.nextInt(range));
    }
}
