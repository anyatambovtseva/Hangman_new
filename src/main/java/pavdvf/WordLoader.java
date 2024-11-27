package pavdvf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WordLoader
{
    private List<String> words;

    public WordLoader()
    {
        words = new ArrayList<>();
    }

    public void loadWords(String filePath) throws IOException
    {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)))
        {

            String line;
            while ((line = reader.readLine()) != null)
            {
                words.add(line.trim());
            }
        }
    }

    public List<String> getWords()
    {
        return words;
    }
}


