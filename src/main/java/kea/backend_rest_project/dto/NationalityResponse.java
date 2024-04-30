package kea.backend_rest_project.dto;

import java.util.List;

public record NationalityResponse(String name, int count, List<CountryItem> country) {
}
