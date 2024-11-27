package pavdvf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLogic {
    private String wordToGuess; // Загаданное слово
    private List<Character> guessedLetters; // Угаданные буквы
    private int remainingTries; // Оставшиеся попытки

    // Конструктор, который инициализирует игру
    public GameLogic(String wordToGuess) {
        this.wordToGuess = getRandomWord(); // Получаем случайное слово
        this.guessedLetters = new ArrayList<>(); // Инициализируем список угаданных букв
        this.remainingTries = 5; // Устанавливаем количество попыток
    }

    // Метод для получения случайного слова (можно заменить на свой список слов)
    private String getRandomWord() {
        String[] words = {"кот", "собака", "птица", "слон", "жираф"};
        Random random = new Random();
        return words[random.nextInt(words.length)];
    }

    // Метод для обработки введенной буквы
    public boolean guessLetter(char letter) {
        if (guessedLetters.contains(letter)) {
            return false; // Буква уже была угадана
        }

        guessedLetters.add(letter); // Добавляем букву в список угаданных

        if (!wordToGuess.contains(String.valueOf(letter))) {
            remainingTries--; // Уменьшаем количество оставшихся попыток, если буква не угадана
            return false;
        }
        return true; // Буква угадана
    }

    // Метод для проверки, выиграна ли игра
    public boolean isGameWon() {
        for (char c : wordToGuess.toCharArray()) {
            if (!guessedLetters.contains(c)) {
                return false; // Если есть буквы, которые не были угаданы, игра не выиграна
            }
        }
        return true; // Все буквы угаданы
    }

    // Метод для проверки, проиграна ли игра
    public boolean isGameLost() {
        return remainingTries <= 0; // Если попытки закончились, игра проиграна
    }

    // Метод для получения текущего состояния слова с угаданными буквами
    public String getCurrentState() {
        StringBuilder currentState = new StringBuilder();
        for (char c : wordToGuess.toCharArray()) {
            if (guessedLetters.contains(c)) {
                currentState.append(c); // Если буква угадана, добавляем ее к текущему состоянию
            } else {
                currentState.append('_'); // Если не угадана, ставим подчеркивание
            }
        }
        return currentState.toString();
    }

    // Геттеры для оставшихся попыток и загаданного слова
    public int getRemainingTries() {
        return remainingTries;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }
}

