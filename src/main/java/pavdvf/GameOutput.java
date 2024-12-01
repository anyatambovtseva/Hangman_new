package pavdvf;

public class GameOutput {
    public String getWelcomeMessage() {
        return "Добро пожаловать в игру Виселица!";
    }

    public String getHelpMessage() {
        return "Правила игры:\n" +
                "1. Я загадаю слово на тему животные, а вы будете пытаться его угадать, вводя буквы.\n" +
                "2. У вас есть 5 попыток.\n" +
                "3. Если вы введете букву, которая не входит в слово, попытка будет считаться использованной.\n" +
                "4. Чтобы увидеть это сообщение снова, введите команду /help.\n" +
                "5. Чтобы выйти из игры, введите команду /exit.";
    }

    public String getCurrentState(String guessedWord, int remainingTries) {
        return "Слово: " + guessedWord + "\n" +
                "Попытки оставшиеся: " + remainingTries;
    }

    public String getResult(String wordToGuess, boolean isWon) {
        if (isWon) {
            return "Поздравляем! Вы угадали слово: " + wordToGuess;
        } else {
            return "Вы проиграли! Загаданное слово было: " + wordToGuess;
        }
    }
}
