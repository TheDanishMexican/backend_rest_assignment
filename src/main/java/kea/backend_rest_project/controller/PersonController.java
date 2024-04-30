package kea.backend_rest_project.controller;

import kea.backend_rest_project.dto.CountryItem;
import kea.backend_rest_project.dto.PersonResponse;
import kea.backend_rest_project.service.PersonService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{name}")
    public Mono<PersonResponse> getPersonInfo(@PathVariable String name) {
        return Mono.zip(
                personService.getPersonAge(name),
                personService.getPersonGender(name),
                personService.getPersonNationality(name)
        ).map(tuple -> {
            List<CountryItem> nationality = tuple.getT3().country();
            CountryItem highestProbabilityCountry = nationality.stream()
                    .max(Comparator.comparing(CountryItem::probability))
                    .orElse(null);
            return new PersonResponse(
                    name,
                    tuple.getT1().age(),
                    tuple.getT1().count(),
                    tuple.getT2().gender(),
                    tuple.getT2().probability(),
                    highestProbabilityCountry.country_id(),
                    highestProbabilityCountry.probability()
            );
        });
    }


}
