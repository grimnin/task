package com.support.task.services;

import com.support.task.entities.Producer;
import com.support.task.repositories.ProducerRepository;
import com.support.task.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerRepository producerRepository;
    private final ProductRepository productRepository;


    @Transactional(readOnly = true)
    public List<Producer> getAllProducers() {
        return producerRepository.findAll();
    }

    @Transactional
    public Producer createProducer(Producer producer) {
        producerRepository.findByNameIgnoreCase(producer.getName())
                .ifPresent(p -> {
                    throw new RuntimeException("Producer with name '" + producer.getName() + "' already exists");
                });

        return producerRepository.save(producer);
    }

    @Transactional
    public Producer updateProducer(Long id, Producer producerDetails) {
        Producer producer = producerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producer not found with id: " + id));


        producerRepository.findByNameIgnoreCase(producerDetails.getName())
                .ifPresent(p -> {
                    if (!p.getId().equals(id)) {
                        throw new RuntimeException("Producer with name '" + producerDetails.getName() + "' already exists");
                    }
                });

        producer.setName(producerDetails.getName());
        return producerRepository.save(producer);
    }

    @Transactional
    public void deleteProducer(Long id) {
        Producer producer = producerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producer not found with id: " + id));
        if (productRepository.existsByProducerId(id)) {
            throw new RuntimeException("Cannot delete producer with assigned products. Delete products first.");
        }

        producerRepository.delete(producer);
    }
}