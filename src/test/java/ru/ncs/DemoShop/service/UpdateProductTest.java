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
import ru.ncs.DemoShop.service.immutable.ImmutableUpdateProductRequest;

import static ru.ncs.DemoShop.model.ProductCategoryEnum.CPU;
import static ru.ncs.DemoShop.model.ProductCategoryEnum.PERIPHERY;

public class UpdateProductTest {
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
        UUID id = UUID.fromString("b5bcfb71-48f7-4768-b590-c7233c8ada4b");
        ImmutableUpdateProductRequest imRequest = ImmutableUpdateProductRequest.builder()
                .name("Test name 1")
                .description("Updated Test Description")
                .category(CPU)
                .price(1.0)
                .amount(1)
                .availability(false)
                .build();

        Product mockProduct = new Product(id, "Test name 1", "Test Description", PERIPHERY,10.0,100, LocalDateTime.now(),true);

        ProductDTO mockDTO = ProductDTO.builder()
                .id(mockProduct.getId())
                .amount(mockProduct.getAmount())
                .category(mockProduct.getCategory())
                .amountUpdatedAt(mockProduct.getAmountUpdatedAt())
                .description(mockProduct.getDescription())
                .name(mockProduct.getName())
                .price(mockProduct.getPrice())
                .amountUpdatedAt(mockProduct.getAmountUpdatedAt())
                .build();

        Mockito.doReturn(Optional.of(mockProduct))
                .when(productRepository)
                .findById(id);

        Mockito.doReturn(Optional.of(mockProduct))
                .when(productRepository)
                .save(mockProduct);

        Assertions.assertEquals(mockProduct.getId(), id, "Id Is not the same");
        Assertions.assertEquals(mockProduct.getName(), imRequest.getName(), "name Is not the same");
        Assertions.assertEquals(mockProduct.getDescription(), imRequest.getDescription(),"Id Is not the same");
        Assertions.assertEquals(mockProduct.getCategory(), imRequest.getCategory(),"Category Is not the same");
        Assertions.assertEquals(mockProduct.getPrice(), imRequest.getPrice(), "Price Is not the same");
        Assertions.assertEquals(mockProduct.getAmount(), imRequest.getAmount(), "Amount Is not the same");
        Assertions.assertEquals(mockProduct.isAvailability(), imRequest.getAvailability(), "Availability Is not the same");

        Mockito.doReturn(mockDTO)
                .when(conversionService)
                .convert(mockProduct, ProductDTO.class);

        ProductDTO updated = productService.update(imRequest, id );
        Assertions.assertEquals(mockProduct.getId(), updated.getId());
    }
}
