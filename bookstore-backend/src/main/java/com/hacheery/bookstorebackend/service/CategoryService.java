package com.hacheery.bookstorebackend.service;

import com.hacheery.bookstorebackend.entity.Category;
import com.hacheery.bookstorebackend.payload.request.CategoryRequest;
import org.springframework.data.domain.Page;

public interface CategoryService {
    Page<Category> getCategories(CategoryRequest request);

    Category getCategoryById(Long id);

    Category addCategory(Category category);

    Category updateCategory(Long id, Category category);

    void deleteCategory(Long id);
}
