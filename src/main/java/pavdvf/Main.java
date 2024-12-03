package pavdvf;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        boolean startTelegramBot = false;
        boolean startConsoleApp = false;

        for (String arg : args) {
            if (arg.equalsIgnoreCase("bot")) {
                startTelegramBot = true;
            } else if (arg.equalsIgnoreCase("console")) {
                startConsoleApp = true;
            }
        }

        try {
            if (startTelegramBot) {
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                TGBot bot = new TGBot();
                botsApi.registerBot(bot);
                System.out.println("ТГ бот запущен!");
            }

            if (startConsoleApp) {
                new Thread(() -> new ConsoleApp().start()).start();
                System.out.println("Консольный бот запущен!");
            }

            if (!startTelegramBot && !startConsoleApp) {
                System.out.println("Укажите аргументы: 'bot', 'console' или оба.");
            }

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
