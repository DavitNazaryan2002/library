package com.neetry.library.controller.book;

import com.neetry.library.book.service.BookService;
import com.neetry.library.book.service.model.Book;
import com.neetry.library.controller.book.mapper.BookRequestMapper;
import com.neetry.library.controller.book.model.CreateBookRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController("/api/book/v1/")
@Validated
public class BookController {

    private final BookService bookService;

    private final BookRequestMapper mapper;

    public BookController(
            BookService bookService,
            BookRequestMapper mapper
    ) {
        this.bookService = bookService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Book> createBook(
            @RequestBody @Valid CreateBookRequest request
    ) {
        final var book = bookService.create(mapper.mapToCreateBookCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(
            @PathVariable Long id
    ) {
        final var book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping
    public ResponseEntity<List<Book>> searchByFilters(
            @RequestParam
            @NotBlank
                    String title,

            @RequestParam(required = false)
            @NotBlank
                    String author,

            @RequestParam(required = false)
            @NotBlank
                    String genre,

            @RequestParam(required = false)
            @NotBlank
                    String publisher,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate olderThan,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate newerThan
    ) {
        final var books = bookService.searchByFilters(
                title,
                author,
                genre,
                publisher,
                olderThan,
                newerThan
        );

        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id
    ) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok().build();
    }

    // TODO: Add get books paginated endpoint for showing them in the main page

    // TODO: Add recommend books endpoint
}
