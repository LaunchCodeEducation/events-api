package org.launchcode.Spring.REST.API.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.launchcode.Spring.REST.API.Models.Event;
import org.launchcode.Spring.REST.API.Models.EventDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/events")
public class EventController {

    @GetMapping
    public List<Event> getEvents() {
        return Event.findAllItems();
    }
    @GetMapping(value = "/{id}")
    public Event getEventById(@PathVariable int id) {
        return Event.findItem(id);
    }

    @PostMapping
    public ResponseEntity<Event> postEvent(HttpServletRequest servletRequest, @RequestBody EventDto eventDto) throws URISyntaxException {
        Event event = Event.createEvent(eventDto.getName(), eventDto.getDescription());
        URI location = new URI(servletRequest.getRequestURL().toString() + "/" + event.getId());

        return ResponseEntity.created(location).body(event);
    }

    @PatchMapping(value = "/{id}")
    public Event patchEvent(@PathVariable int id, @RequestBody EventDto eventDto) {
        Event theEvent = Event.findItem(id);
        if (theEvent != null) {
            if (eventDto.getName() != null) {
                theEvent.setName(eventDto.getName());
            }
            if (eventDto.getDescription() != null) {
                theEvent.setDescription(eventDto.getDescription());
            }
        }
        return theEvent;
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteEvent(@PathVariable int id) {
        boolean deleted = Event.deleteItem(id);
        if (deleted) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
