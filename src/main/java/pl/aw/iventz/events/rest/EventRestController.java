package pl.aw.iventz.events.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.aw.iventz.events.Event;
import pl.aw.iventz.events.EventRepository;
import pl.aw.iventz.events.EventService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class EventRestController {

    private EventRepository eventRepository;
    private EventService eventService;

    @Autowired
    public EventRestController(EventRepository eventRepository, EventService eventService) {
        this.eventRepository = eventRepository;
        this.eventService = eventService;
    }

    @GetMapping(value = "/events")
    public ResponseEntity<List<ApiEventListDTO>> getEvents() {

        return ResponseEntity.ok(findComingEvents());
    }

    @GetMapping(value = "/event/{id}")
    public ResponseEntity<ApiConcreteEvent> getConcreteEvent(@PathVariable Long id) {

        return ResponseEntity.ok(findConcreteEvent(id));
    }

    private List<ApiEventListDTO> findComingEvents() {
        return eventRepository.findAllByStartDateAfter(new Date())
                .stream()
                .map(event -> convertFromEventToApiEventListDTO(event))
                .collect(Collectors.toList());
    }

    private ApiEventListDTO convertFromEventToApiEventListDTO(Event event) {
        ApiEventListDTO apiEventListDTO = new ApiEventListDTO();

        apiEventListDTO.setId(event.getId());
        apiEventListDTO.setName(event.getTitle());
        apiEventListDTO.setStartDate(event.getStartDate());
        apiEventListDTO.setEndDate(event.getEndDate());

        return apiEventListDTO;
    }

    private ApiConcreteEvent findConcreteEvent(Long id) {
        ApiConcreteEvent apiConcreteEvent = new ApiConcreteEvent();
        Event concreteEventById = eventService.findConcreteEventById(id);

        apiConcreteEvent.setId(concreteEventById.getId());
        apiConcreteEvent.setDescription(concreteEventById.getDescription());
        apiConcreteEvent.setStartDate(concreteEventById.getStartDate());
        apiConcreteEvent.setEndDate(concreteEventById.getEndDate());
        apiConcreteEvent.setTitle(concreteEventById.getTitle());

        return apiConcreteEvent;
    }
}
