package pl.aw.iventz.users.registration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.aw.iventz.infrastracture.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserRole extends BaseEntity {

    public static final String ROLE_USER = "ROLE_USER";

    @Column(unique = true)
    private String roleName;

    public UserRole(String roleName) {
        this.roleName = roleName;
    }
}
