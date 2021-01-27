package de.ksbrwsk.vocabulary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyTupel {
    private Long id;
    private String source;
    private String target;
}
