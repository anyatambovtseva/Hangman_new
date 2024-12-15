import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pavdvf.GameLogic;

import static org.junit.jupiter.api.Assertions.*;

class GameLogicTest {

    private GameLogic gameLogic;

    @BeforeEach
    void setUp() {
        gameLogic = new GameLogic("жираф");
    }

    @Test
    void testInitialState() {
        assertEquals("_____", gameLogic.getCurrentState());
        assertEquals(5, gameLogic.getRemainingTries());
        assertFalse(gameLogic.isGameOver());
        assertFalse(gameLogic.isGameWon());
    }

    @Test
    void testMakeGuessCorrectLetter() {
        assertTrue(gameLogic.makeGuess('ж'));
        assertEquals("ж____", gameLogic.getCurrentState());
        assertEquals(5, gameLogic.getRemainingTries());
        assertFalse(gameLogic.isGameOver());
        assertFalse(gameLogic.isGameWon());

        assertTrue(gameLogic.makeGuess('и'));
        assertEquals("жи___", gameLogic.getCurrentState());
        assertFalse(gameLogic.isGameWon());
    }

    @Test
    void testMakeGuessIncorrectLetter() {
        assertFalse(gameLogic.makeGuess('x'));
        assertEquals(4, gameLogic.getRemainingTries());
        assertFalse(gameLogic.isGameOver());

        assertEquals("_____", gameLogic.getCurrentState());
    }

    @Test
    void testMakeGuessRepeatedLetter() {
        assertTrue(gameLogic.makeGuess('ж'));
        assertFalse(gameLogic.makeGuess('ж'));
        assertEquals("ж____", gameLogic.getCurrentState());
    }

    @Test
    void testWinGame() {
        gameLogic.makeGuess('ж');
        gameLogic.makeGuess('и');
        gameLogic.makeGuess('р');
        gameLogic.makeGuess('а');
        gameLogic.makeGuess('ф');

        assertTrue(gameLogic.isGameWon());
    }

    @Test
    void testLoseGame() {
        gameLogic.makeGuess('д'); // 1
        gameLogic.makeGuess('п'); // 2
        gameLogic.makeGuess('х'); // 3
        gameLogic.makeGuess('у'); // 4
        gameLogic.makeGuess('я'); // 5

        assertTrue(gameLogic.isGameOver());
    }

    @Test
    void testValidCharacter() {
        assertTrue(gameLogic.isValidCharacter('а'));
        assertTrue(gameLogic.isValidCharacter('ё'));
        assertFalse(gameLogic.isValidCharacter('A'));
        assertFalse(gameLogic.isValidCharacter('1'));
    }
}
