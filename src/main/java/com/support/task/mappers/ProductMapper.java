package com.support.task.mappers;

import com.support.task.dto.ProducerDTO;
import com.support.task.dto.ProductResponseDTO;
import com.support.task.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponseDTO toResponseDto(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                new ProducerDTO(product.getProducer().getId(), product.getProducer().getName()),
                product.getAttributes()
        );
    }
}