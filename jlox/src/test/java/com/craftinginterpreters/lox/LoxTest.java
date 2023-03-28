package com.craftinginterpreters.lox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    @Test
    void testStatement() throws IOException {
        String Path = "src/main/resources/test/stmt.lox";
        String expectedOutput =
                "inner a\n" +
                "outer b\n" +
                "global c\n" +
                "outer a\n" +
                "outer b\n" +
                "global c\n" +
                "global a\n" +
                "global b\n" +
                "global c\n";
        Lox.main(new String[]{Path});
        assertEquals(expectedOutput, getOutput());
    }

    @Test
    void testIfElse() throws IOException {
        String Path = "src/main/resources/test/if-else.lox";
        String expectedOutput =
                "1\n" +
                "0\n";
        Lox.main(new String[]{Path});
        assertEquals(expectedOutput, getOutput());
    }
}
