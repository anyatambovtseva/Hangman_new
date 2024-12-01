package pavdvf;

public class GameOutput
{

    public void printWelcomeMessage()
    {
        System.out.println("Добро пожаловать в игру Виселица!");
    }

    public void printHelpMessage()
    {
        System.out.println("Правила игры:");
        System.out.println("1. Я загадаю слово на тему животные, а вы будете пытаться его угадать, вводя буквы.");
        System.out.println("2. У вас есть 5 попыток.");
        System.out.println("3. Если вы введете букву, которая не входит в слово, попытка будет считаться использованной.");
        System.out.println("4. Чтобы увидеть это сообщение снова, введите команду /help.");
        System.out.println("5. Чтобы выйти из игры, введите команду /exit.");
    }

    public String printCurrentState(String guessedWord, int remainingTries)
    {
        System.out.println("Слово: " + guessedWord);
        System.out.println("Попытки оставшиеся: " + remainingTries);
        return "";
    }

    public void printResult(String wordToGuess, boolean isWon)
    {
        if (isWon)
        {
            System.out.println("Поздравляем! Вы угадали слово: " + wordToGuess);
        }
        else
        {
            System.out.println("Вы проиграли! Загаданное слово было: " + wordToGuess);
        }
    }
}