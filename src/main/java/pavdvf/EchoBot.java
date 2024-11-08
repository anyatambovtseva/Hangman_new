package pavdvf;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class EchoBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        // Проверяем, что сообщение не пустое и содержит текст
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            // Отправляем обратно то же сообщение
            sendMessage(chatId, messageText);
        }
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message); // Отправка сообщения
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "hangman100bot"; // Замените на имя вашего бота
    }

    @Override
    public String getBotToken() {
        return "7563996661:AAGFeNZsUQZSSFE53nqDKQwgklmFMesfbIs"; // Замените на токен вашего бота
    }
}
