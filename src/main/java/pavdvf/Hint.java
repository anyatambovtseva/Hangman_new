package pavdvf;

import java.util.HashSet;
import java.util.Set;

public class Hint {
    private final UserData userData;
    private final Set<Integer> usedHintIndices;

    public Hint(UserData userData) {
        this.userData = userData;
        this.usedHintIndices = new HashSet<>();
    }

    public String provideHint(long chatId, GameLogic gameLogic) {
        int coins = userData.getCoins(chatId);
        if (coins < 1) {
            return "У вас недостаточно монеток для использования подсказки.";
        }

        userData.updateCoins(chatId, coins - 1);
        String word = gameLogic.getWordToGuess();
        String guessedWord = gameLogic.getGuessedWord();

        Set<Integer> missingLetters = new HashSet<>();
        for (int i = 0; i < word.length(); i++) {
            if (guessedWord.charAt(i) != word.charAt(i)) {
                missingLetters.add(i);
            }
        }

        missingLetters.removeAll(usedHintIndices);

        if (!missingLetters.isEmpty()) {
            int index = missingLetters.iterator().next();
            usedHintIndices.add(index);
            return "Вот подсказка: В этом слове есть буква: " + word.charAt(index);
        }
        return "Все буквы уже угаданы!";
    }
}
