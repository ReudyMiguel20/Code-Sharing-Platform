package platform.mvc_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import platform.entity.CodeExamples;
import platform.service.CodeExampleServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Controller
public class WebMVController {

    private CodeExampleServiceImpl codeExampleService;

    @Autowired
    public WebMVController(CodeExampleServiceImpl codeExampleService) {
        this.codeExampleService = codeExampleService;
    }

    @GetMapping("/code/new")
    public String postNewCode() {
        return "post_new_code";
    }

    @GetMapping("/code/{id}")
    public String getSpecificCode(Model model, @PathVariable UUID id) {
        CodeExamples tempCodeExample = this.codeExampleService.returnCodeById(id);

        /*If the code cannot expire it doesn't change its values because it has no restriction and return it with the desired template
        * however, if the code is able to expire then it checks the ENUM type of the CodeExample object and then shows a template
        * based on the restriction type
        * */
        if (tempCodeExample.canNeverExpire()) {
            model.addAttribute("code", tempCodeExample);
            return "specific_code_no_restriction";
        } else {
            this.codeExampleService.actionBasedOnEnumType(tempCodeExample);
            model.addAttribute("code", tempCodeExample);

            switch (tempCodeExample.getRestrictionType()) {
                case TIME -> {
                    return "specific_code_time_restriction";
                }
                case VIEWS -> {
                    return "specific_code_views_restriction";
                }
                case BOTH -> {
                    return "specific_code";
                }
            }

        }
        return null;
    }

    // Shows the ten latest's code posted that contains no restriction
    @GetMapping("/code/latest")
    public String getLatestCodes(Model model) {
        List<CodeExamples> latestCodes = this.codeExampleService.getLatestCodes();
        model.addAttribute("latestcodes", latestCodes);
        return "latests_codes";
    }
}
