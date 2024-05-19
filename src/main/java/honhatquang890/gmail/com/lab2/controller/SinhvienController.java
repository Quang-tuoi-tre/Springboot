package honhatquang890.gmail.com.lab2.controller;


import honhatquang890.gmail.com.lab2.model.Sinhvien;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
@Controller
public class SinhvienController {
    @GetMapping("/sinhvien")
    public String showForm(Model model) {
        model.addAttribute("sinhVien", new Sinhvien());
        return "form-sinhvien";
    }
    @PostMapping("/sinhvien")
    public String submitForm(@Valid Sinhvien sinhVien, BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            return "form-sinhvien";
        }
        model.addAttribute("message", "Sinh viên đã được thêm thành công!");
        return "result-sinhvien";
    }
}
