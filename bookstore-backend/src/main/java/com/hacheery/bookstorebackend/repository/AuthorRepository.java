package com.hacheery.bookstorebackend.repository;

import com.hacheery.bookstorebackend.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findAuthorByAuthorName(String name);
}
