package ru.ncs.DemoShop.web;

import io.restassured.http.ContentType;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.convert.ConversionService;
import ru.ncs.DemoShop.AbstractSpringBootTest;
import ru.ncs.DemoShop.web.request.productRequest.CreateProductRequest;
import ru.ncs.DemoShop.service.product.ProductServiceImpl;
import ru.ncs.DemoShop.service.product.immutable.ImmutableCreateProductRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.ncs.DemoShop.model.ProductCategoryEnum.PERIPHERY;


class ProductControllerImplTest
        extends AbstractSpringBootTest {
    @SpyBean
    ConversionService conversionService;

    @MockBean
    ProductServiceImpl productService;

    private ImmutableCreateProductRequest imReqStub;
    private CreateProductRequest reqBodyStub;
    private UUID idStub;

    @Override
    protected void init() {

        reqBodyStub = CreateProductRequest.builder()
                .amount(1)
                .category(PERIPHERY)
                .description("p")
                .name("Test")
                .price(1)
                .availability(true)
                .build();

        imReqStub = ImmutableCreateProductRequest.builder()
                .amount(reqBodyStub.getAmount())
                .category(reqBodyStub.getCategory())
                .description(reqBodyStub.getDescription())
                .name(reqBodyStub.getName())
                .price(reqBodyStub.getPrice())
                .availability(reqBodyStub.isAvailability())
                .build();

        idStub = UUID.randomUUID();

        when(conversionService.convert(reqBodyStub, ImmutableCreateProductRequest.class)).
                thenReturn(imReqStub);
        when(productService.save(any())).thenReturn(idStub);
    }

    @Test
    @DisplayName("Given valid request body " +
            "when call Create " +
            "then Controller returns Status code 201")
    void givenValidRequestBody_thenControllerReturnsStatus201() {
        UUID idResponse = given()
                .baseUri("http://localhost:" + port)
                .basePath("/products")
                .contentType(ContentType.JSON)
                .when()
                .body(reqBodyStub)
                .post()
                .then()
                .statusCode(201).log().all()
                .extract()
                .as(UUID.class);
        Assertions.assertEquals(idResponse, idStub);
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
                .baseUri("http://localhost:" + port)
                .basePath("/products")
                .contentType(ContentType.JSON)
                .when()
                .body(reqBodyStub)
                .post()
                .then()
                .body("message", containsString("product name should  not be Empty"))
                .statusCode(400).log().all();
    }
}