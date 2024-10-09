import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class GameLogicTest {
    private GameLogic gameLogic;

    @BeforeEach
    public void setUp() {
        gameLogic = new GameLogic("кошка");
    }

    @Test
    public void testInitialGuessedWord() {
        assertEquals("_____", gameLogic.getGuessedWord());
    }

    @Test
    public void testMakeGuessCorrect() {
        assertTrue(gameLogic.makeGuess('к'));
        assertEquals("к__к_", gameLogic.getGuessedWord());
        assertEquals(5, gameLogic.getRemainingTries());
    }

    @Test
    public void testMakeGuessIncorrect() {
        assertFalse(gameLogic.makeGuess('х'));
        assertEquals("_____", gameLogic.getGuessedWord());
        assertEquals(4, gameLogic.getRemainingTries());
    }

    @Test
    public void testIsGameWon() {
        // Угадываем все буквы
        for (char c : "кошка".toCharArray()) {
            gameLogic.makeGuess(c);
        }
        assertTrue(gameLogic.isGameWon());
    }

    @Test
    public void testRemainingTriesAfterIncorrectGuesses() {
        gameLogic.makeGuess('х');
        gameLogic.makeGuess('ф');
        assertEquals(3, gameLogic.getRemainingTries());
    }


    @Test
    void testIsValidCharacter_ValidLowercase() throws Exception {
        Method method = GameLogic.class.getDeclaredMethod("isValidCharacter", char.class);
        method.setAccessible(true);

        assertTrue((Boolean) method.invoke(gameLogic, 'а'));
        assertTrue((Boolean) method.invoke(gameLogic, 'я'));
        assertTrue((Boolean) method.invoke(gameLogic, 'в'));
    }

    @Test
    void testIsValidCharacter_InvalidCharacter() throws Exception {
        Method method = GameLogic.class.getDeclaredMethod("isValidCharacter", char.class);
        method.setAccessible(true);

        assertFalse((Boolean) method.invoke(gameLogic, 'a'));
        assertFalse((Boolean) method.invoke(gameLogic, '1'));
        assertFalse((Boolean) method.invoke(gameLogic, ' '));
        assertFalse((Boolean) method.invoke(gameLogic, 'I'));
    }
}
