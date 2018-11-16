package pl.aw.iventz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.aw.iventz.events.EventService;

@Controller
public class HomePageController {

    private EventService eventService;

    @Autowired
    public HomePageController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = "/")
    public String showHomePage(Model model) {
        model.addAttribute("comingEvents", eventService.findComingEvents());
        return "homepage";
    }
}
