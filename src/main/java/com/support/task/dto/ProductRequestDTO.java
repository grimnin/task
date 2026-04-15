package com.support.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;

public record ProductRequestDTO(
        @NotBlank(message = "Name is required")
        String name,

        String description,

        BigDecimal price,

        @NotNull(message = "Producer ID is required")
        Long producerId,

        Map<String, String> attributes
) {}