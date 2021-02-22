package de.ksbrwsk.vocabulary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VocabularyTrainerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VocabularyTrainerApplication.class, args);
    }

}
