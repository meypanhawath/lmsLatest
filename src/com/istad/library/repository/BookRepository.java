package com.istad.library.repository;

import com.istad.library.model.Book;

import java.util.List;

public interface BookRepository {

    List<Book> findAll();
    void insert(Book newBook);
    void update(Book updatedBook);
    void delete(String uuid);
    Book findByUUID(String uuid);
    List<Book> searchByTitle(String title);
    List<Book> searchByAuthor(String author);
    List<Book> searchByPublishedYear(int year);
}
