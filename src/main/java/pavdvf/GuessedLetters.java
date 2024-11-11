package pavdvf;

import java.util.HashSet;
import java.util.Set;

public class GuessedLetters
{
    private Set<Character> guessedLetters;
    public GuessedLetters()
    {
        this.guessedLetters = new HashSet<>();
    }
    public void addLetter(char letter)
    {
        guessedLetters.add(letter);
    }
    public boolean hasLetter(char letter)
    {
        return guessedLetters.contains(letter);
    }

}