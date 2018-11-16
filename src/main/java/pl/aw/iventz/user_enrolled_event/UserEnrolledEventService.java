package pl.aw.iventz.user_enrolled_event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.aw.iventz.events.Event;
import pl.aw.iventz.events.EventRepository;
import pl.aw.iventz.events.NoSuchEventException;
import pl.aw.iventz.users.login.UserLoginService;
import pl.aw.iventz.users.registration.User;
import pl.aw.iventz.users.registration.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserEnrolledEventService {

    private UserEnrolledEventRepository userEnrolledEventRepository;
    private EventRepository eventRepository;
    private UserLoginService userLoginService;
    private UserRepository userRepository;

    @Autowired
    public UserEnrolledEventService(UserEnrolledEventRepository userEnrolledEventRepository, EventRepository eventRepository, UserLoginService userLoginService, UserRepository userRepository) {
        this.userEnrolledEventRepository = userEnrolledEventRepository;
        this.eventRepository = eventRepository;
        this.userLoginService = userLoginService;
        this.userRepository = userRepository;
    }

    public Boolean enrollForEvent(Long eventId) throws NoSuchEventException {
        Boolean userAlreadyEnrolled = checkIfUserAlreadyEnrolledForEvent(eventId);

        if (!userAlreadyEnrolled) {


            UserEnrolledEvent userEnrolledEvent = new UserEnrolledEvent();

            Event event = eventRepository.findById(eventId).orElseGet(() -> {
                throw new NoSuchEventException("Event o id " + eventId + " nie istnieje.");
            });


            userEnrolledEvent.setEvent(event);
            userEnrolledEvent.setEnrollDate(new Date());
            userEnrolledEvent.setUser(getUserByEmail().get());

            userEnrolledEventRepository.save(userEnrolledEvent);
        }
        return userAlreadyEnrolled;
    }

    public Boolean checkIfUserAlreadyEnrolledForEvent(Long eventId) {
        Optional<User> userByEmail = getUserByEmail();

        if (userByEmail.isPresent()) {
            return userEnrolledEventRepository.findByEventAndUser(eventId, userByEmail.get().getId()).isPresent();
        }
        return null;
    }

    public void leaveEvent(Long eventId) {
        Optional<UserEnrolledEvent> byEventAndUser = userEnrolledEventRepository.findByEventAndUser(eventId, getUserByEmail().get().getId());
        userEnrolledEventRepository.delete(byEventAndUser.get());
    }

    private Optional<User> getUserByEmail() {
        return userRepository.findUserByEmail(userLoginService.getUserLoggedIn());
    }

    public List<User> findAllUsersEnrolledForEvent(Long id) {
        return userEnrolledEventRepository.findByEventId(id);
    }
}
