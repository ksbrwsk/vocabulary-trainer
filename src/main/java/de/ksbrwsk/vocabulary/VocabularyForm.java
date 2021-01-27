package de.ksbrwsk.vocabulary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyForm {
    private Long id;
    private String source;
    private String target;
    private String input = "";
    private boolean next = false;
}
