package ru.malyutin.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "book")
@Data
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person reader;

    @Column(name = "name_book")
    @NotEmpty(message = "Название книги не может быть пустым")
    @NotNull(message = "Название книги не может быть пустым")
    private String nameBook;

    @Column(name = "author")
    @NotEmpty(message = "Введите имя автора")
    @NotNull(message = "Введите имя автора")
    private String author;

    @Column(name = "year_editions")
    private int yearEditions;

    public Book(int bookId, int personId, String nameBook, String author, int yearEditions) {
        this.bookId = bookId;
        this.nameBook = nameBook;
        this.author = author;
        this.yearEditions = yearEditions;
    }

    public Book() {
    }

}
