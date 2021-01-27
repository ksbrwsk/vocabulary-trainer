package de.ksbrwsk.vocabulary;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@Log4j2
public class VocabularyRepository {

    private static final ConcurrentMap<Long, VocabularyTupel> VOCABULARY = new ConcurrentHashMap<>();

    public void addTupel(VocabularyTupel vocabularyTupel) {
        log.info("füg Tupel hinzu {}", vocabularyTupel.getId());
        VOCABULARY.put(vocabularyTupel.getId(), vocabularyTupel);
    }

    public VocabularyTupel getTupel(Long id) {
        log.info("hole Tupel mit ID {}", id);
        return VOCABULARY.get(id);
    }

    public Collection<VocabularyTupel> getAllTupels() {
        log.info("hole alle Tupel");
        return VOCABULARY.values();
    }

    public int count() {
        return VOCABULARY.size();
    }

    public VocabularyTupel random() {
        int count = this.count();
        Random random = new Random();
        long i = random.nextInt(count);
        VocabularyTupel tupel = this.getTupel(i);
        while (tupel == null) {
            i = random.nextInt(count);
            tupel = this.getTupel(i);
        }
        return tupel;
    }
}
