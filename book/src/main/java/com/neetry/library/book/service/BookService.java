package com.neetry.library.book.service;

import com.neetry.library.book.repository.BookJpaRepository;
import com.neetry.library.book.service.mapper.BookMapper;
import com.neetry.library.book.service.model.Book;
import com.neetry.library.book.service.model.command.CreateBookCommand;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    private final BookJpaRepository bookJpaRepository;

    private final BookMapper bookMapper;

    public BookService(
            BookJpaRepository bookJpaRepository,
            BookMapper bookMapper
    ) {
        this.bookJpaRepository = bookJpaRepository;
        this.bookMapper = bookMapper;
    }

    public Book create(CreateBookCommand createBookCommand) {
        final var bookEntity = bookJpaRepository.save(bookMapper.mapToBookEntity(createBookCommand));
        return bookMapper.mapToBook(bookEntity);
    }

    public Book findById(Long id) {
        final var bookEntity = bookJpaRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            throw new RuntimeException("Not found");
                        }
                );
        return bookMapper.mapToBook(bookEntity);
    }

    public List<Book> searchByFilters(
            String title,
            @Nullable String author,
            @Nullable String genre,
            @Nullable String publisher,
            @Nullable LocalDate olderThan,
            @Nullable LocalDate newerThan
    ) {
        return bookJpaRepository
                .searchByFilters(
                        title,
                        author,
                        genre,
                        publisher,
                        olderThan,
                        newerThan
                )
                .stream()
                .map(bookMapper::mapToBook)
                .toList();
    }

    public void deleteBookById(Long id) {
        bookJpaRepository.deleteById(id);
    }
}
