package pl.aw.iventz.events.rest;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ApiEventListDTO {

    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;

}
