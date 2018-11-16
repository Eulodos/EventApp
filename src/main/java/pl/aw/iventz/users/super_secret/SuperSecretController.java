package pl.aw.iventz.users.super_secret;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.aw.iventz.users.registration.User;
import pl.aw.iventz.users.registration.UserRegistrationDTO;


@RestController
@RequestMapping(value = "/superSecret")
public class SuperSecretController {

    private SuperSecretService superSecretService;

    @Autowired
    public SuperSecretController(SuperSecretService superSecretService) {
        this.superSecretService = superSecretService;
    }

    @PostMapping(value = "/nimdaDda")
    public ResponseEntity<User> addAdmin(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        User user = superSecretService.addAdmin(userRegistrationDTO);
        return ResponseEntity.status(201).body(user);
    }

}
