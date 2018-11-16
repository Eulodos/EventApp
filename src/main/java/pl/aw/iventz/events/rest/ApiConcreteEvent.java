package pl.aw.iventz.events.rest;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ApiConcreteEvent {

    private Long id;
    private String title;
    private Date startDate;
    private Date endDate;
    private String description;
}
