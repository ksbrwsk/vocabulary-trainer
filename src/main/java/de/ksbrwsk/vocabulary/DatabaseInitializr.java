package de.ksbrwsk.vocabulary;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@Log4j2
@RequiredArgsConstructor
public class DatabaseInitializr {

    private final VocabularyRepository vocabularyRepository;
    private final ApplicationProperties applicationProperties;

    @Scheduled(fixedRateString = "${application.dataRefreshInterval}")
    @EventListener(ApplicationReadyEvent.class)
    public void process() throws IOException {
        this.processData();
    }

    private void processData() throws IOException {
        String dataFileUrl = applicationProperties.getDataFileUrl();
        log.info("Vokabeln einlesen und Datenbank initialisieren. Quelle: {}", dataFileUrl);
        Resource resource = new UrlResource(dataFileUrl);
        String str =  StreamUtils.copyToString(resource.getInputStream(),StandardCharsets.ISO_8859_1);
        String[] tmp = str.split("\n");
        List<String> strings = List.of(tmp);
        List<VocabularyTupel> vcs = strings
                .stream()
                .skip(1) // headers
                .map(this::splitVcTuple)
                .map(this::createTupel)
                .collect(toList());

        this.vocabularyRepository.reset();
        for (VocabularyTupel tuple : vcs) {
            this.vocabularyRepository.addTupel(tuple);
        }
        log.info("Datenbank erfolgreich initialisiert.");
    }

    private VocabularyTupel createTupel(String[] vTuple) {
        log.info("Erzeuge Tupel -> {}", vTuple);
        return new VocabularyTupel(Long.parseLong(vTuple[0]), vTuple[1], vTuple[2]);
    }

    private String[] splitVcTuple(String s) {
        log.info("Datenzeile splitten -> {}", s);
        s = s.replaceAll("(\r\n|\r)", "");
        return s.split(";");
    }
}
