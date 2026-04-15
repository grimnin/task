package com.support.task.dto;

import java.math.BigDecimal;
import java.util.Map;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        ProducerDTO producer,
        Map<String, String> attributes
) {}