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
    void testIsValidCharacter_ValidLowercase() {
        assertTrue(gameLogic.isValidCharacter('а'));
        assertTrue(gameLogic.isValidCharacter('ш'));
    }


    @Test
    void testIsValidCharacter_InvalidCharacter() {
        assertFalse(gameLogic.isValidCharacter('a'));
        assertFalse(gameLogic.isValidCharacter('1'));
        assertFalse(gameLogic.isValidCharacter(' '));
        assertFalse(gameLogic.isValidCharacter('I'));
        assertFalse(gameLogic.isValidCharacter('/'));
    }

}
