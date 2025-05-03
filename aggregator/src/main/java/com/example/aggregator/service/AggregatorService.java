package com.example.aggregator.service;

import com.example.aggregator.client.AggregatorRestClient;
import com.example.aggregator.model.Entry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AggregatorService {

    private final AggregatorRestClient aggregatorRestClient;

    public AggregatorService(AggregatorRestClient aggregatorRestClient) {
        this.aggregatorRestClient = aggregatorRestClient;
    }

    public List <Entry> getAllPalindromes() {
        final List<Entry> candidates = new ArrayList<>();
            // Iterate from a to z
        IntStream.range('a', '{')
            .mapToObj(i -> Character.toString(i))
            .forEach(c -> {

            // get words starting and ending with character
            List<Entry> startsWith = aggregatorRestClient.getWordsStartingWith(c);
            List<Entry> endsWith = aggregatorRestClient.getWordsEndingWith(c);

            // keep entries that exist in both lists
            List<Entry> startsAndEndsWith = new ArrayList<>(startsWith);
            startsAndEndsWith.retainAll(endsWith);

            
            // store list with existing entries
            candidates.addAll(startsAndEndsWith);

        });

        // test each entry for palindrome, sort and return
        return candidates.stream()
          .filter(entry -> {
              String word = entry.getWord();
              String reverse = new StringBuilder(word).reverse().toString();

              return word.equals(reverse);
          })
            .sorted()
            .collect(Collectors.toList());
    }
    public Entry getDefinitionFor(String word) {
        return aggregatorRestClient.getDefinitionFor(word);
    }

    public List <Entry> getWordsStartingWith(String chars) {
        return aggregatorRestClient.getWordsStartingWith(chars);
    }

    public List<Entry> getWordsEndingWith(String chars) {
        return aggregatorRestClient.getWordsEndingWith(chars);
    }

    public List<Entry> getWordsThatContainSuccessiveLettersAndStartsWith(String chars) {

        List<Entry> wordsThatStartWith = aggregatorRestClient.getWordsStartingWith(chars);
        List<Entry> wordsThatContainSuccessiveLetters = aggregatorRestClient.getWordsThatContainConsecutiveLetters();

        // stream API version
        List<Entry> common = wordsThatStartWith.stream()
                .filter(wordsThatContainSuccessiveLetters::contains)
                .toList();

        /*List<Entry> common = new ArrayList<>(wordsThatStartWith);
        common.retainAll(wordsThatContainSuccessiveLetters);*/

        return common;

    }

    public List<Entry> getWordsThatContain(String chars) {

        return aggregatorRestClient.getWordsThatContain(chars);

    }

    public List<Entry> getWordsThatContainSpecificConsecutiveLetters(String chars) {

        return aggregatorRestClient.getWordsThatContainSpecificConsecutiveLetters(chars);

    }

}
