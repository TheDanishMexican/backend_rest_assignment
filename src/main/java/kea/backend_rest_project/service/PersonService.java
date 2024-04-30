package kea.backend_rest_project.service;

import kea.backend_rest_project.dto.AgeResponse;
import kea.backend_rest_project.dto.GenderResponse;
import kea.backend_rest_project.dto.NationalityResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PersonService {

    private final WebClient webClient;

    public PersonService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<AgeResponse> getPersonAge(String name) {
        return webClient.get()
                .uri("https://api.agify.io/?name=" + name)
                .retrieve()
                .bodyToMono(AgeResponse.class);
    }

    public Mono<NationalityResponse> getPersonNationality(String name) {
        return webClient.get()
                .uri("https://api.nationalize.io/?name=" + name)
                .retrieve()
                .bodyToMono(NationalityResponse.class);
    }

    public Mono<GenderResponse> getPersonGender(String name) {
        return webClient.get()
                .uri("https://api.genderize.io/?name=" + name)
                .retrieve()
                .bodyToMono(GenderResponse.class);
    }
}
