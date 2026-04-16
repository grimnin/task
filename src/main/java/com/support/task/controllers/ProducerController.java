package com.support.task.controllers;

import com.support.task.entities.Producer;
import com.support.task.repositories.ProducerRepository;
import com.support.task.services.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producers")
@RequiredArgsConstructor
public class ProducerController {
    private final ProducerRepository producerRepository;
    private final ProducerService producerService;

    @GetMapping
    public List<Producer> getAllProducers() {
        return producerRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Producer> createProducer(@RequestParam String name) {
        if (name == null || name.isBlank()) {
            throw new RuntimeException("Producer name cannot be empty");
        }

        Producer producer = new Producer();
        producer.setName(name);
        return new ResponseEntity<>(producerService.createProducer(producer), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producer> updateProducer(
            @PathVariable Long id,
            @RequestParam String name
    ) {
        if (name == null || name.isBlank()) {
            throw new RuntimeException("New producer name cannot be empty");
        }
        Producer details = new Producer();
        details.setName(name);

        return ResponseEntity.ok(producerService.updateProducer(id, details));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducer(@PathVariable Long id) {
        producerService.deleteProducer(id);
        return ResponseEntity.noContent().build();
    }

}