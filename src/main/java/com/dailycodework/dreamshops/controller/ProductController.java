package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.dto.ProductDto;
import com.dailycodework.dreamshops.exception.ProductNotFoundException;
import com.dailycodework.dreamshops.exception.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.request.AddProductRequest;
import com.dailycodework.dreamshops.request.UpdateProductRequest;
import com.dailycodework.dreamshops.response.ApiResonse;
import com.dailycodework.dreamshops.service.product.IProductService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResonse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedproducts = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResonse("success", convertedproducts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResonse> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            ProductDto convertedProduct = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResonse("Found", convertedProduct));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResonse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResonse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product addedProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResonse("success", addedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResonse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResonse> updateProduct(@RequestBody UpdateProductRequest product, @PathVariable Long id) {
        try {
            Product updatedProduct = productService.updateProduct(product, id);
            return ResponseEntity.ok(new ApiResonse("success", updatedProduct));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResonse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResonse> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResonse("success", null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResonse("Not found", null));
        }
    }

    @GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResonse> getProductByBrandAndName(@RequestParam String brand, @RequestParam @Nullable String name) {
        if (name == null) {
            try {
                List<Product> products = productService.getProductsByBrand(brand);
                List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
                if (products.isEmpty()) {
                    return ResponseEntity.ok(new ApiResonse("No product found", null));
                }
                return ResponseEntity.ok(new ApiResonse("Success", convertedProducts));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResonse(e.getMessage(), null));
            }
        } else {
            try {
                List<Product> products = productService.getProductsByBrandAndName(brand, name);
                List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
                if (products.isEmpty()) {
                    return ResponseEntity.ok(new ApiResonse("No product found", null));
                }
                return ResponseEntity.ok(new ApiResonse("Found", convertedProducts));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResonse(e.getMessage(), null));
            }
        }

    }

    @GetMapping("/by/brand")
    public ResponseEntity<ApiResonse> getProductByBrand(@RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            if (products.isEmpty()) {
                return ResponseEntity.ok(new ApiResonse("No product found", null));
            }
            return ResponseEntity.ok(new ApiResonse("Found", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResonse(e.getMessage(), null));
        }
    }

}
