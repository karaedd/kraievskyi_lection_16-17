package com.kraievskyi.task.model.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document
public class NameStatisticDto {
    @Id
    private String personName;
    private Integer quantity;
}

