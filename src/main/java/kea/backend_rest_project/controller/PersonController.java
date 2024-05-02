package kea.backend_rest_project.controller;

import kea.backend_rest_project.dto.AgeResponse;
import kea.backend_rest_project.dto.CountryItem;
import kea.backend_rest_project.dto.PersonResponse;
import kea.backend_rest_project.service.PersonService;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public Mono<PersonResponse> getPersonInfo(@RequestParam String firstName, @RequestParam String lastName,
                                              @RequestParam(required = false) String middleName) {
        return personService.getPersonInformation(firstName, lastName, middleName);
    }
}
