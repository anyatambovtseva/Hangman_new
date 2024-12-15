package pavdvf;

import java.io.*;
import java.util.Properties;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import java.util.List;
import java.util.Random;
import java.util.HashMap;
import java.io.File;

public class TGBot extends TelegramLongPollingBot {
    private String botUsername;
    private String botToken;
    private WordLoader wordLoader;
    private GameOutput gameOutput;

    private final List<String> attemptImages = List.of(
            "src/main/resources/игра_виселица_5.jpg",
            "src/main/resources/игра_виселица_4.jpg",
            "src/main/resources/игра_виселица_3.jpg",
            "src/main/resources/игра_виселица_2.jpg",
            "src/main/resources/игра_виселица_1.jpg"
    );
    private final String startImage = "src/main/resources/приветствие.jpg";
    private final String winImage = "src/main/resources/победа.jpg";
    private final String loseImage = "src/main/resources/игра_виселица_0.jpg";

    private HashMap<Long, GameLogic> userGames;

    public TGBot() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/bot.properties")) {
            props.load(input);
            botUsername = props.getProperty("bot.name");
            botToken = props.getProperty("bot.token");
            wordLoader = new WordLoader();
            gameOutput = new GameOutput();
            wordLoader.loadWords("виселица.txt");
            userGames = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
            botUsername = botToken = "";
        }
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
                    sendMessage(chatId, gameOutput.getHelpMessage());
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

    private void sendPhoto(long chatId, String imagePath, String caption) {
        SendPhoto message = new SendPhoto();
        message.setChatId(String.valueOf(chatId));
        message.setPhoto(new InputFile(new File(imagePath)));
        message.setCaption(caption);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void startGame(long chatId) {
        List<String> words = wordLoader.getWords();
        String wordToGuess = words.get(new Random().nextInt(words.size()));
        GameLogic gameLogic = new GameLogic(wordToGuess);
        userGames.put(chatId, gameLogic);
        sendPhoto(chatId, startImage, gameOutput.getWelcomeMessage());
        printCurrentState(chatId);
    }

    private void handleGuess(long chatId, String messageText) {
        GameLogic gameLogic = userGames.get(chatId);
        if (gameLogic == null) {
            sendMessage(chatId, "Сначала начните игру с командой /start.");
            return;
        }

        if (messageText.equals("/help")) {
            sendMessage(chatId, gameOutput.getHelpMessage());
            return;
        }

        if (messageText.equals("/exit")) {
            endGame(chatId);
            return;
        }

        if (messageText.length() != 1 || !gameLogic.isValidCharacter(messageText.charAt(0))) {
            sendMessage(chatId, "Пожалуйста, введите одну маленькую букву русского алфавита.");
            return;
        }

        char guess = messageText.charAt(0);
        if (!gameLogic.makeGuess(guess)) {
            sendMessage(chatId, "Неправильно! Буква не в слове.");
        } else {
            sendMessage(chatId, "Правильно!");
        }
        if (gameLogic.isGameWon()) {
            printResult(chatId, true);
            userGames.remove(chatId);
        } else if (gameLogic.isGameOver()) {
            printResult(chatId, false);
            userGames.remove(chatId);
        } else {
            printCurrentState(chatId);
        }
    }



    private void endGame(long chatId) {
        userGames.remove(chatId);
        sendMessage(chatId, "Вы вышли из игры. Чтобы начать заново, введите /start.");
    }

    private void printCurrentState(long chatId) {
        GameLogic gameLogic = userGames.get(chatId);
        if (gameLogic != null) {
            String image = attemptImages.get(5 - gameLogic.getRemainingTries());
            sendPhoto(chatId, image, gameOutput.getCurrentState(gameLogic.getGuessedWord(), gameLogic.getRemainingTries()));
        }
    }

    private void printResult(long chatId, boolean isWin) {
        if (isWin) {
            sendPhoto(chatId, winImage, "Поздравляем! Вы выиграли!");
        } else {
            sendPhoto(chatId, loseImage, "Игра окончена. Вы проиграли.");
        }
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