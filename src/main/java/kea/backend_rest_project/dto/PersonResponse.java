package kea.backend_rest_project.dto;

public record PersonResponse(String name, int age,
                             String gender, double genderProbability,
                             String country, double probability) {
}
