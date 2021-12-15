package de.ksbrwsk.vocabulary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@Slf4j
public class VocabularyRepository {

    private static final ConcurrentMap<Long, VocabularyTupel> VOCABULARY = new ConcurrentHashMap<>();

    private final Random random = new Random();

    public void addTupel(VocabularyTupel vocabularyTupel) {
        VOCABULARY.put(vocabularyTupel.getId(), vocabularyTupel);
    }

    public VocabularyTupel getTupel(Long id) {
        log.info("hole Tupel mit ID {}", id);
        return VOCABULARY.get(id);
    }

    public int count() {
        return VOCABULARY.size();
    }

    public VocabularyTupel random() {
        int count = this.count();
        if (count < 0) {
            count = 0;
        }
        long i = random.nextInt(count);
        VocabularyTupel tupel = this.getTupel(i);
        while (tupel == null) {
            i = random.nextInt(count);
            tupel = this.getTupel(i);
        }
        return tupel;
    }

    public void reset() {
        VOCABULARY.clear();
    }
}
