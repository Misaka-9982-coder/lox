package com.craftinginterpreters.lox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoxTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        return testOut.toString();
    }

    @AfterEach
    void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    void testSuccessfulExpressionEvaluation() throws Exception {
        String input = "print 1 + 2 * 3;\n";
        String expectedOutput = "> 7\n> ";

        provideInput(input);
        Lox.main(new String[]{});
        assertEquals(expectedOutput, getOutput());
    }

    @Test
    void testRuntimeError() throws Exception {
        String input = "print 10 / (5 - 5);\n";
        String expectedOutput = "> Infinity\n> ";

        provideInput(input);
        Lox.main(new String[]{});
        assertEquals(expectedOutput, getOutput());
    }
}
