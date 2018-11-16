package pl.aw.iventz.users.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    private UserRepository userRepository;
    private UserRegistrationDTOToUserConverter userRegistrationDTOToUserConverter;
    private UserRoleSetter userRoleSetter;

    @Autowired
    public UserRegistrationService(UserRepository userRepository, UserRegistrationDTOToUserConverter userRegistrationDTOToUserConverter, UserRoleSetter userRoleSetter) {
        this.userRepository = userRepository;
        this.userRegistrationDTOToUserConverter = userRegistrationDTOToUserConverter;
        this.userRoleSetter = userRoleSetter;
    }

    public void registerNewUser(UserRegistrationDTO userRegistrationDTO) throws UserAlreadyExistsException {
        userRepository.findUserByEmail(userRegistrationDTO.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("Użytkownik o emailu " + userRegistrationDTO.getEmail() + " już istnieje.");
                });
        User user = userRegistrationDTOToUserConverter.convertToUser(userRegistrationDTO);
        userRoleSetter.setDefaultRoleForUser(user);
        userRepository.save(user);
    }
}
