package com.dailycodework.dreamshops.service.product;

import com.dailycodework.dreamshops.dto.ProductDto;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.request.AddProductRequest;
import com.dailycodework.dreamshops.request.UpdateProductRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(UpdateProductRequest request, Long productId);
    List<Product> getAllProducts();

    List<ProductDto> getConvertedProducts(List<Product> products);

    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);

    ProductDto convertToDto(Product product);
}
