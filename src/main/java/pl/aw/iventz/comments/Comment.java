package pl.aw.iventz.comments;

import lombok.Getter;
import lombok.Setter;
import pl.aw.iventz.events.Event;
import pl.aw.iventz.users.registration.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date commentCreationDate;

    @Column(length = 500)
    private String content;

    @ManyToOne
    private User commentPoster;

    @ManyToOne
    @JoinColumn(name = "EVENT")
    private Event event;
}
