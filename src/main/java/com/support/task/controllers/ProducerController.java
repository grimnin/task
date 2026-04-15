package com.support.task.controllers;

import com.support.task.entities.Producer;
import com.support.task.repositories.ProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/producers")
@RequiredArgsConstructor
public class ProducerController {
    private final ProducerRepository producerRepository;

    @GetMapping
    public List<Producer> getAllProducers() {
        return producerRepository.findAll();
    }
}