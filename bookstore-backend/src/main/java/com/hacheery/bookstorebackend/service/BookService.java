package com.hacheery.bookstorebackend.service;

import com.hacheery.bookstorebackend.entity.Book;
import com.hacheery.bookstorebackend.payload.request.BookRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    Page<Book> getBooks(BookRequest bookRequest, Pageable pageable);

    Book getBookById(Long id);

    Book createBook(Book book);

    Book updateBook(Long id, Book book);

    void deleteBook(Long id);

    List<Book> searchBooksByName(String name);

    List<Book> getBooksByAuthor(Long authorId);

    List<Book> getBooksByCategory(Long categoryId);

    List<Book> getBestSellingBooks();

    List<Book> getLatestBooks();

    List<Book> getOnSaleBooks();
}
