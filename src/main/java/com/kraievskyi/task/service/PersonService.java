package com.kraievskyi.task.service;

import com.kraievskyi.task.model.dto.NameStatisticDto;
import com.kraievskyi.task.model.Person;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface PersonService {

    List<Person> findAllByFullNameEn(String fullName);

    List<NameStatisticDto> getPopularNames();

    String uploadFile(MultipartFile file);

}
