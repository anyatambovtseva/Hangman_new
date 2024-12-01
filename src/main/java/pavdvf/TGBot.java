package pavdvf;

import java.io.*;
import java.util.Properties;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TGBot extends TelegramLongPollingBot {
    private String botUsername;
    private String botToken;
    private GameLogic gameLogic;
    private List<String> words;

    public TGBot() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/bot.properties")) {
            props.load(input);
            botUsername = props.getProperty("bot.name");
            botToken = props.getProperty("bot.token");
            loadWords("src/main/resources/виселица.txt");
        } catch (IOException e) {
            e.printStackTrace();
            botUsername = botToken = "";
        }
    }

    private void loadWords(String filePath) throws IOException {
        words = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(line.trim());
        }
        reader.close();
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    startGame(chatId);
                    break;
                case "/help":
                    printHelpMessage(chatId);
                    break;
                case "/exit":
                    endGame(chatId);
                    break;
                default:
                    handleGuess(chatId, messageText);
                    break;
            }
        }
    }

    private void startGame(long chatId) {
        String wordToGuess = words.get(new Random().nextInt(words.size()));
        gameLogic = new GameLogic(wordToGuess);
        printCurrentState(chatId);
    }

    private void handleGuess(long chatId, String messageText) {
        if (gameLogic == null) {
            sendMessage(chatId, "Сначала начните игру с командой /start.");
            return;
        }
        if (messageText.length() == 1) {
            char guessedLetter = messageText.charAt(0);
            boolean correctGuess = gameLogic.guessLetter(guessedLetter);

            if (!correctGuess) {
                sendMessage(chatId, "Эта буква уже была угадана или ее нет в слове.");
            }

            if (gameLogic.isGameWon()) {
                printResult(chatId, true);
                gameLogic = null;
            } else if (gameLogic.isGameOver()) {
                printResult(chatId, false);
                gameLogic = null;
            } else {
                printCurrentState(chatId);
            }
        } else {
            sendMessage(chatId, "Пожалуйста, вводите только одну букву.");
        }
    }

    private void endGame(long chatId) {
        gameLogic = null;
        sendMessage(chatId, "Вы вышли из игры. Чтобы начать заново, введите /start.");
    }

    private void printCurrentState(long chatId) {
        String currentState = gameLogic.getCurrentState();
        int remainingTries = gameLogic.getRemainingTries();
        String message = "Слово: " + currentState + "\nПопытки оставшиеся: " + remainingTries;
        sendMessage(chatId, message);
    }

    private void printResult(long chatId, boolean isWon) {
        String wordToGuess = gameLogic.getWordToGuess();
        if (isWon) {
            sendMessage(chatId, "Поздравляем! Вы угадали слово: " + wordToGuess);
        } else {
            sendMessage(chatId, "Вы проиграли! Загаданное слово было: " + wordToGuess);
        }
    }

    private void printHelpMessage(long chatId) {
        String helpMessage = "Правила игры:\n" +
                "1. Я загадаю слово на тему животные, а вы будете пытаться его угадать, вводя буквы.\n" +
                "2. У вас есть 5 попыток.\n" +
                "3. Если вы введете букву, которая не входит в слово, попытка будет считаться использованной.\n" +
                "4. Чтобы увидеть это сообщение снова, введите команду /help.\n" +
                "5. Чтобы выйти из игры, введите команду /exit.";
        sendMessage(chatId, helpMessage);
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}