package pl.aw.iventz.users.super_secret;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.aw.iventz.users.registration.*;

import java.util.HashSet;
import java.util.Set;


@Service
public class SuperSecretService {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private UserRegistrationDTOToUserConverter userRegistrationDTOToUserConverter;

    @Autowired
    public SuperSecretService(UserRepository userRepository, UserRoleRepository userRoleRepository, UserRegistrationDTOToUserConverter userRegistrationDTOToUserConverter) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userRegistrationDTOToUserConverter = userRegistrationDTOToUserConverter;
    }

    public User addAdmin(UserRegistrationDTO userRegistrationDTO) {
        User user = userRegistrationDTOToUserConverter.convertToUser(userRegistrationDTO);
        superSecretlySetAdminRole(user);
        userRepository.save(user);
        return user;
    }

    private void superSecretlySetAdminRole(User user) {
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(userRoleRepository.findUserRoleByRoleName("ROLE_ADMIN")
                .orElseGet(() -> userRoleRepository.save(new UserRole("ROLE_ADMIN"))));
        user.setUserRoles(userRoles);
    }
}
