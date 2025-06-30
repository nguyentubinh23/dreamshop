package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.exception.AlreadyExistsException;
import com.dailycodework.dreamshops.exception.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Category;
import com.dailycodework.dreamshops.response.ApiResonse;
import com.dailycodework.dreamshops.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResonse> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResonse("Found!", categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResonse("Error", null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResonse> addCategory(@RequestBody Category name) {
        try {
            Category category = categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResonse("Success", category));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResonse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<ApiResonse> getCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResonse("Found", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResonse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<ApiResonse> getCategoryByName(@PathVariable String name) {
        try {
            Category category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResonse("Found", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResonse(e.getMessage(), null));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResonse> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResonse("Delete success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResonse(e.getMessage(), null));
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResonse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            Category updatedCategory = categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new ApiResonse("Update success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResonse(e.getMessage(), null));
        }
    }
}
