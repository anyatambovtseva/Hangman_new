package pavdvf;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Какого бота запустить? (1 - консольный, 2 - телеграмм, 3 - оба): ");
        String input = scanner.nextLine();

        try {
            if (input.equals("2") || input.equals("3")) {
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                TGBot bot = new TGBot();
                botsApi.registerBot(bot);
                System.out.println("Телеграмм бот запущен!");
            }

            if (input.equals("1") || input.equals("3")) {
                new Thread(() -> new ConsoleApp(scanner).start()).start();
                System.out.println("Консольный бот запущен!");
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
