package com.neetry.library.book.service.model;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDate;
import java.util.Date;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Book(
        Long id,
        String title,
        String author,
        String genre,
        String description,
        Long isbn,
        LocalDate published,
        String publisher,
        Date createdAt,
        Date updatedAt
) {
}
