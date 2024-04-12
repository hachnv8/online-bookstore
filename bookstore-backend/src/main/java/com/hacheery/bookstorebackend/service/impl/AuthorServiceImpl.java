package com.hacheery.bookstorebackend.service.impl;

import com.hacheery.bookstorebackend.entity.Author;
import com.hacheery.bookstorebackend.entity.Book;
import com.hacheery.bookstorebackend.exception.ResourceNotFoundException;
import com.hacheery.bookstorebackend.repository.AuthorRepository;
import com.hacheery.bookstorebackend.repository.BookRepository;
import com.hacheery.bookstorebackend.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found with id :" + id));
    }

    @Override
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(Long id, Author authorDetails) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found with id :" + id));
        author.setAuthorName(authorDetails.getAuthorName());
        author.setDescription(authorDetails.getDescription());
        author.setImage(authorDetails.getImage());
        author.setWebsite(authorDetails.getWebsite());
        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public List<Author> searchAuthorsByName(String name) {
        return authorRepository.findAuthorByAuthorName(name);
    }

    @Override
    public List<Book> getBooksByAuthor(Long authorId) {
        return bookRepository.findBookByAuthorId(authorId);
    }

    @Override
    public List<Author> getAuthorsByCategory(Long categoryId) {
        List<Book> books = bookRepository.findBookByCategoryId(categoryId);
        List<Author> authors = new ArrayList<>();
        for (Book book : books) {
            Optional<Author> author = authorRepository.findById(book.getAuthorId());
            author.ifPresent(authors::add);
        }
        return authors;
    }
}
