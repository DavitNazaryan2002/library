package com.neetry.library.book.repository;

import com.neetry.library.book.repository.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {

    @Query(
            "SELECT e FROM BookEntity e WHERE " +
                    " (e.title LIKE %:title%) " +
                    "AND " +
                    "(:author IS NULL OR e.author = :author) AND " +
                    "(:genre IS NULL OR e.genre = :genre) AND " +
                    "(:publisher IS NULL OR e.publisher = :publisher) AND " +
                    "(:olderThan IS NULL OR e.published <= :olderThan) AND " +
                    "(:newerThan IS NULL OR e.published >= :newerThan)"
    )
    List<BookEntity> searchByFilters(
            String title,
            @Nullable String author,
            @Nullable String genre,
            @Nullable String publisher,
            @Nullable LocalDate olderThan,
            @Nullable LocalDate newerThan
    );

    Optional<BookEntity> findById(Long id);

    void deleteById(Long id);
}
