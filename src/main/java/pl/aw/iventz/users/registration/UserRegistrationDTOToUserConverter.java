package pl.aw.iventz.users.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationDTOToUserConverter {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserRegistrationDTOToUserConverter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User convertToUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();

        user.setEmail(userRegistrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setDisplayName(userRegistrationDTO.getDisplayName());
        return user;
    }
}
