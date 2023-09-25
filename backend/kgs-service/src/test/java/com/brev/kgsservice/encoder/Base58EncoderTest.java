package com.brev.kgsservice.encoder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Base58EncoderTest {
    private Base58Encoder encoder;

    @BeforeEach
    public void setUp() {
        encoder = new Base58Encoder();
    }

    @Test
    public void testEncode() {
        testEncodeAndCheckResult("0", "yyyyyyy");
        testEncodeAndCheckResult("12345", "yyyyxtR");
        testEncodeAndCheckResult("511623456789", "eahbWyk");
    }

    private void testEncodeAndCheckResult(String inputStr, String expected) {
        BigInteger input = new BigInteger(inputStr);
        String result = encoder.encode(input);
        assertEquals(expected, result);
    }
}
