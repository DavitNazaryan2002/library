package com.neetry.library.controller.book.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record CreateBookRequest(
        @NotBlank
        String title,
        @NotBlank
        String author,
        @NotBlank
        String genre,
        @NotBlank
        String description,
        @NotNull @Positive
        Long isbn,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate published,
        @NotBlank
        String publisher
) {
}
