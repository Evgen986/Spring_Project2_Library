package ru.malyutin.library.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.malyutin.library.model.Book;

import java.time.YearMonth;

@Component
public class BookValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if(book.getYearEditions() < 1 || book.getYearEditions() > YearMonth.now().getYear())
            errors.rejectValue("yearEditions", "", "Вы ввели не корректный год");
    }
}
