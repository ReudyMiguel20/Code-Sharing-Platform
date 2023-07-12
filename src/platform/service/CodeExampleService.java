package platform.service;

import platform.entity.CodeExamples;

import java.util.List;
import java.util.UUID;

public interface CodeExampleService {
    CodeExamples returnCodeById(UUID id);
    List<CodeExamples> getLatestCodes();
    void deleteById(UUID id);
    void deleteCode(CodeExamples codeExample);
    void updateCodeExample(CodeExamples codeToUpdate);
    void addCodeExample(CodeExamples codeToSave);
    List<CodeExamples> getAllCodes();
    boolean didCodeExpire(CodeExamples codeExample);
    void updateTimeAndReduceViews(CodeExamples codeExamples);
    void updateSecondsToExpire(CodeExamples codeExample);
    boolean stillHasSecondsLeft(CodeExamples codeExample);
    void reduceViewsFromCode(CodeExamples codeExample);
    boolean stillHasViewsLeft(CodeExamples codeExample);
}
