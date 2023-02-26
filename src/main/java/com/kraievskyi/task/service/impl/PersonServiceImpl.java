package com.kraievskyi.task.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kraievskyi.task.model.dto.NameStatisticDto;
import com.kraievskyi.task.model.Person;
import com.kraievskyi.task.repository.PersonRepository;
import com.kraievskyi.task.service.PersonService;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final MongoTemplate mongoTemplate;

    public PersonServiceImpl(PersonRepository dataRepository, MongoTemplate mongoTemplate) {
        this.personRepository = dataRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    @Transactional
    public List<Person> findAllByFullNameEn(String fullName) {
        return personRepository.findAllByFullNameEn(fullName);
    }

    @Override
    @Transactional
    public List<NameStatisticDto> getPopularNames() {
        GroupOperation countName = group("firstName")
                .count().as("count");
        SortOperation sort = sort(Sort.by(Sort.Direction.DESC, "count"));
        MatchOperation filter= match(Criteria.where("isPep").is(true));
        LimitOperation limit = limit(10);
        ProjectionOperation toMatchModel = project()
                .andExpression("firstName").as("personName")
                .andExpression("count").as("quantity");
        Aggregation aggregation = newAggregation(filter, countName, sort, limit, toMatchModel);
        return mongoTemplate.aggregate(aggregation, Person.class, NameStatisticDto.class).getMappedResults();
    }

    @Override
    @Transactional
    public String uploadFile(MultipartFile file) {
        personRepository.deleteAll();
        try (
                ZipInputStream zis = new ZipInputStream(file.getInputStream())) {
            ZipEntry zipEntry;
            while (true) {
                try {
                    if ((zipEntry = zis.getNextEntry()) == null) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (!zipEntry.isDirectory()) {
                    String content;
                    try {
                        content = new String(zis.readAllBytes(), StandardCharsets.UTF_8);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    ObjectMapper objectMapper = new ObjectMapper();
                    List<Person> persons;
                    try {
                        persons = objectMapper.readValue(content, new TypeReference<>() {
                        });
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }

                    personRepository.saveAll(persons);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "File uploaded successfully.";
    }
}
