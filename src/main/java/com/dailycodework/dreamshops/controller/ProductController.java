package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.dto.ProductDto;
import com.dailycodework.dreamshops.exception.ProductNotFoundException;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.request.AddProductRequest;
import com.dailycodework.dreamshops.request.UpdateProductRequest;
import com.dailycodework.dreamshops.response.ApiResponse;
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
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedproducts = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("success", convertedproducts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            ProductDto convertedProduct = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Found", convertedProduct));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product addedProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("success", addedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest product, @PathVariable Long id) {
        try {
            Product updatedProduct = productService.updateProduct(product, id);
            return ResponseEntity.ok(new ApiResponse("success", updatedProduct));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("success", null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not found", null));
        }
    }

    @GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brand, @RequestParam @Nullable String name) {
        if (name == null) {
            try {
                List<Product> products = productService.getProductsByBrand(brand);
                List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
                if (products.isEmpty()) {
                    return ResponseEntity.ok(new ApiResponse("No product found", null));
                }
                return ResponseEntity.ok(new ApiResponse("Success", convertedProducts));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
            }
        } else {
            try {
                List<Product> products = productService.getProductsByBrandAndName(brand, name);
                List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
                if (products.isEmpty()) {
                    return ResponseEntity.ok(new ApiResponse("No product found", null));
                }
                return ResponseEntity.ok(new ApiResponse("Found", convertedProducts));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
            }
        }

    }

    @GetMapping("/by/brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            if (products.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse("No product found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Found", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
