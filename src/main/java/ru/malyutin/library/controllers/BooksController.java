package ru.malyutin.library.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.malyutin.library.dao.BookDAO;
import ru.malyutin.library.dao.PersonDAO;
import ru.malyutin.library.model.Book;
import ru.malyutin.library.model.Person;
import ru.malyutin.library.services.BooksService;
import ru.malyutin.library.services.PeopleService;
import ru.malyutin.library.util.BookValidator;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;
    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService, BookValidator bookValidator) {
        this.booksService = booksService;
        this.peopleService = peopleService;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String show(Model model){
        model.addAttribute("books", booksService.show());
        return "books/show";
    }

    @GetMapping("/new")
    public String add(Model model){
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors()){
            return "books/new";
        }else {
            booksService.create(book);
            return "redirect:/books";
        }
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id, Model model){
        model.addAttribute("book", booksService.index(id));
        model.addAttribute("person", booksService.haveBook(id));
        model.addAttribute("people", peopleService.show());
        model.addAttribute("cleanPerson", new Person());
        return "books/index";
    }

    @PutMapping("/{id}")
    public String freeBook(@PathVariable("id") int id){
        booksService.freeBook(id);
        return "redirect:/books/" + id;
    }

    @PutMapping("/{id}/busy")
    public String busyBook(@ModelAttribute("person")Person person, @PathVariable("id") int id){
        booksService.busyBook(id, person.getPersonId());
        return "redirect:/books/" + id;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", booksService.index(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id){
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors()) {
            return "books/edit";
        }
        else {
            booksService.update(book, id);
            return "redirect:/books";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }

}
