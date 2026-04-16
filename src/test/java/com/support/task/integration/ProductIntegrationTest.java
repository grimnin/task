package com.support.task.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.support.task.dto.ProductRequestDTO;
import com.support.task.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void shouldCreateProductAndPersistInDatabase() throws Exception {
        ProductRequestDTO request = new ProductRequestDTO(
                "Integracyjny Smartfon",
                "Opis testowy",
                new BigDecimal("3500.00"),
                1L, // Zakładamy, że Samsung (ID 1) istnieje dzięki Liquibase
                Map.of("Memory", "256GB", "Color", "Silver")
        );

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Integracyjny Smartfon"))
                .andExpect(jsonPath("$.producer.name").exists())
                .andExpect(jsonPath("$.attributes.Memory").value("256GB"));
    }

    @Test
    void shouldReturnEmptyListWhenNoProducts() throws Exception {
        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }
}