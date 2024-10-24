package ru.gubern.springmvc.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.gubern.springmvc.dao.PersonDao;
import ru.gubern.springmvc.models.Person;
import ru.gubern.springmvc.util.PersonValidator;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDao personDAO;
    private PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDao personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) throws SQLException {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) throws SQLException {
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult result) {

        personValidator.validate(person, result);

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") Person person, @PathVariable("id") int id, BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);

        personDAO.update(id, person);
        return "redirect:/people";
    }
}
