package pl.aw.iventz.user_enrolled_event;

import lombok.Getter;
import lombok.Setter;
import pl.aw.iventz.events.Event;
import pl.aw.iventz.users.registration.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class UserEnrolledEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @OneToOne
    private Event event;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date enrollDate;
}
