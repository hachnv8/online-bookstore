package com.hacheery.bookstorebackend.repository;

import com.hacheery.bookstorebackend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    boolean existsByTitle(String title);
    List<Book> findBookByCategoryId(Long categoryId);
    List<Book> findBookByAuthorId(Long authorId);
    List<Book> findBookByTitle(String title);
}
