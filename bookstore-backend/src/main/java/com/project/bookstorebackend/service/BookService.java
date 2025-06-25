package com.project.bookstorebackend.service;

import com.project.bookstorebackend.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book createBook(Book book);
    Optional<Book> getBookById(Long id);
    List<Book> getAllBooks();
    Book updateBook(Long id, Book bookDetails);
    boolean deleteBook(Long id);
}
