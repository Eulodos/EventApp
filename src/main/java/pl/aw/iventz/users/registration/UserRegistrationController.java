package pl.aw.iventz.users.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserRegistrationController {

    private UserRegistrationService userRegistrationService;

    @Autowired
    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping(value = "/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userRegistrationDTO", new UserRegistrationDTO());
        return "user/registerForm";
    }

    @PostMapping(value = "/register")
    public String handleUserRegisterRequest(@ModelAttribute @Valid UserRegistrationDTO userRegistrationDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "user/registerForm";
        }
        try {
            userRegistrationService.registerNewUser(userRegistrationDTO);
        } catch (UserAlreadyExistsException e) {
            bindingResult.rejectValue("email","user.exists","Użytkownik o emailu " + userRegistrationDTO.getEmail() + " już istnieje.");
            return "user/registerForm";
        }
        redirectAttributes.addFlashAttribute("regSuccess", "Dziękujemy za rejestrację " + userRegistrationDTO.getEmail());
        return "redirect:/";
    }
}
