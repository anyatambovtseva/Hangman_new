package pavdvf;
//test
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
        try (InputStream input = new FileInputStream("src/main/resources/bot.properties")) {
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
            Long chatId = update.getMessage().getChatId();
            if (messageText.equals("/start")) {
                this.sendMessage(chatId, "Бот успешно перезапущен. Отправь любое сообщение");
            }
            else {
                this.sendMessage(chatId, messageText);
            }
        }
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

//test
}