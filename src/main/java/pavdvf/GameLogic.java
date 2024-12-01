package pavdvf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLogic {
    private final int MAX_TRIES = 5;
    private String wordToGuess;
    private StringBuilder guessedWord;
    private int tries;
    private GuessedLetters guessedLetters;

    // Конструктор, который инициализирует игру
    public GameLogic(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.guessedWord = new StringBuilder("_".repeat(wordToGuess.length()));
        this.tries = 0;
        this.guessedLetters = new GuessedLetters();
    }

    public boolean makeGuess(char guess)
    {
        if (guessedLetters.hasLetter(guess))
        {
            return false;
        }
        guessedLetters.addLetter(guess);

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

    // Метод для получения случайного слова (можно заменить на свой список слов)
    private String getRandomWord() {
        String[] words = {"кот", "собака", "птица", "слон", "жираф"};
        Random random = new Random();
        return words[random.nextInt(words.length)];
    }

    // Метод для обработки введенной буквы
    public boolean guessLetter(char letter) {
        if (guessedLetters.hasLetter(letter)) {
            return false; // Буква уже была угадана
        }

        guessedLetters.addLetter(letter); // Добавляем букву в список угаданных

        if (!wordToGuess.contains(String.valueOf(letter))) {

            tries--; // Уменьшаем количество оставшихся попыток, если буква не угадана
            return false;
        }
        return true; // Буква угадана
    }

    // Метод для проверки, выиграна ли игра
    public boolean isGameWon() {
        for (char c : wordToGuess.toCharArray()) {
            if (!guessedLetters.hasLetter(c)) {
                return false; // Если есть буквы, которые не были угаданы, игра не выиграна
            }
        }
        return true; // Все буквы угаданы
    }

    public String getWordToGuess() {
        return wordToGuess;
    }
/*
    // Метод для проверки, проиграна ли игра
    public boolean isGameLost() {
        return remainingTries <= 0; // Если попытки закончились, игра проиграна
    }
*/
    // Метод для получения текущего состояния слова с угаданными буквами
    public String getCurrentState() {
        StringBuilder currentState = new StringBuilder();
        for (char c : wordToGuess.toCharArray()) {
            if (guessedLetters.hasLetter(c)) {
                currentState.append(c); // Если буква угадана, добавляем ее к текущему состоянию
            } else {
                currentState.append('_'); // Если не угадана, ставим подчеркивание
            }
        }
        return currentState.toString();
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

    public boolean isValidCharacter(char c)
    {
        return (c >= 'а' && c <= 'я') || c == 'ё';
    }

}