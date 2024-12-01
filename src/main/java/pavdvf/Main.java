package pavdvf;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            TGBot bot = new TGBot();
            botsApi.registerBot(bot);
            System.out.println("Бот запущен!");
            new Thread(() -> {new ConsoleApp().start();}).start();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}