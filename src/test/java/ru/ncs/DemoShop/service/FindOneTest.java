package ru.ncs.DemoShop.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;
import ru.ncs.DemoShop.model.Product;
import ru.ncs.DemoShop.repository.ProductRepository;
import ru.ncs.DemoShop.service.data.ProductDTO;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static ru.ncs.DemoShop.model.ProductCategoryEnum.PERIPHERY;

public class FindOneTest {
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
    public void findProductTest() {
        UUID id = UUID.fromString("f8f35c84-dbb4-4fa2-9c75-689bbf03246e");
        LocalDateTime time = LocalDateTime.now();
        Product mockProduct = new Product(id, "Test", "d", PERIPHERY, 1.0, 1, time,true);

        ProductDTO mockDTO = ProductDTO.builder()
                .id(mockProduct.getId())
                .amount(mockProduct.getAmount())
                .category(mockProduct.getCategory())
                .amountUpdatedAt(mockProduct.getAmountUpdatedAt())
                .description(mockProduct.getDescription())
                .name(mockProduct.getName())
                .price(mockProduct.getPrice())
                .availability(mockProduct.isAvailability())
                .build();

        Mockito.doReturn(Optional.of(mockProduct))
                .when(productRepository)
                .findById(id);
        Mockito.doReturn(mockDTO)
                .when(conversionService)
                .convert(mockProduct, ProductDTO.class);

        ProductDTO found = productService.findOne(id);
        Assertions.assertEquals(mockProduct.getId(), found.getId());
    }
}
