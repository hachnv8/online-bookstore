package com.hacheery.bookstorebackend.service;

import com.hacheery.bookstorebackend.entity.Author;
import com.hacheery.bookstorebackend.entity.Book;

import java.util.List;

public interface AuthorService {
    List<Author> getAuthors();

    Author getAuthorById(Long id);

    Author createAuthor(Author author);

    Author updateAuthor(Long id, Author author);

    void deleteAuthor(Long id);

    List<Author> searchAuthorsByName(String name);

    List<Book> getBooksByAuthor(Long authorId);

    List<Author> getAuthorsByCategory(Long categoryId);
}
