package com.example.aggregator.controller;

import com.example.aggregator.model.Entry;
import com.example.aggregator.service.AggregatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AggregatorController {

    private static final Logger logger = LoggerFactory.getLogger(AggregatorController.class.getName());
    private final AggregatorService aggregatorService;

    public AggregatorController(AggregatorService aggregatorService) {
        this.aggregatorService = aggregatorService;
    }

    @GetMapping("/")
    public List<Entry> helloWorld() {

        List<Entry> entries = List.of(
                aggregatorService.getDefinitionFor("hello"),
                aggregatorService.getDefinitionFor("world")
        );
        return entries;
    }

    @GetMapping("getDefinitionFor/{word}")
    public Entry getDefinitionFor(@PathVariable String word) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Entry entry = aggregatorService.getDefinitionFor(word);
        stopWatch.stop();

        long nanoSeconds = stopWatch.getLastTaskTimeNanos();
        String message = new StringBuilder()
                .append("Retrieved entry for [")
                .append(word)
                .append("] in ")
                .append(nanoSeconds / 1000000.0)
                .append("ms")
                .toString();
        logger.info(message);

        return entry;
    }

    @GetMapping("getWordsStartingWith/{chars}")
    public List<Entry> getWordsStartingWith(@PathVariable String chars) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Entry> entry = aggregatorService.getWordsStartingWith(chars);
        stopWatch.stop();

        long nanoSeconds = stopWatch.getLastTaskTimeNanos();
        String message = new StringBuilder()
                .append("Retrieved ")
                .append(entry.size())
                .append(" entries for words starting with [")
                .append(chars)
                .append("] in ")
                .append(nanoSeconds / 1000000.0)
                .append("ms")
                .toString();
        logger.info(message);

        return entry;
    }

    @GetMapping("getWordsEndingWith/{chars}")
    public List<Entry> getWordsEndingWith(@PathVariable String chars) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Entry> entry = aggregatorService.getWordsEndingWith(chars);
        stopWatch.stop();

        long nanoSeconds = stopWatch.getLastTaskTimeNanos();
        String message = new StringBuilder()
                .append("Retrieved ")
                .append(entry.size())
                .append(" entries for words ending with [")
                .append(chars)
                .append("] in ")
                .append(nanoSeconds / 1000000.0)
                .append("ms")
                .toString();
        logger.info(message);

        return entry;
    }

    @GetMapping("getWordsThatContainSuccessiveLettersAndStartsWith/{chars}")
    public List<Entry> getWordsThatContainSuccessiveLettersAndStartsWith(@PathVariable String chars) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Entry> entry = aggregatorService.getWordsThatContainSuccessiveLettersAndStartsWith(chars);
        stopWatch.stop();

        long nanoSeconds = stopWatch.getLastTaskTimeNanos();
        String message = new StringBuilder()
                .append("Retrieved ")
                .append(entry.size())
                .append(" entries for words with successive letters that contain [")
                .append(chars)
                .append("] in ")
                .append(nanoSeconds / 1000000.0)
                .append("ms")
                .toString();
        logger.info(message);

        return entry;
    }

    @GetMapping("getWordsThatContain/{chars}")
    public List<Entry> getWordsThatContain(@PathVariable String chars) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Entry> entry = aggregatorService.getWordsThatContain(chars);
        stopWatch.stop();

        long nanoSeconds = stopWatch.getLastTaskTimeNanos();
        String message = new StringBuilder()
                .append("Retrieved entries for words that contain [")
                .append(chars)
                .append("] in ")
                .append(nanoSeconds / 1000000.0)
                .append("ms")
                .toString();
        logger.info(message);

        return entry;
    }
}
