package com.istad.library.repository.impl;

import com.istad.library.model.Book;
import com.istad.library.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookRepositoryImpl implements BookRepository {

    private static final List<Book> bookList = new ArrayList<>();

    public BookRepositoryImpl() {
        if (bookList.isEmpty()) {
            Book b1 = new Book(UUID.randomUUID().toString(), "Clean Code", "Robert Martin", "978-01323", 2008, true);
            Book b2 = new Book(UUID.randomUUID().toString(), "Java Programming", "James Gosling", "978-09999", 2020, false);
            Book b3 = new Book(UUID.randomUUID().toString(), "Java Programming", "James Gosling", "978-09999", 2020, false);
            Book b4 = new Book(UUID.randomUUID().toString(), "Java Programming", "James Gosling", "978-09999", 2020, false);
            Book b5 = new Book(UUID.randomUUID().toString(), "Java Programming", "James Gosling", "978-09999", 2020, false);
            Book b6 = new Book(UUID.randomUUID().toString(), "Java Programming", "James Gosling", "978-09999", 2020, false);
            Book b7 = new Book(UUID.randomUUID().toString(), "Java Programming", "James Gosling", "978-09999", 2020, false);
            Book b8 = new Book(UUID.randomUUID().toString(), "Java Programming", "James Gosling", "978-09999", 2020, false);

            bookList.add(b1);
            bookList.add(b2);
            bookList.add(b3);
            bookList.add(b4);
            bookList.add(b5);
            bookList.add(b6);
            bookList.add(b7);
            bookList.add(b8);
        }
    }

    @Override
    public List<Book> findAll() {
        return bookList;
    }

    @Override
    public void insert(Book newBook) {
        newBook.setUUID(UUID.randomUUID().toString());
        bookList.add(newBook);
    }

    @Override
    public void update(Book updatedBook) {
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getUUID().equals(updatedBook.getUUID())) {
                bookList.set(i, updatedBook);
                break;
            }
        }
    }

    @Override
    public void delete(String uuid) {
        bookList.removeIf(book -> book.getUUID().equals(uuid));
    }

    @Override
    public Book findByUUID(String uuid) {
        for (Book book : bookList) {
            if (book.getUUID().equals(uuid)) return book;
        }
        return null;
    }

    @Override
    public List<Book> searchByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public List<Book> searchByPublishedYear(int year) {
        List<Book> result = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getPublishedYear() == year) {
                result.add(book);
            }
        }
        return result;
    }
}
