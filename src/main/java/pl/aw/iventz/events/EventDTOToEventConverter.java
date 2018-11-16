package pl.aw.iventz.events;

import org.springframework.stereotype.Service;
import pl.aw.iventz.users.login.UserLoginService;
import pl.aw.iventz.users.registration.User;
import pl.aw.iventz.users.registration.UserRepository;

@Service
public class EventDTOToEventConverter {

    private UserLoginService userLoginService;
    private UserRepository userRepository;

    public EventDTOToEventConverter(UserLoginService userLoginService, UserRepository userRepository) {
        this.userLoginService = userLoginService;
        this.userRepository = userRepository;
    }

    public Event convertEventDTOToEvent(EventDTO eventDTO) {
        Event event = new Event();

        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setStartDate(eventDTO.getStartDate());
        event.setEndDate(eventDTO.getEndDate());
        event.setCreator(findUserLoggedIn());
        return event;
    }

    private User findUserLoggedIn() {
        String userLoggedIn = userLoginService.getUserLoggedIn();
        return userRepository.findUserByEmail(userLoggedIn).get();
    }
}
