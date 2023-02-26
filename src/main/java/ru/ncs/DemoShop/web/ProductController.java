package ru.ncs.DemoShop.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.ncs.DemoShop.web.request.productRequest.CreateProductRequest;
import ru.ncs.DemoShop.web.request.productRequest.SearchProductRequest;
import ru.ncs.DemoShop.web.request.productRequest.UpdateProductRequest;
import ru.ncs.DemoShop.web.response.GetProductResponse;

/**
 * Контроллер для выполнения операций над товарами
 */
@RequestMapping("/products")
@Tag(name = "Product service", description = "the Product API with description tag annotation")
public interface ProductController {
    /**
     * Метод, возвращающий все товары из БД
     *
     * @return список продуктов
     */
    @GetMapping
    List<GetProductResponse> getProducts();

    /**
     * Метод, возвращающий один товар
     *
     * @param id ID возвращаемого товара
     * @return GetProductResponse возвращает сущность-ответ содержащую товар
     */
    @GetMapping("/{id}")
    GetProductResponse getOneProduct(@PathVariable("id") UUID id);

    /**
     * Метод, создающий новый товар
     *
     * @param createProductRequest параметр содержащий сущность-запрос с данными полей создаваемого товара
     * @return UUID - возвращает ID созданного товара
     */
    @PostMapping
    UUID create(@RequestBody @Valid CreateProductRequest createProductRequest);

    /**
     * Метод для обновления данных товара
     *
     * @param id                   - ID обновляемого товара
     * @param updateProductRequest параметр, содержащий сущность-запрос с данными полей для обновления товара
     * @return UUID - возвращает ID обновленного товара
     */
    @PutMapping("/{id}")
    UUID updateProduct(@PathVariable("id") UUID id,
                       @RequestBody @Valid UpdateProductRequest updateProductRequest);

    /**
     * метод, удаляющий товар
     *
     * @param id - ID удаляемого товара
     */
    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable("id") UUID id);

    /**
     * Метод поиска товара по заданным критериям:
     * - содержит часть названия
     * - c ценой не более, чем в запросе
     * - в количестве на складе не менее, чем в запросе
     * - товар доступен для заказа
     *
     * @param searchProductRequest параметр, содержащий сущность-запрос для поиска товара по критериям
     * @return возвращает список товаров, удовлетворяющих критериям
     */
    @PostMapping("/search")
    List<GetProductResponse> searchProducts(SearchProductRequest searchProductRequest);


    @PostMapping(value= "/csv", consumes = "multipart/form-data")
    void addFromCSV(@RequestParam("file") MultipartFile csvFile);
}
