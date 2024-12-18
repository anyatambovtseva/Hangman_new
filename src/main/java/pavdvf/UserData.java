package pavdvf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserData {

    private static final String DIRECTORY_PATH = "user_data/";
    private Map<Long, Integer> userCoins;

    public UserData() {
        userCoins = new HashMap<>();
        loadUserData();
    }

    private void loadUserData() {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                System.out.println("Не удалось создать папку для данных пользователей.");
            }
        }

        File[] userFiles = directory.listFiles();
        if (userFiles != null) {
            for (File file : userFiles) {
                if (file.isFile()) {
                    long userId = Long.parseLong(file.getName().replace(".txt", ""));
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line = reader.readLine();
                        if (line != null) {
                            int coins = Integer.parseInt(line);
                            userCoins.put(userId, coins);
                        }
                    } catch (IOException e) {
                        System.out.println("Ошибка при чтении файла для пользователя" + userId + ": " + e.getMessage());
                    }
                }
            }
        }
    }

    private void saveUserData(long userId) {
        File userFile = new File(DIRECTORY_PATH + userId + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
            writer.write(String.valueOf(userCoins.get(userId)));
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл для пользователя " + userId + ": " + e.getMessage());
        }
    }

    public int getCoins(long userId) {
        return userCoins.getOrDefault(userId, 2);
    }

    public void updateCoins(long userId, int coins) {
        userCoins.put(userId, coins);
        saveUserData(userId);
    }

    public void addUser(long userId) {
        if (!userCoins.containsKey(userId)) {
            userCoins.put(userId, 2);
            saveUserData(userId);
        }
    }

    public boolean userExists(long userId) {
        File userFile = new File(DIRECTORY_PATH + userId + ".txt");
        return userFile.exists();
    }
}
