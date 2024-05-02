package kea.backend_rest_project.service;


import kea.backend_rest_project.dto.CountryItem;
import kea.backend_rest_project.dto.PersonResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;


@Service
public class PersonService {

    private final PersonInfoService personInfoService;


    public PersonService(PersonInfoService personInfoService) {
        this.personInfoService = personInfoService;
    }

    public String capitalize(String name) {
        if (name == null || name.isEmpty()) {
            return "";
        }
        if (name.contains(" ")) {
            String[] names = name.split(" ");
            String capitalizedName = "";
            for (String nameItem : names) {
                nameItem = nameItem.toUpperCase().charAt(0) + nameItem.substring(1).toLowerCase();
                capitalizedName += nameItem + " ";
            }
            return capitalizedName.trim();
        }
        return name.toUpperCase().charAt(0) + name.substring(1).toLowerCase();
    }

    public Mono<CountryItem> getHighestProbabilityCountry(List<CountryItem> countries) {
        return Mono.justOrEmpty(countries.stream()
                .max(Comparator.comparing(CountryItem::probability)));
    }


    public Mono<PersonResponse> getPersonInformation(String firstName, String lastName, String middleName) {
        String name = firstName + "+" + (middleName != null ? middleName + "+" : "") + lastName;

        return Mono.zip(
                personInfoService.getPersonAge(name),
                personInfoService.getPersonGender(name),
                personInfoService.getPersonNationality(name)
        ).flatMap(tuple -> {
            List<CountryItem> nationality = tuple.getT3().country();
            return getHighestProbabilityCountry(nationality)
                    .map(highestProbabilityCountry -> new PersonResponse(
                            capitalize(name.replace("+", " ")),
                            capitalize(firstName),
                            capitalize(middleName),
                            capitalize(lastName),
                            tuple.getT1().age(),
                            tuple.getT2().gender(),
                            tuple.getT2().probability(),
                            highestProbabilityCountry.country_id(),
                            highestProbabilityCountry.probability()
                    ))
                    .defaultIfEmpty(new PersonResponse(
                            capitalize(name.replace("+", " ")),
                            capitalize(firstName),
                            capitalize(middleName),
                            capitalize(lastName),
                            tuple.getT1().age(),
                            tuple.getT2().gender(),
                            tuple.getT2().probability(),
                            "unknown",
                            null
                    ));
        });
    }
}
