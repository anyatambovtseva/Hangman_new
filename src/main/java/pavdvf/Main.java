package pavdvf;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

import java.util.List;
import java.util.Random;

public class Main
{
    private WordLoader wordLoader;
    private UserDialog userDialog;
    private GameOutput gameOutput;

    public Main()
    {
        wordLoader = new WordLoader();
        userDialog = new UserDialog();
        gameOutput = new GameOutput();
    }

    public void start()
    {
        try {
            // Создаем экземпляр TelegramBotsApi с использованием DefaultBotSession
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TGBot());

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        try
        {
            wordLoader.loadWords("виселица.txt");
        }
        catch (IOException e)
        {
            System.out.println("Ошибка при загрузке слов: " + e.getMessage());
            return;
        }
        gameOutput.printWelcomeMessage();
        boolean playAgain;
        do
        {
            playAgain = false;
            List<String> words = wordLoader.getWords();
            String wordToGuess = words.get(new Random().nextInt(words.size()));
            GameLogic gameLogic = new GameLogic(wordToGuess);

            while (!gameLogic.isGameOver() && !gameLogic.isGameWon())
            {
                gameOutput.printCurrentState(gameLogic.getGuessedWord(), gameLogic.getRemainingTries());
                String input = userDialog.getInput("Введите букву: ");
                if (input.equals("/help"))
                {
                    gameOutput.printHelpMessage();
                    continue;
                }
                if (input.equals("/exit"))
                {
                    System.out.println("Вы вышли из игры. Спасибо за игру!");
                    userDialog.close();
                    return;
                }
                if (input.length() != 1 || !gameLogic.isValidCharacter(input.charAt(0)))
                {
                    System.out.println("Пожалуйста, введите одну букву русского алфавита.");
                    continue;
                }
                char guess = input.charAt(0);
                if (!gameLogic.makeGuess(guess))
                {
                    System.out.println("Неправильно! Буква не в слове.");
                }
                else
                {
                    System.out.println("Правильно!");
                }
            }
            gameOutput.printResult(wordToGuess, gameLogic.isGameWon());
            String response = userDialog.getInput("Хотите сыграть еще раз? (да/нет): ");
            if (response.equals("да"))
            {
                playAgain = true;
            }
        }

        while (playAgain);
        userDialog.close();
    }

    public static void main(String[] args)
    {
        Main main = new Main();
        main.start();
    }
}