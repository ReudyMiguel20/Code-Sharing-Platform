package platform.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.dto.NewCodeResponse;
import platform.dto.PostNewCode;
import platform.entity.CodeExamples;
import platform.service.CodeExampleServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ApiController {

    private CodeExampleServiceImpl codeExampleService;

    @Autowired
    public ApiController(CodeExampleServiceImpl codeExampleService) {
        this.codeExampleService = codeExampleService;
    }

    //Return a list of Code Examples
    @GetMapping("/code/latest")
    public ResponseEntity<?> getCode() {
        List<CodeExamples> latestCodes = this.codeExampleService.getLatestCodes();

        if (latestCodes.size() == 0) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(this.codeExampleService.getLatestCodes());
        }
    }

    //Return a specific CodeExample object by id
    @GetMapping("/code/{id}")
    public ResponseEntity<?> getSpecificCode(@PathVariable UUID id) {
        CodeExamples tempCodeExample = this.codeExampleService.returnCodeById(id);
        boolean isCodeExpired = this.codeExampleService.didCodeExpire(tempCodeExample);

        //Comment here
        if (tempCodeExample == null) {
            return ResponseEntity.notFound().build();
        } else if (isCodeExpired) {
            this.codeExampleService.deleteCode(tempCodeExample);
            return ResponseEntity.notFound().build();
        } else {
            if (!tempCodeExample.canNeverExpire()) {
                this.codeExampleService.actionBasedOnEnumType(tempCodeExample);
            }
            return ResponseEntity.ok().body(tempCodeExample);
        }
    }

    //Post a new Code Example
    @PostMapping("/code/new")
    public ResponseEntity<?> postNewCode(@RequestBody PostNewCode newCode) {
        CodeExamples tempCodeExample = null;

        if (newCode.getSecondsToExpire() == 0 && newCode.getViews() == 0) {
            tempCodeExample = new CodeExamples(newCode.getCode());
            this.codeExampleService.addCodeExample(tempCodeExample);
        } else {
            tempCodeExample = new CodeExamples(newCode.getCode(), newCode.getSecondsToExpire(), newCode.getViews());
            this.codeExampleService.addCodeExample(tempCodeExample);
        }

        return ResponseEntity.ok().body(new NewCodeResponse(tempCodeExample.getId().toString()));
    }

//    @GetMapping("/code/latest")
//    public ResponseEntity<?> returnLatestCodes() {
//        List<CodeExamples> latestCodes = this.codeExampleService.getLatestCodes();
//
//        return ResponseEntity.ok().body(latestCodes);
//    }
}
