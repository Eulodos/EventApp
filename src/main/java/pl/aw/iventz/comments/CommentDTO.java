package pl.aw.iventz.comments;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.aw.iventz.events.Event;
import pl.aw.iventz.users.registration.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class CommentDTO {

    @NotBlank(message = "Komentarz nie może być pusty.")
    @Size(max = 500, message = "Komentarz może zawierać maksymalnie 500 znaków.")
    private String content;

    private User commentPoster;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date creationDate;

    private Event event;
}
