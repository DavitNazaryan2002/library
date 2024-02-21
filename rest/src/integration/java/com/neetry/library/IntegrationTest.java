package com.neetry.library;

import com.neetry.library.book.repository.BookJpaRepository;
import com.neetry.library.book.repository.model.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IntegrationTest extends AbstractPostgresAwareIntegrationTest {

    @Autowired
    private BookJpaRepository repository;

    @Test
    public void test1() {

        BookEntity book = new BookEntity();

        repository.save(book);
        final var retrieved = repository.findAll();

//        repository.findByTitle(null);
        System.out.println(retrieved.get(0).toString());
    }
}
