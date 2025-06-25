package com.project.bookstorebackend.service.impl;

import com.project.bookstorebackend.entity.Book;
import com.project.bookstorebackend.repository.BookRepository;
import com.project.bookstorebackend.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    // Create
    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    // Read (with caching)
    @Cacheable(value = "books", key = "#id")
    public Optional<Book> getBookById(Long id) {
        System.out.println("Fetching book from database for ID: " + id); // Log để kiểm tra cache hit/miss
        return bookRepository.findById(id);
    }

    @Cacheable(value = "books")
    public List<Book> getAllBooks() {
        System.out.println("Fetching all books from database."); // Log để kiểm tra cache hit/miss
        return bookRepository.findAll();
    }

    // Update (with cache update)
    @CachePut(value = "books", key = "#book.id")
    public Book updateBook(Long id, Book bookDetails) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(bookDetails.getTitle());
                    book.setAuthor(bookDetails.getAuthor());
                    book.setIsbn(bookDetails.getIsbn());
                    book.setPrice(bookDetails.getPrice());
                    book.setPublicationDate(bookDetails.getPublicationDate());
                    book.setStock(bookDetails.getStock());
                    return bookRepository.save(book);
                }).orElse(null); // Hoặc ném một ngoại lệ nếu sách không tồn tại
    }

    // Delete (with cache eviction)
    @CacheEvict(value = "books", key = "#id")
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
