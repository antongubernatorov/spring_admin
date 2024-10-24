package ru.gubern.springmvc.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.gubern.springmvc.dao.PersonDao;
import ru.gubern.springmvc.models.Person;

@Component
public class PersonValidator implements Validator {

    private PersonDao personDao;

    @Autowired
    public PersonValidator(PersonDao personDao) {
        this.personDao = personDao;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        //посмотреть есть ли человек с таким емэйлом
        if (personDao.show(person.getEmail()).isPresent()){
            errors.rejectValue("email",  "", "This email is already taken");
        }
    }
}
