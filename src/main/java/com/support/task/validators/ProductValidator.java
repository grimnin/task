package com.support.task.validators;

import com.support.task.dto.ProductRequestDTO;
import com.support.task.repositories.ProducerRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {

    private final ProducerRepository producerRepository;

    public ProductValidator(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    public void validate(ProductRequestDTO request) {

        if (!producerRepository.existsById(request.producerId())) {
            throw new RuntimeException("Producer with ID " + request.producerId() + " does not exist");
        }

    }
}