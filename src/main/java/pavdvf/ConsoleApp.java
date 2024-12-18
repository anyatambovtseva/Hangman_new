package pavdvf;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ConsoleApp {
    private WordLoader wordLoader;
    private UserDialog userDialog;
    private GameOutput gameOutput;
    private Scanner scanner;

    private long userId;
    private UserData userData;
    private Hint hint;

    public ConsoleApp(Scanner scanner) {
        this.scanner = scanner;
        wordLoader = new WordLoader();
        userDialog = new UserDialog(scanner);
        gameOutput = new GameOutput();
        userData = new UserData();
        hint = new Hint(userData);
    }

    private void assignUserId() {
        String input = userDialog.getInput("Введите ваш уникальный ID (например, номер пользователя): ");
        try {
            userId = Long.parseLong(input);
            System.out.println("Ваш уникальный ID: " + userId);

            if (!userData.userExists(userId)) {
                userData.addUser(userId);
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка! ID должен быть числом.");
            assignUserId();
        }
    }

    public void start() {
        assignUserId();

        try {
            wordLoader.loadWords("виселица.txt");
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке слов: " + e.getMessage());
            return;
        }
        System.out.println(gameOutput.getWelcomeMessage());
        boolean playAgain;
        do {
            playAgain = false;
            List<String> words = wordLoader.getWords();
            String wordToGuess = words.get(new Random().nextInt(words.size()));
            GameLogic gameLogic = new GameLogic(wordToGuess);

            while (!gameLogic.isGameOver() && !gameLogic.isGameWon()) {
                System.out.println(gameOutput.getCurrentState(gameLogic.getGuessedWord(),
                        gameLogic.getRemainingTries()));
                String input = userDialog.getInput("Введите букву или команду (/help, /exit, /hint): ");

                if (input.equals("/help")) {
                    System.out.println(gameOutput.getHelpMessage());
                    continue;
                }

                if (input.equals("/exit")) {
                    System.out.println("Вы вышли из игры. Спасибо за игру!");
                    return;
                }

                if (input.equals("/hint")) {
                    useHint(gameLogic);
                    continue;
                }

                if (input.length() != 1 || !gameLogic.isValidCharacter(input.charAt(0))) {
                    System.out.println("Пожалуйста, введите одну маленькую букву русского алфавита.");
                    continue;
                }

                char guess = input.charAt(0);
                if (!gameLogic.makeGuess(guess)) {
                    System.out.println("Неправильно! Буква не в слове.");
                } else {
                    System.out.println("Правильно!");
                }
            }

            if (gameLogic.isGameWon()) {
                System.out.println("Поздравляем! Вы выиграли!");
                int coins = userData.getCoins(userId);
                userData.updateCoins(userId, coins + 1);
                System.out.println("Ваши монеты увеличены! Теперь у вас: " + (coins + 1));
            } else {
                System.out.println("Вы проиграли! Загаданное слово: " + wordToGuess);
            }

            String response = userDialog.getInput("Хотите сыграть еще раз? (да/нет): ");
            if (response.equals("да")) {
                playAgain = true;
            }
        } while (playAgain);
    }

    private void useHint(GameLogic gameLogic) {
        String hintMessage = hint.provideHint(userId, gameLogic);
        System.out.println(hintMessage);
    }
}
