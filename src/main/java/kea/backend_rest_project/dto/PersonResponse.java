package kea.backend_rest_project.dto;

public record PersonResponse(String name, String firstName, String middleName,
                             String lastName, int age,
                             String gender, double genderProbability,
                             String country, Double countryProbability) {
}
