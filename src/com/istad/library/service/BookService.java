package com.istad.library.service;

import com.istad.library.model.Book;
import com.istad.library.model.Member;

import java.util.List;

public interface BookService {
    List<Book> listBooks();
    void addNew(Book newBook);

    Book findByUUID(String uuid);
    void update(Book book);
    void delete(String uuid);
    List<Book> search(String keyword);

    void borrowBook(Member member, String bookUUID);
    void returnBook(Member member, String bookUUID);
    void renewBook(Member member, String bookUUID);
    void addFavorite(Member member, String bookUUID);
    void removeFavorite(Member member, String bookUUID);
}
