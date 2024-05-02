package kea.backend_rest_project.service;

import kea.backend_rest_project.dto.AgeResponse;
import kea.backend_rest_project.dto.GenderResponse;
import kea.backend_rest_project.dto.NationalityResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
public class PersonInfoService {

    private final WebClient webClient;

    public PersonInfoService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Cacheable("ages")
    public Mono<AgeResponse> getPersonAge(String name) {
        String uri = UriComponentsBuilder.fromHttpUrl("https://api.agify.io/")
                .queryParam("name", name)
                .toUriString();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(AgeResponse.class);
    }

    @Cacheable("nationalities")
    public Mono<NationalityResponse> getPersonNationality(String name) {
        String uri = UriComponentsBuilder.fromHttpUrl("https://api.nationalize.io/")
                .queryParam("name", name)
                .toUriString();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(NationalityResponse.class);
    }

    @Cacheable("genders")
    public Mono<GenderResponse> getPersonGender(String name) {
        String uri = UriComponentsBuilder.fromHttpUrl("https://api.genderize.io/")
                .queryParam("name", name)
                .toUriString();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(GenderResponse.class);
    }
}
