package com.project.bookstorebackend.repository;

import com.project.bookstorebackend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
