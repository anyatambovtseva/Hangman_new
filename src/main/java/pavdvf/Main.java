package pavdvf;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        Scanner scanner = new Scanner(System.in);
        if (args[0].equals("telegram") && args.length != 0) {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            TGBot bot = new TGBot();
            botsApi.registerBot(bot);
            System.out.println("Телеграмм бот запущен!");
        } else if (args[0].equals("console") && args.length != 0) {
            new Thread(() -> new ConsoleApp(scanner).start()).start();
            System.out.println("Консольный бот запущен!");
        } else if (args[0].equals("console_AND_telegram") && args.length != 0) {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            TGBot bot = new TGBot();
            botsApi.registerBot(bot);
            System.out.println("Телеграмм бот запущен!");
            new Thread(() -> new ConsoleApp(scanner).start()).start();
            System.out.println("Консольный бот запущен!");
        } else {
            System.out.println("введите правильные аргументы!");
        }
    }
}