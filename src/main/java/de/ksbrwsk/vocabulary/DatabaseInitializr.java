package de.ksbrwsk.vocabulary;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@Log4j2
@RequiredArgsConstructor
public class DatabaseInitializr {

    private final VocabularyRepository vocabularyRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void process() throws IOException {
        boolean initialized = this.isDatabaseInitialized();
        if (!initialized) {
            this.processData();
        } else {
            log.info("Vokabeln bereits eingelesen, Initialisierung nicht notwendig.");
        }
    }

    private void processData() throws IOException {
        log.info("Vokabeln einlesen und Datenbank initialisieren...");
        Resource resource = new ClassPathResource("/data.csv");
        List<String> strings = FileUtils.readLines(resource.getFile(), StandardCharsets.UTF_8);
        List<VocabularyTupel> vcs = strings
                .stream()
                .skip(1) // headers
                .map(this::splitVcTuple)
                .map(this::createTupel)
                .collect(toList());

        for (VocabularyTupel tuple : vcs) {
            this.vocabularyRepository.addTupel(tuple);
        }
        log.info("Datenbank erfolgreich initialisiert.");
    }

    private boolean isDatabaseInitialized() {
        log.info("Prüfen ob die Datenbank initialisiert ist...");
        int count = this.vocabularyRepository.count();
        return count > 0;
    }

    private VocabularyTupel createTupel(String[] vTuple) {
        log.info("Erzeuge Tupel -> {}", vTuple);
        return new VocabularyTupel(Long.parseLong(vTuple[0]), vTuple[1], vTuple[2]);
    }

    private String[] splitVcTuple(String s) {
        log.info("Datenzeile splitten -> {}", s);
        return s.split(";");
    }
}
