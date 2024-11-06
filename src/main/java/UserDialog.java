import java.util.Scanner;

public class UserDialog
{
    private Scanner scanner = new Scanner(System.in);

    public String getInput(String prompt)
    {
        System.out.print(prompt);
        return scanner.nextLine().toLowerCase();
    }

    public void close()
    {
        scanner.close();
    }
}