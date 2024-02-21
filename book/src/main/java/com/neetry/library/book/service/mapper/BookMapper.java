package com.neetry.library.book.service.mapper;

import com.neetry.library.book.repository.model.BookEntity;
import com.neetry.library.book.service.model.Book;
import com.neetry.library.book.service.model.command.CreateBookCommand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring"
)
public interface BookMapper {
    Book mapToBook(BookEntity bookEntity);

    BookEntity mapToBookEntity(CreateBookCommand createBookCommand);
}
