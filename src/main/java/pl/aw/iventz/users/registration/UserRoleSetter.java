package pl.aw.iventz.users.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserRoleSetter {

    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleSetter(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public void setDefaultRoleForUser(User user) {
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(userRoleRepository.findUserRoleByRoleName(UserRole.ROLE_USER)
                .orElseGet(() -> userRoleRepository.save(new UserRole(UserRole.ROLE_USER))));
        user.setUserRoles(userRoles);
    }
}
