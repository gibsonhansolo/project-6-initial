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

    public boolean validPalindrome(String word) {
        int len  = word.length();
        if(word.charAt(0) != word.charAt(len-1)) {
            return false;
        };

        for(int i = 0; i < len/2; i++) {
            if(word.charAt(i) != word.charAt(len-i-1)) {
                return false;
            }
        }
        return true;
    }

    public List <Entry> getAllPalindromes() {
        final List<Entry> words = new ArrayList<>();
        List<Entry> invalidWords = new ArrayList<>();
        //iterate through all letters
        for(char letter  = 'a';  letter <= 'z'; letter++){
            String s = String.valueOf(letter);
            List<Entry> entries = aggregatorRestClient.getWordsStartingWith(s);

            //iterate through words
            for(Entry entry : entries) {
                System.out.println(entry);
                String word = String.valueOf(entry);

                if(words.contains(word)) {
                    continue;
                }
                else if(invalidWords.contains(word)) {
                    continue;
                }
                else if(!validPalindrome(String.valueOf(word))) {
                    invalidWords.add(entry);
                    continue;
                }
                else {
                    words.add(entry);
                }
            }

        }

        return words;
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
