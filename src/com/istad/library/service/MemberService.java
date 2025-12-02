package com.istad.library.service;

import com.istad.library.model.Book;
import com.istad.library.model.Member;

import java.util.List;

public interface MemberService {
    List<Member> listMembers();
    void addNew(Member newMember);
    void update(Member member);
    void delete(String uuid);
    Member findByUsername(String username);
    Member findByUUID(String uuid);
    List<Member> searchByName(String name);

    // New methods for member actions
    void borrowBook(Member member, Book book);
    void renewBook(Member member, Book book);
    void returnBook(Member member, Book book);
    void favoriteBook(Member member, Book book);
    void unfavoriteBook(Member member, Book book);
}
