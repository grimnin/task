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
    public ResponseEntity<Producer> create(@RequestBody Producer producer) {
        // Prosta walidacja: nazwa producenta musi być unikalna
        if (producerRepository.findByNameIgnoreCase(producer.getName()).isPresent()) {
            throw new RuntimeException("Producer with this name already exists");
        }
        return new ResponseEntity<>(producerRepository.save(producer), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producer> updateProducer(@PathVariable Long id, @RequestBody Producer producer) {
        return ResponseEntity.ok(producerService.updateProducer(id, producer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducer(@PathVariable Long id) {
        producerService.deleteProducer(id);
        return ResponseEntity.noContent().build();
    }

}