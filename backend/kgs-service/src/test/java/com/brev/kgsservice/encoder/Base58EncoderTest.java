package com.brev.kgsservice.encoder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Base58EncoderTest {
    private Base58Encoder encoder;

    static Stream<Arguments> encodingTestCases() {
        return Stream.of(
                arguments("0", "yyyyyyy"),
                arguments("12345", "yyyyxtR"),
                arguments("511623456789", "eahbWyk")
        );
    }

    @BeforeEach
    public void setUp() {
        encoder = new Base58Encoder();
    }

    @ParameterizedTest
    @MethodSource("encodingTestCases")
    public void encode(String inputStr, String expected) {
        BigInteger input = new BigInteger(inputStr);
        String result = encoder.encode(input);
        assertEquals(expected, result);
    }
}
