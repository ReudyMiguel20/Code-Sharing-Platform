package platform.service;

import javassist.NotFoundException;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import platform.entity.CodeExamples;
import platform.exception.CodeNotFound;
import platform.repositories.CodeExamplesRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CodeExampleServiceImpl implements CodeExampleService {
    private CodeExamplesRepository codeExamplesRepository;

    @Autowired
    public CodeExampleServiceImpl(CodeExamplesRepository codeExamplesRepository) {
        this.codeExamplesRepository = codeExamplesRepository;
    }

    @Override
    public List<CodeExamples> getAllCodes() {
        return this.codeExamplesRepository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        this.codeExamplesRepository.deleteById(id);
    }

    @Override
    public void deleteCode(CodeExamples codeExample) {
        this.codeExamplesRepository.delete(codeExample);
    }

    @Override
    public void updateCodeExample(CodeExamples codeToUpdate) {
        this.codeExamplesRepository.save(codeToUpdate);
    }

    @Override
    public void addCodeExample(CodeExamples codeToSave) {
        this.codeExamplesRepository.save(codeToSave);
    }

    @Override
    public CodeExamples returnCodeById(UUID id) {
        return this.codeExamplesRepository.findById(id)
                .orElseThrow(CodeNotFound::new);
    }

    //Get the latest ten uploaded codes
    @Override
    public List<CodeExamples> getLatestCodes() {
//        return this.codeStorage.getCodeExamplesList()
//                .stream()
//                .sorted(Comparator.naturalOrder())
//                .limit(10)
//                .toList();

        List<CodeExamples> latestCodes = getAllCodes();
        List<CodeExamples> tenLatestCodes = new ArrayList<>();

        int counter = 0;
        int latestCodesSize = latestCodes.size();


        for (int i = 1; latestCodesSize >= i; i++) {
            //If the list contain 10 items proceed to break and return the list
            if (counter == 10) {
                break;
            }

            //If the code has 0 views and time then it gets added to the list of latest codes
            if (latestCodes.get(latestCodesSize - i).getSecondsToExpire() == 0 && latestCodes.get(latestCodesSize - i).getViews() == 0) {
                tenLatestCodes.add(latestCodes.get(latestCodesSize - i));
                counter++;
            }

        }

        return tenLatestCodes;
    }

    public void actionBasedOnEnumType(CodeExamples codeExample) {
        if (codeExample.getRestrictionType().equals(CodeExamples.RestrictionType.TIME)) {
            updateSecondsToExpire(codeExample);
        } else if (codeExample.getRestrictionType().equals(CodeExamples.RestrictionType.VIEWS)) {
            reduceViewsFromCode(codeExample);
        } else {
            updateTimeAndReduceViews(codeExample);
        }
    }

    @Override
    public void updateTimeAndReduceViews(CodeExamples codeExamples) {
        updateSecondsToExpire(codeExamples);
        reduceViewsFromCode(codeExamples);
    }

    @Override
    public void updateSecondsToExpire(CodeExamples codeExample) {
        LocalDateTime timeNow = LocalDateTime.now();
        Duration duration = Duration.between(timeNow, codeExample.getExpireDate());
        int secondsLeft = (int) duration.getSeconds();

        codeExample.setSecondsToExpire(secondsLeft);

        updateCodeExample(codeExample);
    }

    @Override
    public void reduceViewsFromCode(CodeExamples codeExample) {
        codeExample.setViews(codeExample.getViews() - 1);
        updateCodeExample(codeExample);
    }

    //Check if the code expired, based on the attribute of the CodeExamples object 'canNeverExpire' then based on the values 'time' and/or 'views'
    @Override
    public boolean didCodeExpire(CodeExamples codeExample) {
        if (codeExample.canNeverExpire()) {
            return false;
        } else if (!codeExample.canNeverExpire()) {
            return !stillHasSecondsLeft(codeExample) && !stillHasViewsLeft(codeExample) && (!stillHasSecondsLeft(codeExample) || !stillHasViewsLeft(codeExample));
        } else {
            return true;
        }
    }

    @Override
    public boolean stillHasViewsLeft(CodeExamples codeExample) {
        return codeExample.getViews() > 0;
    }

    @Override
    public boolean stillHasSecondsLeft(CodeExamples codeExample) {
        LocalDateTime timeNow = LocalDateTime.now();
        Duration duration = Duration.between(timeNow, codeExample.getExpireDate());

        return duration.getSeconds() >= 0;
    }

}
