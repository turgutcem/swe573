package com.swe573.swe573.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SearchDTO {
    @NotBlank
    @NotEmpty
    @NotNull
    private String searchField;

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }
}
