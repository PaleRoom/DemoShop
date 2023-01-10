package ru.ncs.DemoShop.service.product;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import ru.ncs.DemoShop.service.product.data.ProductDTO;
import ru.ncs.DemoShop.service.product.immutable.ImmutableCreateProductRequest;
import ru.ncs.DemoShop.service.product.immutable.ImmutableSearchProductRequest;
import ru.ncs.DemoShop.service.product.immutable.ImmutableUpdateProductRequest;

@Service
public interface ProductService {
    /**
     * Метод для поиска товара по ID
     *
     * @param id ID, передаваемый для поиска товара
     * @return ProductDTO найденный товар товар, возвращаемый методом
     */
    ProductDTO findOne(UUID id);

    /**
     * Метод для поиска ID товара по имени
     *
     * @param name - имя товара, ID которого следует вернуть
     * @return UUID - ID найденного товара, возвращаемый методом
     */
    UUID findIdByName(String name);

    /**
     * Метод для выдачи всех товаров из БД
     *
     * @return возвращает список товаров из БД
     */
    List<ProductDTO> findAll();

    /**
     * Метод для сохранения в БД нового товара
     *
     * @param immutableCreateProductRequest параметр с сущностью товара для передачи в метод
     * @return UUID - возращает ID созданного товара
     */
    UUID save(ImmutableCreateProductRequest immutableCreateProductRequest);

    /**
     * Метод для обновления данных товара в БД
     *
     * @param request параметр с сущностью-запросом для обновления данных товара
     * @param id      ID обновляемого товара
     * @return UUID - возращает ID обновленного товара
     */
    ProductDTO update(ImmutableUpdateProductRequest request, UUID id);

    /**
     * Метод, удаляющий товар из БД
     *
     * @param id - ID удаляемого товара
     */
    void delete(UUID id);

    /**
     * Метод увеличивающий цену всех товаров в определенный промежуток времени с заданным коэффициентом
     *
     * @param mod множитель-модификатор для расчета новой цены
     * @throws InterruptedException выбрасывается при прерывании потока
     */
    void increasePrice(double mod) throws InterruptedException;

    /**
     * Метод поиска товара по заданным критериям:
     * - содержит часть названия
     * - c ценой не более, чем в запросе
     * - в количестве на складе не менее, чем в запросе
     * - товар доступен для заказа
     *
     * @param request параметр с сущностью-запросом для поиска товара по критериям
     * @return возвращает список товаров, соответствующих критериям запроса
     * также результаты поиска сохраняются в файлы pdf и xls форматов
     */
    List<ProductDTO> searchProducts(ImmutableSearchProductRequest request);
}
