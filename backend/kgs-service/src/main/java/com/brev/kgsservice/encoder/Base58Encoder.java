package com.brev.kgsservice.encoder;

import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class Base58Encoder implements Encoder {
    private static final String ALPHABET = "y1sxjdGBHZAXYeQ84U65fvKbMapTh37PckmJSqtnoiErgCzD9RN2uFVWwL";

    /**
     * Encodes a BigInteger value into a Base58 string with a minimum of 7 characters.
     *
     * @param bigInt The BigInteger value to encode.
     * @return The Base58-encoded string with at least 7 characters.
     */
    @Override
    public String encode(BigInteger bigInt) {
        StringBuilder result = new StringBuilder();

        BigInteger base58 = BigInteger.valueOf(58);

        while (bigInt.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] quotientAndRemainder = bigInt.divideAndRemainder(base58);
            int remainder = quotientAndRemainder[1].intValue();
            result.insert(0, ALPHABET.charAt(remainder));
            bigInt = quotientAndRemainder[0];
        }

        // Ensure a minimum of 7 characters
        while (result.length() < 7) {
            result.insert(0, ALPHABET.charAt(0));
        }

        return result.toString();
    }
}