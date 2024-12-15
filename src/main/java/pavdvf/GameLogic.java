package pavdvf;

public class GameLogic {

    private static final int MAX_TRIES = 5;
    private final String wordToGuess;
    private final StringBuilder guessedWord;
    private int tries;
    private final GuessedLetters guessedLetters;

    public GameLogic(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.guessedWord = new StringBuilder("_".repeat(wordToGuess.length()));
        this.tries = 0;
        this.guessedLetters = new GuessedLetters();
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public boolean makeGuess(char guess) {
        if (guessedLetters.hasLetter(guess)) {
            return false;
        }

        guessedLetters.addLetter(guess);

        if (wordToGuess.indexOf(guess) >= 0) {
            for (int i = 0; i < wordToGuess.length(); i++) {
                if (wordToGuess.charAt(i) == guess) {
                    guessedWord.setCharAt(i, guess);
                }
            }
            return true;
        } else {
            tries++;
            return false;
        }
    }

    public boolean isGameWon() {
        for (char c : wordToGuess.toCharArray()) {
            if (!guessedLetters.hasLetter(c)) {
                return false;
            }
        }
        return true;
    }


    public String getCurrentState() {
        StringBuilder currentState = new StringBuilder();
        for (char c : wordToGuess.toCharArray()) {
            if (guessedLetters.hasLetter(c)) {
                currentState.append(c);
            } else {
                currentState.append('_');
            }
        }
        return currentState.toString();
    }

    public boolean isGameOver() {
        return tries >= MAX_TRIES;
    }

    public String getGuessedWord() {
        return guessedWord.toString();
    }

    public int getRemainingTries() {
        return MAX_TRIES - tries;
    }

    public boolean isValidCharacter(char c) {
        return (c >= 'а' && c <= 'я') || c == 'ё';
    }
}
