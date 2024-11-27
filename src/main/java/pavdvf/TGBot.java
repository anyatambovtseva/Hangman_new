package pavdvf;
import java.io.*;
import java.util.Properties;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class TGBot extends TelegramLongPollingBot{
    private String botUsername;
    private String botToken;
    public TGBot()
    {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/bot.properties"))
        {
            props.load(input);
            botUsername = props.getProperty("bot.name");
            botToken = props.getProperty("bot.token");
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
                    printWelcomeMessage(chatId);
                    break;
                case "/help":
                    printHelpMessage(chatId);
                    break;
                case "/exit":
                    // Логика выхода из игры (если требуется)
                    sendMessage(chatId, "Вы вышли из игры.");
                    break;
                default:
                    // Логика обработки ввода пользователя в игре
                    // Например, обработка попытки угадать букву
                    break;
            }
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

    private void printWelcomeMessage(long chatId) {
        sendMessage(chatId, "Добро пожаловать в игру Виселица!");
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

    public void printCurrentState(long chatId, String guessedWord, int remainingTries) {
        String currentState = "Слово: " + guessedWord + "\n" +
                "Попытки оставшиеся: " + remainingTries;
        sendMessage(chatId, currentState);
    }

    public void printResult(long chatId, String wordToGuess, boolean isWon) {
        if (isWon) {
            sendMessage(chatId, "Поздравляем! Вы угадали слово: " + wordToGuess);
        } else {
            sendMessage(chatId, "Вы проиграли! Загаданное слово было: " + wordToGuess);
        }
    }
}
