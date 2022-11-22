package ru.ncs.DemoShop.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;
import ru.ncs.DemoShop.exception.ProductNotFoundException;
import ru.ncs.DemoShop.repository.ProductRepository;
import ru.ncs.DemoShop.service.data.ProductDTO;

import java.util.Optional;
import java.util.UUID;

public class FindProductTestAbsent {
    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ConversionService conversionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findProductTestAbsent() {
        UUID id = UUID.fromString("f8a35c84-dbb4-4fa2-9c75-689bbf03246e");
        Mockito.doReturn(Optional.empty())
                .when(productRepository)
                .findById(id);

        Mockito.doThrow(new ProductNotFoundException())
                .when(conversionService)
                .convert(null, ProductDTO.class);

        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.findOne(id));
    }
}
