package pl.aw.iventz.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.aw.iventz.comments.Comment;
import pl.aw.iventz.comments.CommentDTO;
import pl.aw.iventz.comments.CommentService;
import pl.aw.iventz.user_enrolled_event.UserEnrolledEventService;
import pl.aw.iventz.users.registration.User;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequestMapping(value = "/event")
public class EventController {

    private EventService eventService;
    private CommentService commentService;
    private UserEnrolledEventService userEnrolledEventService;

    @Autowired
    public EventController(EventService eventService, CommentService commentService, UserEnrolledEventService userEnrolledEventService) {
        this.eventService = eventService;
        this.commentService = commentService;
        this.userEnrolledEventService = userEnrolledEventService;
    }

    @GetMapping(value = "/add")
    public String showAddEventForm(Model model) {
        model.addAttribute("eventDTO", new EventDTO());
        return "event/addEventForm";
    }

    @PostMapping(value = "/add")
    public String addNewEvent(@ModelAttribute @Valid EventDTO eventDTO, BindingResult validationResult, RedirectAttributes redirectAttributes) {
        validateEnteredDate(eventDTO, validationResult);
        if (validationResult.hasErrors()) {
            return "event/addEventForm";
        }
        eventService.createNewEvent(eventDTO);
        redirectAttributes.addFlashAttribute("eventCreated", eventDTO.getTitle());
        return "redirect:/";
    }

    @GetMapping(value = "/show")
    public String showEvents(@RequestParam(required = false) String query, @RequestParam(required = false) String eventTime, Model model) {
        List<Event> eventsBySpecificQuery = eventService.findEventsBySpecificQuery(query, eventTime);

        model.addAttribute("eventsByQuery", eventsBySpecificQuery);
        model.addAttribute("query", query);
        model.addAttribute("eventTime", eventTime);
        return "event/showEvents";
    }

    @GetMapping(value = "/show/{id}")
    public String showConcreteEvent(@PathVariable Long id, Model model) {
        Event concreteEventById = eventService.findConcreteEventById(id);
        List<Comment> allCommentsByEventId = commentService.findAllCommentsForEventByEventId(concreteEventById.getId());
        Boolean flagUserEnrolled = userEnrolledEventService.checkIfUserAlreadyEnrolledForEvent(id);
        List<User> allUsersEnrolledForEvent = userEnrolledEventService.findAllUsersEnrolledForEvent(id);

        model.addAttribute("allUsersEnrolled", allUsersEnrolledForEvent);
        model.addAttribute("allCommentsByEventId", allCommentsByEventId);
        model.addAttribute("concreteEvent", concreteEventById);
        model.addAttribute("commentDTO", new CommentDTO());
        model.addAttribute("flagAlreadyEnrolled", flagUserEnrolled);
        return "event/showConcreteEvent";
    }

    @PostMapping(value = "/addComment/{id}")
    public String addNewComment(@ModelAttribute @Valid CommentDTO commentDTO, BindingResult validationResult, RedirectAttributes redirectAttributes, Model model, @PathVariable Long id) {
        Event concreteEventById = eventService.findConcreteEventById(id);
        if (validationResult.hasErrors()) {
            //todo: wyciagnac do metodki kod do walidacji
            model.addAttribute("concreteEvent", concreteEventById);
            return "event/showConcreteEvent";
        }
        commentService.addNewComment(commentDTO, concreteEventById);
        return "redirect:/event/show/" + concreteEventById.getId();
    }

    //todo: dokończyć
    @PostMapping(value = "/enrollForEvent/{eventId}")
    public String enrollForEvent(@PathVariable Long eventId, RedirectAttributes redirectAttributes) {
        Boolean flagAlreadyEnrolled = userEnrolledEventService.enrollForEvent(eventId);
        if (!flagAlreadyEnrolled) {
            redirectAttributes.addFlashAttribute("msg", "Zapisałeś/aś się na wydarzenie");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Już jesteś zapisany/a na to wydarzenie");
        }
        return "redirect:/event/show/" + eventId;
    }

    @PostMapping(value = "/leaveEvent/{eventId}")
    public String leaveEvent(@PathVariable Long eventId,RedirectAttributes redirectAttributes) {
        userEnrolledEventService.leaveEvent(eventId);
        redirectAttributes.addFlashAttribute("msg", "Odwołałeś swój udział w wydarzeniu");
        return "redirect:/event/show/" + eventId;
    }

    private void validateEnteredDate(EventDTO eventDTO, BindingResult bindingResult) {
        LocalDateTime localDateTimeStart = eventDTO.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime localDateTimeEnd = eventDTO.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();

        if (localDateTimeStart.isBefore(now)) {
            bindingResult.rejectValue("startDate", "data.inPast", "Wydarzenie nie może odbyć się w przeszłości (jeszcze :) )");
        }
        if (localDateTimeStart.isAfter(localDateTimeEnd)) {
            bindingResult.rejectValue("endDate", "end.BeforeStart", "Wydarzenie nie może kończyć się zanim się zacznie");
        }
    }
}
