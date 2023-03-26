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
        String filePath = "src/test/java/lox/test/test.lox";
        Lox.main(new String[]{filePath});
        assertFalse(outContent.toString().contains("Expected output"));
    }

    @Test
    void testClouse() throws IOException {
        String filePath = "src/test/java/lox/test/clouse.lox";
        Lox.main(new String[]{filePath});
        System.out.println(outContent);
        assertTrue(outContent.toString().contains("global\nglobal"));
    }

    @Test
    void testClass() throws IOException {
        String filePath = "src/test/java/lox/test/class.lox";
        Lox.main(new String[]{filePath});
        System.out.println(outContent);
        assertTrue(outContent.toString().contains("DevonshireCream"));
    }

    @Test
    void testClass1() throws IOException {
        String filePath = "src/test/java/lox/test/class1.lox";
        Lox.main(new String[]{filePath});
        System.out.println(outContent);
        assertTrue(outContent.toString().contains("Bagel instance"));

    }

    @Test
    void testMethod() throws IOException {
        String filePath = "src/test/java/lox/test/method.lox";
        Lox.main(new String[]{filePath});
        System.out.println(outContent);
        assertTrue(outContent.toString().contains("Crunch crunch crunch!"));

    }
}
