import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordLoaderTest {

    private WordLoader wordLoader;
    private final String testFilePath = "виселица.txt";

    @BeforeEach
    public void setUp() throws IOException {
        wordLoader = new WordLoader();
    }

    @Test
    public void testLoadWords() throws IOException {
        wordLoader.loadWords(testFilePath);

        List<String> words = wordLoader.getWords();

        assertEquals("кот", words.get(0));
        assertEquals("собака", words.get(1));
        assertEquals("мышь", words.get(2));
    }

}