package pavdvf;

import java.util.Scanner;

public class UserDialog {
    private Scanner scanner;

    public UserDialog(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

}
