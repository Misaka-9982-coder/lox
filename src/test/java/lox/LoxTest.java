package lox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class LoxTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testRunPrompt() throws IOException {
        String input = "print 2 + 3;\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Lox.main(new String[]{});
        assertTrue(outContent.toString().contains("5"));
    }

    @Test
    void testRunFile() throws IOException {
        String filePath = "src/test/java/lox/test.lox";
        Lox.main(new String[]{filePath});
        assertFalse(outContent.toString().contains("Expected output"));
    }
}
