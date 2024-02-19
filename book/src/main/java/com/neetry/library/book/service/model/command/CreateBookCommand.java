package com.neetry.library.book.service.model.command;

import java.time.LocalDate;

public record CreateBookCommand(
        String title,
        String author,
        String genre,
        String description,
        Long isbn,
        LocalDate published,
        String publisher
) {
}
