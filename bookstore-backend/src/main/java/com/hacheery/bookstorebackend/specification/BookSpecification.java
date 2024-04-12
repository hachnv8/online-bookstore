package com.hacheery.bookstorebackend.specification;

import com.hacheery.bookstorebackend.entity.Book;
import com.hacheery.bookstorebackend.payload.request.BookRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
    public static Specification<Book> searchByParameter(BookRequest bookRequest) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if(shouldIncludeBookName(bookRequest.getTitle())) {
                predicate = cb.and(predicate, cb.like(root.get("title"), "%"
                + bookRequest.getTitle() + "%"));
            }
            return predicate;
        };
    }

    private static boolean shouldIncludeBookName(String catName) {
        return catName != null && !catName.isEmpty();
    }
}
