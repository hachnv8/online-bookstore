package com.hacheery.bookstorebackend.specification;

import com.hacheery.bookstorebackend.entity.Category;
import com.hacheery.bookstorebackend.payload.request.CategoryRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecification {
    public static Specification<Category> searchByParameter(CategoryRequest categoryRequest) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if(shouldIncludeCategoryName(categoryRequest.getCategoryName())) {
                predicate = cb.and(predicate, cb.like(root.get("categoryName"), "%"
                + categoryRequest.getCategoryName() + "%"));
            }

            return predicate;
        };
    }

    private static boolean shouldIncludeCategoryName(String catName) {
        return catName != null && !catName.isEmpty();
    }

}
