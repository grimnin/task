package com.support.task.unit;

import com.support.task.dto.ProducerDTO;
import com.support.task.dto.ProductRequestDTO;
import com.support.task.dto.ProductResponseDTO;
import com.support.task.entities.Producer;
import com.support.task.entities.Product;
import com.support.task.mappers.ProductMapper;
import com.support.task.repositories.ProducerRepository;
import com.support.task.repositories.ProductRepository;
import com.support.task.services.ProductService;
import com.support.task.validators.ProductValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProducerRepository producerRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private ProductValidator productValidator;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldThrowExceptionWhenProducerNotFoundInCreate() {

        ProductRequestDTO request = new ProductRequestDTO(
                "Smartphone", "Desc", new BigDecimal("100"), 99L, Map.of()
        );

        doNothing().when(productValidator).validate(request);
        when(producerRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                productService.createProduct(request)
        );

        assertEquals("Producer not found", exception.getMessage());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void shouldCreateProductSuccessfully() {
        ProductRequestDTO request = new ProductRequestDTO(
                "Smartphone", "Desc", new BigDecimal("100"), 1L, Map.of("Color", "Black")
        );
        Producer producer = new Producer(1L, "Samsung");

        when(producerRepository.findById(1L)).thenReturn(Optional.of(producer));
        when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArguments()[0]);
        when(productMapper.toResponseDto(any(Product.class))).thenReturn(new ProductResponseDTO(1L, "Smartphone", "Desc", new BigDecimal("100"), new ProducerDTO(1L, "Samsung"), Map.of("Color", "Black")));

        ProductResponseDTO result = productService.createProduct(request);

        assertNotNull(result);
        assertEquals("Smartphone", result.name());
        verify(productValidator).validate(request);
        verify(productRepository).save(any(Product.class));
    }
}