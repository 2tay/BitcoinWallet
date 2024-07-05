package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bip39Wordlist {
    private static final String[] WORD_LIST;

    static {
        List<String> wordList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("bip-39.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                wordList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        WORD_LIST = wordList.toArray(new String[0]);
    }

    public static String[] getWord_list() {
        return WORD_LIST;
    }

    public static void main(String[] args) {
        // Example usage: print the word list
        for (String word : WORD_LIST) {
            System.out.println(word);
        }
    }
}
