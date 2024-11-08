package pavdvf;

//import org.telegram.telegrambots.bots.TelegramBotsApi;
//import org.telegram.telegrambots.exceptions.TelegramApiException;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class BotStarter {
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

        try {
            botsApi.registerBot(new EchoBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
