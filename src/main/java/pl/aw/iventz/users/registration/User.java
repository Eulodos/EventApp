package pl.aw.iventz.users.registration;

import lombok.Getter;
import lombok.Setter;
import pl.aw.iventz.comments.Comment;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 100)
    private String email;

    @Column(length = 60)
    private String password;
    @Column(length = 50)
    private String displayName;

    @ManyToMany
    @JoinTable(name = "USERS_WITH_ROLES")
    private Set<UserRole> userRoles;

}
