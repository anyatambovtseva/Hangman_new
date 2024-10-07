import java.util.List;
import java.util.Random;

public class GameLogic
{
    private final int MAX_TRIES = 5;
    private String wordToGuess;
    private StringBuilder guessedWord;
    private int tries;
    private boolean[] guessedLetters;

    public GameLogic(String wordToGuess)
    {
        this.wordToGuess = wordToGuess;
        this.guessedWord = new StringBuilder("_".repeat(wordToGuess.length()));
        this.tries = 0;
        this.guessedLetters = new boolean[33];
    }

    public boolean makeGuess(char guess)
    {
        if (guessedLetters[guess - 'а'])
        {
            return false;
        }
        guessedLetters[guess - 'а'] = true;

        if (wordToGuess.indexOf(guess) >= 0)
        {
            for (int i = 0; i < wordToGuess.length(); i++)
            {
                if (wordToGuess.charAt(i) == guess)
                {
                    guessedWord.setCharAt(i, guess);
                }
            }
            return true;
        }
        else
        {
            tries++;
            return false;
        }
    }

    public boolean isGameWon()
    {
        return guessedWord.toString().equals(wordToGuess);
    }

    public boolean isGameOver()
    {
        return tries >= MAX_TRIES;
    }

    public String getGuessedWord()
    {
        return guessedWord.toString();
    }

    public int getRemainingTries()
    {
        return MAX_TRIES - tries;
    }
}
