package com.kevo.ApacheSpark;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoSpark {
    // Map function (processes each line)
    public static Map<String, Integer> mapLine(String line) {
        Map<String, Integer> wordCounts = new HashMap<>();
        for (String word : line.split("\\s+")) {
            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
        }
        return wordCounts;
    }

    // Reduce function (combines partial counts)
    public static Map<String, Integer> reduceCounts(Map<String, Integer> map1, Map<String, Integer> map2) {
        Map<String, Integer> combinedCounts = new HashMap<>(map1);
        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
            combinedCounts.put(entry.getKey(), combinedCounts.getOrDefault(entry.getKey(), 0) + entry.getValue());
        }
        return combinedCounts;
}

    public static void main(String[] args) {
        String text = "This is a sample text to count words. Here are some more words to test.";
        // Simulate splitting the text into lines (replace with actual data source in a real scenario)
        List<String> lines = Arrays.asList(text.split("\n"));

        // Map phase (process each line in parallel using streams for simplicity)
        Map<String, Integer> combinedMapResults = lines.stream()
                .map(NoSpark::mapLine)
                .reduce(new HashMap<>(), NoSpark::reduceCounts);

        System.out.println("Word count results:");
        combinedMapResults.forEach((word, count) -> System.out.println(word + ": " + count));
    }

}
