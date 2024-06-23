package honhatquang890.gmail.com.lab2.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public String error(WebRequest webRequest, Model model) {
        ErrorAttributeOptions options = ErrorAttributeOptions.defaults();
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(webRequest, options);

        model.addAttribute("timestamp", errorAttributes.get("timestamp"));
        model.addAttribute("error", errorAttributes.get("error"));
        model.addAttribute("message", errorAttributes.get("message"));
        model.addAttribute("path", errorAttributes.get("path"));
        model.addAttribute("status", errorAttributes.get("status"));

        return "error";
    }

    public String getErrorPath() {
        return "/error";
    }
}
