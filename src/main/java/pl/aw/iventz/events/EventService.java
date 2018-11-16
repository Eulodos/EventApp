package pl.aw.iventz.events;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class EventService {

    private EventRepository eventRepository;
    private EventDTOToEventConverter eventDTOToEventConverter;

    @Autowired
    public EventService(EventRepository eventRepository, EventDTOToEventConverter eventDTOToEventConverter) {
        this.eventRepository = eventRepository;
        this.eventDTOToEventConverter = eventDTOToEventConverter;
    }

    public void createNewEvent(EventDTO eventDTO) {
        Event event = eventDTOToEventConverter.convertEventDTOToEvent(eventDTO);
        eventRepository.save(event);
    }

    public List<Event> findComingEvents() {
        List<Event> allByStartDateAfter = eventRepository.findAllByStartDateAfter(new Date());
        sortEventsListByStartDate(allByStartDateAfter);
        return allByStartDateAfter;
    }

    public Event findConcreteEventById(Long id) throws NoSuchEventException {
        return eventRepository.findById(id).orElseGet(() -> {
            throw new NoSuchEventException("Event o id " + id + " nie istnieje.");});
    }

    public List<Event> findEventsBySpecificQuery(String query, String eventTime) {
        if (StringUtils.isBlank(query)) {
            List<Event> eventsOnlyByType = findEventsOnlyByType(eventTime);
            sortEventsListByStartDate(eventsOnlyByType);
            return eventsOnlyByType;
        }
        List<Event> eventsByTypeAndQuery = findEventsByTypeAndQuery(query, eventTime);
        sortEventsListByStartDate(eventsByTypeAndQuery);
        return eventsByTypeAndQuery;
    }

    private List<Event> findEventsByTypeAndQuery(String query, String eventTime) {
        switch (eventTime) {
            case "currentAndComing":
                return eventRepository.findAllByTitleLikeAndEndDateAfter(query, new Date());
            case "all":
                return eventRepository.findAllByTitleLike(query);

            case "coming":
            default:
                return eventRepository.findAllByTitleLikeAndStartDateAfter(query, new Date());
        }
    }

    private List<Event> findEventsOnlyByType(String eventTime) {
        switch (eventTime) {
            case "currentAndComing":
                return eventRepository.findAllByEndDateAfter(new Date());
            case "all":
                return eventRepository.findAll();
            case "coming":
            default:
                return eventRepository.findAllByStartDateAfter(new Date());
        }
    }

    private void sortEventsListByStartDate(List<Event> eventList) {
        eventList.sort(Comparator.comparing(Event::getStartDate));
    }
}
