package ru.malyutin.library.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.malyutin.library.dao.BookDAO;
import ru.malyutin.library.dao.PersonDAO;
import ru.malyutin.library.model.Person;
import ru.malyutin.library.services.BooksService;
import ru.malyutin.library.services.PeopleService;
import ru.malyutin.library.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }


    @GetMapping()
    public String show(Model model){
        model.addAttribute("people", peopleService.show());
        return "people/show";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id, Model model){
        model.addAttribute("person", peopleService.index(id));
        model.addAttribute("books", peopleService.getBooksByPersonId(id));
        return "people/index";
    }

    @GetMapping("/new")
    public String add(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "people/new";
        }else {
            peopleService.create(person);
            return "redirect:/people";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", peopleService.index(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("id") int id){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()) {
            return "people/edit";
        }
        else {
            peopleService.update(person, id);
            return "redirect:/people";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people";
    }
}
