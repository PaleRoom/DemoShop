package ru.ncs.DemoShop.controller;

import io.restassured.http.ContentType;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ncs.DemoShop.AbstractSpringBootTest;
import ru.ncs.DemoShop.controller.request.CreateProductRequest;
import ru.ncs.DemoShop.service.ProductServiceImpl;
import ru.ncs.DemoShop.service.immutable.ImmutableCreateProductRequest;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.ncs.DemoShop.model.ProductCategoryEnum.PERIPHERY;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ProductControllerImplTest
        extends AbstractSpringBootTest {

    @SpyBean
    ConversionService conversionService;

    @MockBean
    ProductServiceImpl productService;

    private ImmutableCreateProductRequest imReqStub;
    private CreateProductRequest reqBodyStub;


    @Override
    protected void init() {
        reqBodyStub = CreateProductRequest.builder()
                .amount(1)
                .category(PERIPHERY)
                .description("p")
                .name("Test")
                .price(1)
                .build();

        imReqStub = ImmutableCreateProductRequest.builder()
                .amount(reqBodyStub.getAmount())
                .category(reqBodyStub.getCategory())
                .description(reqBodyStub.getDescription())
                .name(reqBodyStub.getName())
                .price(reqBodyStub.getPrice())
                .build();

        final UUID idStub = UUID.randomUUID();
        when(conversionService.convert(reqBodyStub, ImmutableCreateProductRequest.class)).
                thenReturn(imReqStub);
        when(productService.save(any())).thenReturn(idStub);
    }

    @Test
    @DisplayName("Given valid request body " +
            "when call Create " +
            "then Controller returns Status code 201")
    void givenValidRequestBody_thenControllerReturnsStatus201() {

        given()
                .baseUri("http://localhost:7070")
                .basePath("/products")
                .contentType(ContentType.JSON)
                .when()
                .body(reqBodyStub)
                .post()
                .then()
                .statusCode(201).log().all();
    }

    @Test
    @DisplayName("Given request body without Name field " +
            "when call Create " +
            "then Controller returns Status code 400")
    void givenWithoutNameFieldRequestBody_thenControllerReturnsStatus201() {

        CreateProductRequest reqBodyStub = CreateProductRequest.builder()
                .amount(1)
                .category(PERIPHERY)
                .description("p")
                .price(1)
                .build();

        given()
                .baseUri("http://localhost:7070")
                .basePath("/products")
                .contentType(ContentType.JSON)
                .when()
                .body(reqBodyStub)
                .post()
                .then()
                .statusCode(400).log().all();
    }
}