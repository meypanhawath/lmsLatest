package com.istad.library.service.impl;

import com.istad.library.model.Book;
import com.istad.library.model.Member;
import com.istad.library.repository.BookRepository;
import com.istad.library.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> listBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void addNew(Book newBook) {
        newBook.setIsAvailable(true);
        bookRepository.insert(newBook);
    }

    @Override
    public void update(Book updatedBook) {
        bookRepository.update(updatedBook);
    }

    @Override
    public void delete(String uuid) {
        Book book = findByUUID(uuid);
        if (book != null) {
            bookRepository.findAll().remove(book);
        }
    }

    @Override
    public Book findByUUID(String uuid) {
        return bookRepository.findAll()
                .stream()
                .filter(b -> b.getUUID().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Book> search(String keyword) {
        String lower = keyword.toLowerCase();
        return bookRepository.findAll()
                .stream()
                .filter(b -> b.getTitle().toLowerCase().contains(lower)
                        || b.getAuthor().toLowerCase().contains(lower)
                        || String.valueOf(b.getPublishedYear()).contains(lower))
                .collect(Collectors.toList());
    }

    @Override
    public void borrowBook(Member member, String bookUUID) {
        Book book = findByUUID(bookUUID);
        if (book != null && book.getIsAvailable()) {
            book.setIsAvailable(false);
            member.getBorrowedBooks().add(book);
        }
    }

    @Override
    public void returnBook(Member member, String bookUUID) {
        Book book = findByUUID(bookUUID);
        if (book != null && member.getBorrowedBooks().contains(book)) {
            book.setIsAvailable(true);
            member.getBorrowedBooks().remove(book);
        }
    }

    @Override
    public void renewBook(Member member, String bookUUID) {
        Book book = findByUUID(bookUUID);
        if (book != null && member.getBorrowedBooks().contains(book)) {
            // For simplicity, just print message, you can add a due date if needed
        }
    }

    @Override
    public void addFavorite(Member member, String bookUUID) {
        Book book = findByUUID(bookUUID);
        if (book != null && !member.getFavoriteBooks().contains(book)) {
            member.getFavoriteBooks().add(book);
        }
    }

    @Override
    public void removeFavorite(Member member, String bookUUID) {
        Book book = findByUUID(bookUUID);
        if (book != null && member.getFavoriteBooks().contains(book)) {
            member.getFavoriteBooks().remove(book);
        }
    }


}
