package com.kraievskyi.task.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@EqualsAndHashCode
@Document
public class Person {

    @JsonProperty(value = "type_of_official")
    private String typeOfOfficial;
    @JsonProperty(value = "first_name")
    private String firstName;
    @JsonProperty(value = "first_name_en")
    private String firstNameEn;
    @JsonProperty(value = "last_name")
    private String lastName;
    @JsonProperty(value = "last_name_en")
    private String lastNameEn;
    @JsonProperty(value = "is_pep")
    private Boolean isPep;
    @JsonProperty(value = "full_name_en")
    private String fullNameEn;
}
