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

    private static final String FILE_PATH = "user_data.txt";
    private Map<Long, Integer> userCoins;

    public UserData() {
        userCoins = new HashMap<>();
        loadUserData();
    }

    private void loadUserData() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    System.out.println("Не удалось создать файл для данных пользователей.");
                }
            } catch (IOException e) {
                System.out.println("Ошибка при создании файла: " + e.getMessage());
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    try {
                        long userId = Long.parseLong(parts[0]);
                        int coins = Integer.parseInt(parts[1]);
                        userCoins.put(userId, coins);
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка формата данных в строке: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    private void saveUserData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<Long, Integer> entry : userCoins.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    public int getCoins(long userId) {
        return userCoins.getOrDefault(userId, 2);
    }

    public void updateCoins(long userId, int coins) {
        userCoins.put(userId, coins);
        saveUserData();
    }

    public void addUser(long userId) {
        if (!userCoins.containsKey(userId)) {
            userCoins.put(userId, 2);
            saveUserData();
        }
    }

    public void removeUser(long userId) {
        if (userCoins.containsKey(userId)) {
            userCoins.remove(userId);
            saveUserData();
        }
    }

    public boolean userExists(long userId) {
        return userCoins.containsKey(userId);
    }
}
