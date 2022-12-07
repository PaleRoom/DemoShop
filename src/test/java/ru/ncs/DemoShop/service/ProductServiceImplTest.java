package ru.ncs.DemoShop.service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.core.convert.ConversionService;
import ru.ncs.DemoShop.AbstractUnitTest;
import ru.ncs.DemoShop.exception.ProductNotFoundException;
import ru.ncs.DemoShop.exception.ProductNotUniqueException;
import ru.ncs.DemoShop.model.Product;
import ru.ncs.DemoShop.repository.ProductRepository;
import ru.ncs.DemoShop.service.data.ProductDTO;
import ru.ncs.DemoShop.service.immutable.ImmutableUpdateProductRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static ru.ncs.DemoShop.model.ProductCategoryEnum.CPU;
import static ru.ncs.DemoShop.model.ProductCategoryEnum.PERIPHERY;

public class ProductServiceImplTest extends AbstractUnitTest {

    @Mock
    private ProductRepository productRepositoryMock;

    @Mock
    private ConversionService conversionServiceMock;

    private Product productStub;

    private ImmutableUpdateProductRequest imReqStub;
    ProductServiceImpl underTest;

    @Override
    protected void init() {
        productStub = new Product();
        productStub.setId(UUID.randomUUID());
        productStub.setName("STUB");
        productStub.setDescription("Test Description");
        productStub.setCategory(CPU);
        productStub.setPrice(1.0);
        productStub.setAmount(1);
        productStub.setAvailability(false);

        final UUID idStub = UUID.randomUUID();

        imReqStub = ImmutableUpdateProductRequest.builder()
                .name("UpdSTUB")
                .description("Test Description")
                .category(CPU)
                .price(1.0)
                .amount(1)
                .availability(false)
                .build();


        when(productRepositoryMock.findById(any())).thenReturn(Optional.of(productStub));
        when(productRepositoryMock.findIdByName(any())).thenReturn(Optional.of(idStub));

        underTest = new ProductServiceImpl(productRepositoryMock, conversionServiceMock);
    }

    //1. id передался в репо
    //GIVEN WHEN THEN
    @Test
    @DisplayName("Given Id " +
            "when call findOne " +
            "then productRepository invoked with expected Id")
    void givenId_whenFindOne_thenProductRepositoryInvokedWithTheId() {
        //GIVEN
        final UUID idStub = UUID.randomUUID();

        //WHEN
        underTest.findOne(idStub);

        //THEN
        verify(productRepositoryMock, times(1)).findById(idStub);
        verifyNoMoreInteractions(productRepositoryMock);
    }

    //2. если репо вернул пустой опшнл, то искл
    @Test
    @DisplayName("Given empty optional " +
            "when call findOne " +
            "then throw productNotFoundException")
    void givenEmptyOptional_whenFindOne_thenThrowException() {
        //GIVEN
        final UUID idStub = UUID.randomUUID();
        when(productRepositoryMock.findById(any())).thenReturn(Optional.empty());

        //WHEN
        //THEN
        assertThatThrownBy(() -> underTest.findOne(idStub))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining(idStub.toString());
    }

    //3. если репо вернул непустой опшнл, то объект из опшнл передается в конвертер
    @Test
    @DisplayName("Given optional with product " +
            "when call findOne " +
            "then converter invoked with the product")
    void givenNotEmptyOptional_whenFindOne_thenThrowException() {
        //WHEN
        //THEN
        underTest.findOne(UUID.randomUUID());

        verify(conversionServiceMock).convert(eq(productStub), eq(ProductDTO.class));
    }

    @Test
    @DisplayName("Given optional with product " +
            "when call findOne " +
            "then converter invoked with the product")
    void givenOptionalWithProduct_whenFindOne_thenThrowException() {
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
//        doReturn(ProductDTO.builder().build()).when(conversionServiceMock).convert(captor.capture(), any());
//        when(conversionServiceMock.convert(captor.capture(), any())).thenReturn(ProductDTO.builder().build());

        //WHEN
        underTest.findOne(UUID.randomUUID());

        //THEN
        verify(conversionServiceMock).convert(captor.capture(), any());

        final Product actual = captor.getValue();
        Assertions.assertEquals(actual, productStub);
    }


    //4. из findOne возвращается то, что возвращает конвертер
    @Test
    @DisplayName("Given converter returns ProductDto " +
            "when call findOne " +
            "then return the ProductDto")
    void givenProductDto_whenFindOne_thenReturnProductDto() {
        //GIVEN
        final ProductDTO productDTOStub = ProductDTO.builder().build();
        when(conversionServiceMock.convert(any(), any())).thenReturn(productDTOStub);

        //WHEN
        final ProductDTO actual = underTest.findOne(UUID.randomUUID());

        //THEN
        assertAll(() -> assertNotNull(actual),
                () -> assertThat(actual).isEqualTo(productDTOStub));
    }

