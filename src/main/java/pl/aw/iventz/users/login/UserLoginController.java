package pl.aw.iventz.users.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserLoginController {

    private UserLoginService userLoginService;

    @Autowired
    public UserLoginController(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    @GetMapping(value = "/login")
    public String showLoginForm() {
        userLoginService.getUserLoggedIn();
        return "user/login";
    }
}
