package com.support.task.services;

import com.support.task.dto.ProductRequestDTO;
import com.support.task.dto.ProductResponseDTO;
import com.support.task.entities.Producer;
import com.support.task.entities.Product;
import com.support.task.mappers.ProductMapper;
import com.support.task.repositories.ProducerRepository;
import com.support.task.repositories.ProductRepository;
import com.support.task.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProducerRepository producerRepository;
    private final ProductMapper productMapper;
    private final ProductValidator productValidator;

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO request) {
        productValidator.validate(request);

        Producer producer = producerRepository.findById(request.producerId())
                .orElseThrow(() -> new RuntimeException("Producer not found"));

        Product product = new Product();
        updateProductFields(product, request, producer);

        Product savedProduct = productRepository.save(product);
        return productMapper.toResponseDto(savedProduct);
    }

    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO request) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productValidator.validate(request);

        Producer producer = producerRepository.findById(request.producerId())
                .orElseThrow(() -> new RuntimeException("Producer not found"));

        updateProductFields(existingProduct, request, producer);

        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toResponseDto(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    private void updateProductFields(Product product, ProductRequestDTO request, Producer producer) {
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setProducer(producer);
        product.setAttributes(request.attributes());
    }
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProductsByProducer(Long producerId) {
        return productRepository.findByProducerId(producerId).stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> searchProducts(String name, Long producerId) {
        if (name != null && !name.isBlank()) {
            return productRepository.findByNameContainingIgnoreCase(name).stream()
                    .map(productMapper::toResponseDto)
                    .toList();
        } else if (producerId != null) {
            return productRepository.findByProducerId(producerId).stream()
                    .map(productMapper::toResponseDto)
                    .toList();
        }
        return getAllProducts();
    }
}