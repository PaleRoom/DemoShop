package ru.ncs.DemoShop.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.ncs.DemoShop.DTO.ProductDTO;
import ru.ncs.DemoShop.models.Product;
import ru.ncs.DemoShop.services.ProductErrorResponse;
import ru.ncs.DemoShop.services.ProductService;
import ru.ncs.DemoShop.util.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductValidator productValidator;
    private final ProductUpdateValidator productUpdateValidator;

    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService,
                             ProductValidator productValidator,
                             ProductUpdateValidator productUpdateValidator, ModelMapper modelMapper) {
        this.productService = productService;
        this.productValidator = productValidator;
        this.productUpdateValidator = productUpdateValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/show")
    public List<ProductDTO> getProducts() {
        return productService.findAll().stream().map(this::convertToProductDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable("id") UUID id) {
        return convertToProductDTO(productService.findOne(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid ProductDTO productDTO, BindingResult bindingResult) {

        productValidator.validate(convertToProduct(productDTO), bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new ProductNotCreatedException(errorMsg.toString());
        }
        productService.save(convertToProduct(productDTO));
        return new ResponseEntity<>(productService.findOneByName(productDTO.getName()).getId(),
                HttpStatus.OK);
        //return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<HttpStatus> updateProduct(@PathVariable("id") UUID id, @RequestBody @Valid ProductDTO productDTO, BindingResult bindingResult) {

        productUpdateValidator.validate(convertToProduct(productDTO), bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new ProductNotUpdatedException(errorMsg.toString());
        }
        productService.update(convertToProduct(productDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") UUID id) {
        if (productService.findOne(id) == null) {
            throw new ProductNotFoundException();
        }
        productService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<ProductErrorResponse> handleException(ProductNotCreatedException e) {
        ProductErrorResponse response = new ProductErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 401
    }

    @ExceptionHandler
    private ResponseEntity<ProductErrorResponse> handleException(ProductNotFoundException e) {
        ProductErrorResponse response = new ProductErrorResponse(
                "Product with this ID not found!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404
    }

    @ExceptionHandler
    private ResponseEntity<ProductErrorResponse> handleException(ProductNotUpdatedException e) {
        ProductErrorResponse response = new ProductErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 401
    }

    private Product convertToProduct(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    private ProductDTO convertToProductDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }


}
