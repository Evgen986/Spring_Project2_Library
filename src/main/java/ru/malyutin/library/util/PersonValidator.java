package ru.malyutin.library.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.malyutin.library.model.Person;

import java.time.YearMonth;

@Component
public class PersonValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(person.getYearOfBirth() < YearMonth.now().getYear()-100 || person.getYearOfBirth() > YearMonth.now().getYear()){
            errors.rejectValue("yearOfBirth", "", "Год рождения введен некоррекно");
        }
    }
}
