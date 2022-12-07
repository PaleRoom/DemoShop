package ru.ncs.DemoShop.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ncs.DemoShop.controller.request.CreateProductRequest;
import ru.ncs.DemoShop.service.ProductServiceImpl;
import ru.ncs.DemoShop.service.immutable.ImmutableCreateProductRequest;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static ru.ncs.DemoShop.model.ProductCategoryEnum.PERIPHERY;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ProductControllerImplTest {

    @SpyBean
    ConversionService conversionService;

    @SpyBean
    ProductServiceImpl productService;

    @Test
    void createTest() {

        UUID id = UUID.fromString("f8f35c84-dbb4-4fa2-9c75-689bbf03246e");

        CreateProductRequest reqBody = CreateProductRequest.builder()
                .amount(1)
                .category(PERIPHERY)
                .description("p")
                .name("Test")
                .price(1)
                .build();

        ImmutableCreateProductRequest immutableReq = ImmutableCreateProductRequest.builder()
                .amount(reqBody.getAmount())
                .category(reqBody.getCategory())
                .description(reqBody.getDescription())
                .name(reqBody.getName())
                .price(reqBody.getPrice())
                .build();

        Mockito.doReturn(immutableReq)
                .when(conversionService)
                .convert(reqBody, ImmutableCreateProductRequest.class);

        Mockito.doReturn(id)
                .when(productService)
                .save(immutableReq);

        given()
                .baseUri("http://localhost:7070")
                .basePath("/products")
                .contentType(ContentType.JSON)
                .when()
                .body(reqBody)
                .post()
                .then()
                .statusCode(201).log().all();
    }

    @Test
    void createTest400() {

        CreateProductRequest reqBody = CreateProductRequest.builder()
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
                .body(reqBody)
                .post()
                .then()
                .statusCode(400).log().all();
    }
}