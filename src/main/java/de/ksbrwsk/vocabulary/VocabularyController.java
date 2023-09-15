package de.ksbrwsk.vocabulary;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class VocabularyController {

    private static final String PAGE_INDEX = "index";
    private static final String PAGE_RESULT = "result";
    private static final String PAGE_NEXT = "next";
    private final static String SUCCESS_MESSAGE = "successMessage";
    private final static String ERROR_MESSAGE = "errorMessage";

    private final ApplicationProperties applicationProperties;
    private final VocabularyRepository vocabularyRepository;

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        addCommonModelAttributes(model);
        return PAGE_INDEX;
    }

    @GetMapping(value = {"start"})
    public String start(Model model) {
        addCommonModelAttributes(model);
        VocabularyTupel tupel = this.vocabularyRepository.random();
        VocabularyForm form = new VocabularyForm();
        form.setId(tupel.getId());
        form.setSource(tupel.getSource());
        form.setTarget(tupel.getTarget());
        form.setInput("");
        model.addAttribute("model", form);
        return PAGE_NEXT;
    }

    @PostMapping(value = {"result"})
    public String result(Model model, @ModelAttribute("model") VocabularyForm vocabularyForm) {
        addCommonModelAttributes(model);
        String input = vocabularyForm.getInput();
        String target = vocabularyForm.getTarget();
        String success = null;
        String error = null;
        if (input.equalsIgnoreCase(target)) {
            success = "Die Antwort ist richtig!";
            vocabularyForm.setNext(true);
        } else {
            error = "Die Antwort ist falsch!";
        }
        model.addAttribute(SUCCESS_MESSAGE, success);
        model.addAttribute(ERROR_MESSAGE, error);
        model.addAttribute("model", vocabularyForm);
        return PAGE_RESULT;
    }

    private void addCommonModelAttributes(@NotNull Model model) {
        model.addAttribute("titleMessage", this.applicationProperties.getTitle());
        model.addAttribute("appInfo", this.applicationProperties.getAppInfo());
    }
}
