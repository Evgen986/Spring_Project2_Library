package ru.malyutin.library.services;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.malyutin.library.model.Book;
import ru.malyutin.library.model.Person;
import ru.malyutin.library.repositories.PeopleRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, EntityManagerFactory entityManager) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> show() {
        return peopleRepository.findAll();
    }

    public Person index(int id){
        return peopleRepository.findById(id).orElse(null);
    }

    public List <Book> getBooksByPersonId(int id){
        Optional <Person> person = peopleRepository.findById(id);

        if(person.isPresent()){
            Hibernate.initialize(person.get().getBooks());
            return person.get().getBooks();
        }
        else{
            return Collections.emptyList();
        }
    }

    @Transactional
    public void create(Person newPerson){
        peopleRepository.save(newPerson);
    }
    @Transactional()
    public void update(Person person, int id){
        person.setPersonId(id);
        peopleRepository.save(person);
    }
    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

//    public Person haveBook(int id){
//
//        return jdbcTemplate.query("select * from person where personid = (select personid from book where bookid = ?)",
//                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
//    }

}
