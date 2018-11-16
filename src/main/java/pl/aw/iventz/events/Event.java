package pl.aw.iventz.events;

import lombok.Getter;
import lombok.Setter;
import pl.aw.iventz.comments.Comment;
import pl.aw.iventz.users.registration.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 4001)
    private String description;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date endDate;

    @ManyToOne
    private User creator;

    @OneToMany(mappedBy = "event")
    private List<Comment> commentList;
}
