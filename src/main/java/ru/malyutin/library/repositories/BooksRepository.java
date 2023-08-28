package ru.malyutin.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.malyutin.library.model.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
}
