package ru.malyutin.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.malyutin.library.model.Book;
import ru.malyutin.library.model.Person;
import ru.malyutin.library.repositories.BooksRepository;
import ru.malyutin.library.repositories.PeopleRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> show(){
        return booksRepository.findAll();
    }

    @Transactional
    public void create(Book newBook){
        booksRepository.save(newBook);
    }

    public Book index(int id){
        return booksRepository.findById(id).orElse(null);
    }
    @Transactional
    public void freeBook(int id){
        Book book = booksRepository.findById(id).orElse(null);
        if (book != null){
            Person person = book.getReader();
            person.getBooks().remove(book);
            book.setReader(null);
        }
    }
    @Transactional
    public void busyBook(int idBook, int idPerson){
        Book book = booksRepository.findById(idBook).orElse(null);
        if (book != null){
            Person person = peopleRepository.findById(idPerson).orElse(null);
            if (person != null) {
                book.setReader(person);
                person.getBooks().add(book);
            }
        }
    }
    @Transactional
    public void update(Book book, int id){
        book.setBookId(id);
        booksRepository.save(book);
    }
    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    public Person haveBook(int id){
        Book book = booksRepository.findById(id).orElse(null);
        if (book != null){
            return book.getReader();
        }
        return null;
    }
}
