package com.hacheery.bookstorebackend.service.impl;

import com.hacheery.bookstorebackend.entity.Book;
import com.hacheery.bookstorebackend.exception.ResourceNotFoundException;
import com.hacheery.bookstorebackend.payload.request.BookRequest;
import com.hacheery.bookstorebackend.repository.BookRepository;
import com.hacheery.bookstorebackend.service.BookService;
import com.hacheery.bookstorebackend.specification.BookSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public Page<Book> getBooks(BookRequest bookRequest, Pageable pageable) {
        Specification<Book> spec = BookSpecification.searchByParameter(
                bookRequest
        );
        return bookRepository.findAll(spec, pageable);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with id :" + id));
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with id :" + id));
        book.setTitle(bookDetails.getTitle());
        book.setDescription(bookDetails.getDescription());
        book.setImage(bookDetails.getImage());
        book.setPrice(bookDetails.getPrice());
        book.setCategoryId(bookDetails.getCategoryId());
        book.setAuthorId(bookDetails.getAuthorId());
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchBooksByName(String name) {
        return bookRepository.findBookByTitle(name);
    }

    public List<Book> getBooksByAuthor(Long authorId) {
        return bookRepository.findBookByAuthorId(authorId);
    }

    public List<Book> getBooksByCategory(Long categoryId) {
        return bookRepository.findBookByCategoryId(categoryId);
    }

    public List<Book> getBestSellingBooks() {
        return new ArrayList<>();
    }

    public List<Book> getLatestBooks() {
        return new ArrayList<>();
    }

    public List<Book> getOnSaleBooks() {
        return new ArrayList<>();
    }
}
