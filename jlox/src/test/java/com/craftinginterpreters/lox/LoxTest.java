package com.craftinginterpreters.lox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

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

    @Test
    void testLogic() throws IOException {
        String Path = "src/main/resources/test/logic.lox";
        String expectedOutput =
                "hi\n" +
                "yes\n";
        Lox.main(new String[]{Path});
        assertEquals(expectedOutput, getOutput());
    }

    @Test
    void testLoop() throws IOException {
        String Path = "src/main/resources/test/loop.lox";
        String expectedOutput =
                "0\n" +
                "1\n" +
                "1\n" +
                "2\n" +
                "3\n" +
                "5\n" +
                "8\n" +
                "13\n" +
                "21\n" +
                "34\n" +
                "55\n" +
                "89\n" +
                "144\n" +
                "233\n" +
                "377\n" +
                "610\n" +
                "987\n" +
                "1597\n" +
                "2584\n" +
                "4181\n" +
                "6765\n";
        Lox.main(new String[]{Path});
        assertEquals(expectedOutput, getOutput());
    }

    @Test
    void testFun() throws IOException {
        String Path = "src/main/resources/test/fun.lox";
        String expectedOutput =
                "Hi, Dear Reader!\n" +
                "0\n" +
                "1\n" +
                "1\n" +
                "2\n" +
                "3\n" +
                "5\n" +
                "8\n" +
                "13\n" +
                "21\n" +
                "34\n" +
                "55\n" +
                "89\n" +
                "144\n" +
                "233\n" +
                "377\n" +
                "610\n" +
                "987\n" +
                "1597\n" +
                "2584\n" +
                "4181\n";
        Lox.main(new String[]{Path});
        assertEquals(expectedOutput, getOutput());
    }
}
