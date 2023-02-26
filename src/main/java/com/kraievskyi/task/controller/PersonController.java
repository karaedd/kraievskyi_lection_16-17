package com.kraievskyi.task.controller;

import com.kraievskyi.task.model.Person;
import com.kraievskyi.task.service.PersonService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import com.kraievskyi.task.model.dto.NameStatisticDto;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
       return personService.uploadFile(file);
    }

    @GetMapping()
    public List<Person> getPersonByFullName(@RequestParam String fullName) {
        return personService.findAllByFullNameEn(fullName);
    }

    @GetMapping("/popular-names")
    public List<NameStatisticDto> getPopularNames() {
        return personService.getPopularNames();
    }
}
