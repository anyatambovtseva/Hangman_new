package pavdvf;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: необходимо указать аргумент (telegram, console или console_AND_telegram).");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        switch (args[0]) {
            case "telegram" -> {
                try {
                    TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                    TGBot bot = new TGBot();
                    botsApi.registerBot(bot);
                    System.out.println("Телеграмм бот запущен!");
                } catch (TelegramApiException e) {
                    System.out.println("Ошибка при запуске телеграмм бота: " + e.getMessage());
                }
            }
            case "console" -> {
                new Thread(() -> new ConsoleApp(scanner).start()).start();
                System.out.println("Консольный бот запущен!");
            }
            case "console_AND_telegram" -> {
                try {
                    TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                    TGBot bot = new TGBot();
                    botsApi.registerBot(bot);
                    System.out.println("Телеграмм бот запущен!");
                } catch (TelegramApiException e) {
                    System.out.println("Ошибка при запуске телеграмм бота: " + e.getMessage());
                }
                new Thread(() -> new ConsoleApp(scanner).start()).start();
                System.out.println("Консольный бот запущен!");
            }
            default -> System.out.println("Ошибка: введены неправильные аргументы. " +
                    "Используйте 'telegram', 'console' или 'console_AND_telegram'.");
        }
    }
}
