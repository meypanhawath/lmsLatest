package com.istad.library.view;

import com.istad.library.controller.BookController;
import com.istad.library.model.Book;
import com.istad.library.util.InputUtil;
import com.istad.library.util.InputValidator;
import com.istad.library.util.TableUtil;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookView {
    private final BookController bookController;

    public BookView(BookController bookController) {
        this.bookController = bookController;
    }

    public void bookManagementMenu() {
        while (true) {
            CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
            Table table = new Table(2, BorderStyle.UNICODE_ROUND_BOX_WIDE);
            table.addCell("Book Management", cellStyle);
            table.addCell("", cellStyle);
            table.addCell("1) View All Books");
            table.addCell("2) Add New Book");
            table.addCell("3) Update Book");
            table.addCell("4) Delete Book");
            table.addCell("5) Search Book");
            table.addCell("0) Back");
            TableUtil.print(table.render(), true);

            int choice = Integer.parseInt(InputUtil.getText("Select option:"));
            switch (choice) {
                case 1 -> viewBooks();
                case 2 -> addBook();
                case 3 -> updateBook();
                case 4 -> deleteBook();
                case 5 -> searchBook();
                case 0 -> { return; }
            }
        }
    }

    private static final int BOOK_PAGE_SIZE = 5;

    private void viewBooks() {
        List<Book> books = new ArrayList<>(bookController.getBookService().listBooks());
        Collections.reverse(books);
        paginateBooks(books);
    }

    private void paginateBooks(List<Book> books) {
        if (books.isEmpty()) {
            TableUtil.print("No books to display.", true);
            return;
        }

        int page = 0;
        int totalPages = (books.size() + BOOK_PAGE_SIZE - 1) / BOOK_PAGE_SIZE;

        while (true) {
            int start = page * BOOK_PAGE_SIZE;
            int end = Math.min(start + BOOK_PAGE_SIZE, books.size());
            List<Book> pageBooks = books.subList(start, end);

            printBooks(pageBooks);
            System.out.println("Options: [N] Next Page  [P] Previous Page  [E] Exit");
            String input = InputUtil.getText("Enter option:").trim().toUpperCase();

            switch (input) {
                case "E" -> { return; }
                case "N" -> {
                    if (page + 1 < totalPages) page++;
                    else TableUtil.print("Already on last page.", true);
                }
                case "P" -> {
                    if (page > 0) page--;
                    else TableUtil.print("Already on first page.", true);
                }
                default -> TableUtil.print("Invalid option!", true);
            }
        }
    }

    private void addBook() {
        String title;
        do {
            title = InputUtil.getText("Enter book title:");
            if (!InputValidator.isNotEmpty(title))
                System.out.println("Title cannot be empty!");
        } while (!InputValidator.isNotEmpty(title));

        String author;
        do {
            author = InputUtil.getText("Enter author:");
            if (!InputValidator.isNotEmpty(author))
                System.out.println("Author cannot be empty!");
        } while (!InputValidator.isNotEmpty(author));

        String isbn;
        do {
            isbn = InputUtil.getText("Enter ISBN (10 or 13 digits):");
            if (!InputValidator.isValidISBN(isbn))
                System.out.println("Invalid ISBN! Must be 10 or 13 digits.");
        } while (!InputValidator.isValidISBN(isbn));

        String yearStr;
        do {
            yearStr = InputUtil.getText("Enter published year:");
            if (!InputValidator.isValidYear(yearStr))
                System.out.println("Invalid year! Enter a valid number.");
        } while (!InputValidator.isValidYear(yearStr));

        int year = Integer.parseInt(yearStr);

        // Create new Book object
        Book newBook = new Book(java.util.UUID.randomUUID().toString(), title, author, isbn, year, true);

        // Add book using controller
        bookController.getBookService().addNew(newBook);

        TableUtil.printHeader("Book added successfully!");
    }



    private void updateBook() {
        String uuid = InputUtil.getText("Enter Book UUID to update:");
        Book existing = bookController.getBookService().findByUUID(uuid);
        if (existing == null) {
            TableUtil.print("Book not found!", true);
            return;
        }

        String title = InputUtil.getText("Enter new Title (" + existing.getTitle() + "):");
        String author = InputUtil.getText("Enter new Author (" + existing.getAuthor() + "):");
        String isbn = InputUtil.getText("Enter new ISBN (" + existing.getIsbn() + "):");
        int year = InputUtil.getInteger("Enter new Published Year (" + existing.getPublishedYear() + "):");
        boolean isAvailable = InputUtil.getText("Is book available? (true/false) (" + existing.getIsAvailable() + "):").equalsIgnoreCase("true");

        existing.setTitle(title);
        existing.setAuthor(author);
        existing.setIsbn(isbn);
        existing.setPublishedYear(year);
        existing.setIsAvailable(isAvailable);

        bookController.getBookService().update(existing);
        TableUtil.printHeader("Book updated successfully!");
    }

    private void deleteBook() {
        String uuid = InputUtil.getText("Enter Book UUID to delete:");
        bookController.getBookService().delete(uuid);
        TableUtil.printHeader("Book deleted successfully!");
    }

    private void searchBook() {
        String keyword = InputUtil.getText("Enter title or author to search:");
        List<Book> result = bookController.getBookService().search(keyword);
        printBooks(result);
    }

    public void printBooks(List<Book> books) {
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        Table table = new Table(6, BorderStyle.UNICODE_ROUND_BOX_WIDE);
        table.addCell("UUID", cellStyle);
        table.addCell("Title", cellStyle);
        table.addCell("Author", cellStyle);
        table.addCell("ISBN", cellStyle);
        table.addCell("Published", cellStyle);
        table.addCell("Status", cellStyle);

        for (Book b : books) {
            table.addCell(b.getUUID());
            table.addCell(b.getTitle());
            table.addCell(b.getAuthor());
            table.addCell(b.getIsbn());
            table.addCell(String.valueOf(b.getPublishedYear()));
            table.addCell(b.getIsAvailable() ? "Available" : "Unavailable");
        }

        TableUtil.print(table.render(), true);
    }
}
