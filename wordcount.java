import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WordCount {
    public static void main(String[] args) {
        String filePath = "C:/Users/re/Downloads/DontRunItWillOverloadTxt/10000sbd.txt"; // Specify the path to your text file

        long startTime = System.currentTimeMillis(); // Start time

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Map<String, Integer> wordCountMap = new HashMap<>();

            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");

                for (String word : words) {
                    // Remove punctuation and convert to lowercase for better counting accuracy
                    word = word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

                    if (!word.isEmpty()) {
                        // Update the count for the word in the map
                        int count = wordCountMap.getOrDefault(word, 0);
                        wordCountMap.put(word, count + 1);
                    }
                }
            }

            // Print the word count results
            for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
                System.out.println(entry.getKey() + "\t" + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis(); // End time
        long runtime = endTime - startTime; // Calculate runtime

        System.out.println("Runtime: " + runtime + " milliseconds");
    }
}
