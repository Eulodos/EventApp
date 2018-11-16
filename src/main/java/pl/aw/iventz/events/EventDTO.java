package pl.aw.iventz.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.aw.iventz.users.registration.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class EventDTO {


    @NotBlank
    @Size(max = 100, message = "Tytuł nie może przekraczać 100 znaków.")
    private String title;

    @Size(min = 20, message = "Opis musi zawierać przynajmniej 20 znaków")
    private String description;

    @NotNull(message = "Data nie może być pusta")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date startDate;

    @NotNull(message = "Data nie może być pusta")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date endDate;

    private User user;
}
