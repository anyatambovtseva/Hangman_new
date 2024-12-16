package pavdvf;

public class Hint {
    private final UserData userData;

    public Hint(UserData userData) {
        this.userData = userData;
    }

    public String provideHint(long chatId, GameLogic gameLogic) {
        int coins = userData.getCoins(chatId);
        if (coins < 1) {
            return "У вас недостаточно монеток для использования подсказки.";
        }

        userData.updateCoins(chatId, coins - 1);
        String word = gameLogic.getWordToGuess();
        String guessedWord = gameLogic.getGuessedWord();

        for (int i = 0; i < word.length(); i++) {
            if (guessedWord.charAt(i) != word.charAt(i)) {
                return "Вот подсказка: В этом слове есть буква: " + word.charAt(i);
            }
        }
        return "Все буквы уже угаданы!";
    }
}

