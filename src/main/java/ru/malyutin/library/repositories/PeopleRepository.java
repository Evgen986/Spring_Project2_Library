package ru.malyutin.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.malyutin.library.model.Person;

@Repository
public interface PeopleRepository extends JpaRepository <Person, Integer> {
}