    @ParameterizedTest
    // @ValueSource(strings = {"", "  ", "   ", "\n", "\t"})
    @MethodSource("nullEmptyBlankStrings")
    @DisplayName("Given converter returns not proper ProductDto " +
            "when call findOneByName " +
            "then throw Exception")
    void givenNotProperProductDto_whenFindOneByName_thenThrowException(String value) {
        //WHEN
        assertThatThrownBy(() -> underTest.findOneByName(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name must not be blank");
    }


    //todo update
    //дана цена 899.99 в ImmutableUpdateProductRequest и продукт в репо с цено 1200.50,
    //то при вызове апдейт
    //в репо засейвится продукт с ценой 899.99

    @Test
    @DisplayName("Given id "
            + "when call update "
            + "then productRepository invoked with expected id")
    void givenId_whenUpdate_thenProductRepositoryInvokedWithTheId() {
        final UUID idStub = UUID.randomUUID();
        // when(productRepositoryMock.findById(any())).thenReturn();
        when(productRepositoryMock.findIdByName(any())).thenReturn(Optional.of(idStub));
        underTest.update(imReqStub, idStub);

        verify(productRepositoryMock, times(1)).findById(idStub);
        // verifyNoMoreInteractions(productRepositoryMock);
    }

    @Test
    @DisplayName("Given empty optional " +
            "when call update " +
            "then throw productNotFoundException")
    void givenEmptyOptional_whenUpdate_thenThrowException() {
        //GIVEN
        final UUID idStub = UUID.randomUUID();
        when(productRepositoryMock.findById(any())).thenReturn(Optional.empty());

        //WHEN

        //THEN
        assertThatThrownBy(() -> underTest.update(imReqStub, idStub))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining(idStub.toString());
    }

    @Test
    @DisplayName("Given non matching id " +
            "when call check Unique " + "on Update " +
            "then throw productNotUniqueException")
    void givenNonMatchingId_whenCheckUnique_onUpdate_thenThrowException() {
        //GIVEN
        final UUID idStub = UUID.randomUUID();
        when(productRepositoryMock.findIdByName(any())).thenReturn(Optional.of(UUID.randomUUID()));

        //WHEN

        //THEN
        assertThatThrownBy(() -> underTest.update(imReqStub, idStub))

                .isInstanceOf(ProductNotUniqueException.class)
                .hasMessageContaining("Name must be unique");
    }

    @Test
    @DisplayName("Given matching id " +
            "when call check Unique " +
            "then productRepository invoked with expecting product")
    void givenMatchingId_whenCheckUnique_thenProductRepositoryInvokedWithProduct() {
        //GIVEN
        final UUID idStub = UUID.randomUUID();
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        when(productRepositoryMock.findIdByName(any())).thenReturn(Optional.of(idStub));

        //WHEN
        underTest.update(imReqStub, idStub);

        //THEN
        verify(productRepositoryMock).save(captor.capture());
    }

    @Test
    @DisplayName("Given Empty " +
            "when call check Unique " +
            "then productRepository invoked with expecting product")
    void givenEmpty_whenCheckUnique_thenProductRepositoryInvokedWithProduct() {
        //GIVEN
        final UUID idStub = UUID.randomUUID();
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        when(productRepositoryMock.findIdByName(any())).thenReturn(Optional.empty());

        //WHEN
        underTest.update(imReqStub, idStub);

        //THEN
        verify(productRepositoryMock).save(captor.capture());
    }

    @Test
    @DisplayName("Given Empty Name " +
            "when call Update " +
            "then productRepository invoked with old name product")
    void givenEmptyName_whenUpdate_thenProductRepositoryInvokedWithOldNameProduct() {
        //GIVEN
        imReqStub = ImmutableUpdateProductRequest.builder().build();
        final UUID idStub = UUID.randomUUID();
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        when(productRepositoryMock.findIdByName(any())).thenReturn(Optional.empty());

        //WHEN
        underTest.update(imReqStub, idStub);

        //THEN
        verify(productRepositoryMock).save(captor.capture());
        Product actual = captor.getValue();
        assertEquals(actual.getName(), productStub.getName());
    }

    @Test
    @DisplayName("Given no description " +
            "when call Update " +
            "then productRepository invoked with old description product")
    void givenNoDescription_whenUpdate_thenProductRepositoryInvokedWithOldDescriptionProduct() {
        //GIVEN
        imReqStub = ImmutableUpdateProductRequest.builder()
                .name("STUB")
                .category(CPU)
                .price(1.0)
                .amount(1)
                .availability(false)
                .build();

        final UUID idStub = UUID.randomUUID();
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        when(productRepositoryMock.findIdByName(any())).thenReturn(Optional.empty());

        //WHEN
        underTest.update(imReqStub, idStub);

        //THEN
        verify(productRepositoryMock).save(captor.capture());
        Product actual = captor.getValue();
        assertEquals(actual.getDescription(), productStub.getDescription());
    }

    @Test
    @DisplayName("Given new description " +
            "when call Update " +
            "then productRepository invoked with new description product")
    void givenNewDescription_whenUpdate_thenProductRepositoryInvokedWithNewDescriptionProduct() {
        //GIVEN
        imReqStub = ImmutableUpdateProductRequest.builder()
                .name("STUB")
                .description("Test Description")
                .category(CPU)
                .price(1.0)
                .amount(1)
                .availability(false)
                .build();

        final UUID idStub = UUID.randomUUID();
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        when(productRepositoryMock.findIdByName(any())).thenReturn(Optional.empty());

        //WHEN
        underTest.update(imReqStub, idStub);

        //THEN
        verify(productRepositoryMock).save(captor.capture());
        Product actual = captor.getValue();
        assertEquals(actual.getDescription(), imReqStub.getDescription());
    }

    @Test
    @DisplayName("Given no category " +
            "when call Update " +
            "then productRepository invoked with old category product")
    void givenNoCategory_whenUpdate_thenProductRepositoryInvokedWithOldCategoryProduct() {
        //GIVEN
        imReqStub = ImmutableUpdateProductRequest.builder()
                .name("STUB")
                .description("Test Description")
                .price(1.0)
                .amount(1)
                .availability(false)
                .build();

        final UUID idStub = UUID.randomUUID();
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        when(productRepositoryMock.findIdByName(any())).thenReturn(Optional.empty());

        //WHEN
        underTest.update(imReqStub, idStub);

        //THEN
        verify(productRepositoryMock).save(captor.capture());
        Product actual = captor.getValue();
        assertEquals(actual.getCategory(), productStub.getCategory());
    }

    @Test
    @DisplayName("Given new category " +
            "when call Update " +
            "then productRepository invoked with new description product")
    void givenNewCategory_whenUpdate_thenProductRepositoryInvokedWithCategoryDescriptionProduct() {
        //GIVEN
        imReqStub = ImmutableUpdateProductRequest.builder()
                .name("STUB")
                .description("Test Description")
                .category(PERIPHERY)
                .price(1.0)
                .amount(1)
                .availability(false)
                .build();

        final UUID idStub = UUID.randomUUID();
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        when(productRepositoryMock.findIdByName(any())).thenReturn(Optional.empty());

        //WHEN
        underTest.update(imReqStub, idStub);

        //THEN
        verify(productRepositoryMock).save(captor.capture());
        Product actual = captor.getValue();
        assertEquals(actual.getCategory(), imReqStub.getCategory());
    }

    @Test
    @DisplayName("Given no price " +
            "when call Update " +
            "then productRepository invoked with old price product")
    void givenNoPrice_whenUpdate_thenProductRepositoryInvokedWithPriceDescriptionProduct() {
        //GIVEN
        imReqStub = ImmutableUpdateProductRequest.builder()
                .name("STUB")
                .description("Test Description")
                .category(CPU)
                .amount(1)
                .availability(false)
                .build();

        final UUID idStub = UUID.randomUUID();
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        when(productRepositoryMock.findIdByName(any())).thenReturn(Optional.empty());

        //WHEN
        underTest.update(imReqStub, idStub);

        //THEN
        verify(productRepositoryMock).save(captor.capture());
        Product actual = captor.getValue();
        assertEquals(actual.getPrice(), productStub.getPrice());
    }

    @Test
    @DisplayName("Given new price " +
            "when call Update " +
            "then productRepository invoked with new price product")
    void givenNewPrice_whenUpdate_thenProductRepositoryInvokedWithNewPriceProduct() {
        //GIVEN
        imReqStub = ImmutableUpdateProductRequest.builder()
                .name("STUB")
                .description("Test Description")
                .category(CPU)
                .price(2.0)
                .amount(1)
                .availability(false)
                .build();

        final UUID idStub = UUID.randomUUID();
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        when(productRepositoryMock.findIdByName(any())).thenReturn(Optional.empty());

        //WHEN
        underTest.update(imReqStub, idStub);

        //THEN
        verify(productRepositoryMock).save(captor.capture());
        Product actual = captor.getValue();
        assertEquals(actual.getPrice(), imReqStub.getPrice());
    }

    @Test
    @DisplayName("Given no amount " +
            "when call Update " +
            "then productRepository invoked with old amount product")
    void givenNoAmount_whenUpdate_thenProductRepositoryInvokedWithOldAmountProduct() {
        //GIVEN
        imReqStub = ImmutableUpdateProductRequest.builder()
                .name("STUB")
                .description("Test Description")
                .category(CPU)
                .price(1.0)
                .availability(false)
                .build();

        final UUID idStub = UUID.randomUUID();
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        when(productRepositoryMock.findIdByName(any())).thenReturn(Optional.empty());

        //WHEN
        underTest.update(imReqStub, idStub);

        //THEN
        verify(productRepositoryMock).save(captor.capture());
        Product actual = captor.getValue();
        assertEquals(actual.getAmount(), productStub.getAmount());
    }

    @Test
    @DisplayName("Given new Amount " +
            "when call Update " +
            "then productRepository invoked with new Amount product")
    void givenNewAmount_whenUpdate_thenProductRepositoryInvokedWithNewAmountProduct() {
        //GIVEN
        imReqStub = ImmutableUpdateProductRequest.builder()
                .name("STUB")
                .description("Test Description")
                .category(CPU)
                .price(1.0)
                .amount(2)
                .availability(false)
                .build();

        final UUID idStub = UUID.randomUUID();
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        when(productRepositoryMock.findIdByName(any())).thenReturn(Optional.empty());

        //WHEN
        underTest.update(imReqStub, idStub);

        //THEN
        verify(productRepositoryMock).save(captor.capture());
        Product actual = captor.getValue();
        assertEquals(actual.getAmount(), imReqStub.getAmount());
    }

    @Test
    @DisplayName("Given no Availability " +
            "when call Update " +
            "then productRepository invoked with old availability product")
    void givenNoAvailability_whenUpdate_thenProductRepositoryInvokedWithOldAvailabilityProduct() {
        //GIVEN
        imReqStub = ImmutableUpdateProductRequest.builder()
                .name("STUB")
                .description("Test Description")
                .category(CPU)
                .price(1.0)
                .amount(1)
                .build();

        final UUID idStub = UUID.randomUUID();
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        when(productRepositoryMock.findIdByName(any())).thenReturn(Optional.empty());

        //WHEN
        underTest.update(imReqStub, idStub);

        //THEN
        verify(productRepositoryMock).save(captor.capture());
        Product actual = captor.getValue();
        assertEquals(actual.isAvailability(), productStub.isAvailability());
    }

    @Test
    @DisplayName("Given new Availability " +
            "when call Update " +
            "then productRepository invoked with new Availability product")
    void givenNewAvailability_whenUpdate_thenProductRepositoryInvokedWithNewAvailabilityProduct() {
        //GIVEN
        imReqStub = ImmutableUpdateProductRequest.builder()
                .name("STUB")
                .description("Test Description")
                .category(CPU)
                .price(1.0)
                .amount(1)
                .availability(true)
                .build();

        final UUID idStub = UUID.randomUUID();
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        when(productRepositoryMock.findIdByName(any())).thenReturn(Optional.empty());

        //WHEN
        underTest.update(imReqStub, idStub);

        //THEN
        verify(productRepositoryMock).save(captor.capture());
        Product actual = captor.getValue();
        assertEquals(actual.isAvailability(), imReqStub.getAvailability());
    }

    @Test
    @DisplayName("Given product " +
            "when call Update " +
            "then converter invoked with expecting product")
    void givenProduct_whenUpdate_thenProductRepositoryInvokedWithNewAvailabilityProduct() {
        //GIVEN
        imReqStub = ImmutableUpdateProductRequest.builder()
                .name("STUB")
                .build();

        final UUID idStub = UUID.randomUUID();
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        when(productRepositoryMock.findIdByName(any())).thenReturn(Optional.empty());

        //WHEN
        underTest.update(imReqStub, idStub);

        //THEN
        verify(conversionServiceMock).convert(captor.capture(), any());

    }
    @Test
    @DisplayName("Given converter returns ProductDto " +
            "when call update " +
            "then return the ProductDto")
    void givenProductDto_whenUpdate_thenReturnProductDto() {
        //GIVEN
        final UUID idStub = UUID.randomUUID();
        final ProductDTO productDTOStub = ProductDTO.builder().build();
        when(productRepositoryMock.findIdByName(any())).thenReturn(Optional.empty());
        when(conversionServiceMock.convert(any(), any())).thenReturn(productDTOStub);

        //WHEN
        final ProductDTO actual = underTest.update(imReqStub, idStub);

        //THEN
        assertAll(() -> assertNotNull(actual),
                () -> assertThat(actual).isEqualTo(productDTOStub));
    }
    static Stream<String> nullEmptyBlankStrings() {
        return Stream.of(null,
                Strings.EMPTY,
                "  ",
                "\t",
                "\n");
    }
}
