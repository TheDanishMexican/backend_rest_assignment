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

//    @GetMapping
//    public Mono<ResponseEntity<AgeResponse>> getPersonAge(@RequestParam String firstName, @RequestParam String lastName,
//                                                          @RequestParam(required = false) String middleName) {
//        String name = firstName + "+" + (middleName != null ? middleName + "+" : "") + lastName;
//        return personService.getPersonAge(name)
//                .map(ageResponse -> ResponseEntity.ok()
//                        .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
//                        .body(ageResponse));
//    }

    @GetMapping
    public Mono<PersonResponse> getPersonInfo(@RequestParam String firstName, @RequestParam String lastName,
                                              @RequestParam(required = false) String middleName) {
        String name = firstName + "+" + (middleName != null ? middleName + "+" : "") + lastName;

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
                    tuple.getT2().gender(),
                    tuple.getT2().probability(),
                    highestProbabilityCountry.country_id(),
                    highestProbabilityCountry.probability()
            );
        });
    }


}
