package kea.backend_rest_project.dto;



import java.util.List;

public record PersonResponse(String name, int age, double ageProbability,
                             String gender, double genderProbability,
                             String country, double probability) {
}
