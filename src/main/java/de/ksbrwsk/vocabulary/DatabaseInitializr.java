package de.ksbrwsk.vocabulary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class DatabaseInitializr {

    private final VocabularyRepository vocabularyRepository;
    private final ApplicationProperties applicationProperties;

    @Scheduled(fixedRateString = "${application.dataRefreshInterval}")
    public void process() throws IOException {
        this.processData();
    }

    private void processData() throws IOException {
        String dataFileUrl = applicationProperties.getDataFileUrl();
        log.info("Vokabeln einlesen und Datenbank initialisieren. Quelle: {}", dataFileUrl);
        Resource resource = new UrlResource(dataFileUrl);
        String str = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.ISO_8859_1);
        String[] tmp = str.split("\n");
        List<String> strings = List.of(tmp);
        List<VocabularyTupel> vcs = strings
                .stream()
                .skip(1) // headers
                .map(this::splitVcTuple)
                .map(this::createTupel).toList();
        this.vocabularyRepository.reset();
        for (VocabularyTupel tuple : vcs) {
            this.vocabularyRepository.addTupel(tuple);
        }
        log.info("Datenbank erfolgreich initialisiert.");
    }

    private VocabularyTupel createTupel(String[] vTuple) {
        return new VocabularyTupel(Long.parseLong(vTuple[0]), vTuple[1], vTuple[2]);
    }

    private String[] splitVcTuple(String s) {
        s = s.replaceAll("(\r\n|\r)", "");
        return s.split(";");
    }
}
