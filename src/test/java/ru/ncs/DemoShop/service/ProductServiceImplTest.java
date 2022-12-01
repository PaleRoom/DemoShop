package ru.ncs.DemoShop.service;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.core.convert.ConversionService;
import ru.ncs.DemoShop.AbstractUnitTest;
import ru.ncs.DemoShop.exception.ProductNotFoundException;
import ru.ncs.DemoShop.model.Product;
import ru.ncs.DemoShop.repository.ProductRepository;
import ru.ncs.DemoShop.service.data.ProductDTO;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest extends AbstractUnitTest {

    @Mock
    private ProductRepository productRepositoryMock;

    @Mock
    private ConversionService conversionServiceMock;

    private Product productStub;

    ProductServiceImpl underTest;

    @Override
    protected void init() {
        productStub = new Product();
        productStub.setId(UUID.randomUUID());
        productStub.setName("STUB");

        when(productRepositoryMock.findById(any())).thenReturn(Optional.of(productStub));

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
    @ValueSource(strings = {"", "  ", "   ", "\n", "\t"})
//    @MethodSource("nullEmptyBlankStrings")
    @DisplayName("Given converter returns ProductDto " +
            "when call findOneByName " +
            "then return the ProductDto")
    void givenProductDto_whenFindOneByName_thenReturnProductDto(String value) {
        //WHEN
        assertThatThrownBy(() -> underTest.findOneByName(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name must not be blank");
    }

    static Stream<String> nullEmptyBlankStrings() {
        return Stream.of(null,
                Strings.EMPTY,
                "  ",
                "\t",
                "\n");
    }
}
