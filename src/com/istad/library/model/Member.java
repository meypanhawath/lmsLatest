package com.istad.library.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Member {
    private String UUID;
    private String username;
    private String password;
    private String name;
    private String email;
    private String contactNumber;

    private List<com.istad.library.model.Book> borrowedBooks;
    private List<com.istad.library.model.Book> favoriteBooks;

    public Member() {
        this.borrowedBooks = new ArrayList<>();
        this.favoriteBooks = new ArrayList<>();
    }

    public Member(String UUID, String username, String password, String name, String email, String contactNumber) {
        this.UUID = UUID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.borrowedBooks = new ArrayList<>();
        this.favoriteBooks = new ArrayList<>();
    }

    public Member(String username, String password, String name, String email, String contactNumber) {
        this.UUID = java.util.UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.borrowedBooks = new ArrayList<>();
        this.favoriteBooks = new ArrayList<>();
    }

    public String getUUID() { return UUID; }
    public void setUUID(String UUID) { this.UUID = UUID; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public List<com.istad.library.model.Book> getBorrowedBooks() { return borrowedBooks; }
    public void setBorrowedBooks(List<com.istad.library.model.Book> borrowedBooks) { this.borrowedBooks = borrowedBooks; }

    public List<com.istad.library.model.Book> getFavoriteBooks() { return favoriteBooks; }
    public void setFavoriteBooks(List<com.istad.library.model.Book> favoriteBooks) { this.favoriteBooks = favoriteBooks; }
}
