package com.istad.library.view;

import com.istad.library.model.Book;
import com.istad.library.model.BorrowHistory;
import com.istad.library.model.Member;
import com.istad.library.service.BookService;
import com.istad.library.service.BorrowHistoryService;
import com.istad.library.service.MemberService;
import com.istad.library.util.InputUtil;
import com.istad.library.util.TableUtil;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;
import org.nocrala.tools.texttablefmt.BorderStyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemberView {
    private final Member member;
    private final MemberService memberService;
    private final BookService bookService;
    private final BorrowHistoryService borrowHistoryService;

    private static final int PAGE_SIZE = 5;

    public MemberView(Member member, MemberService memberService, BookService bookService, BorrowHistoryService borrowHistoryService) {
        this.member = member;
        this.memberService = memberService;
        this.bookService = bookService;
        this.borrowHistoryService = borrowHistoryService;
    }

    public void memberDashboard() {
        while (true) {
            TableUtil.printHeader("Member Dashboard - Welcome, " + member.getName());
            System.out.println("1) My Borrowed Books");
            System.out.println("2) My Favorite Books");
            System.out.println("3) View All Books");
            System.out.println("4) Personal Info");
            System.out.println("0) Logout");

            String choice = InputUtil.getText("Select option:");
            switch (choice) {
                case "1" -> viewBorrowedBooks();
                case "2" -> {
                    List<Book> favs = new ArrayList<>(member.getFavoriteBooks());
                    Collections.reverse(favs);
                    paginateBooks(favs, false, true);
                }
                case "3" -> {
                    List<Book> allBooks = new ArrayList<>(bookService.listBooks());
                    Collections.reverse(allBooks);
                    paginateBooks(allBooks, true, true);
                }
                case "4" -> viewPersonalInfo();
                case "0" -> {
                    TableUtil.print("Logging out...", true);
                    return;
                }
                default -> TableUtil.print("Invalid choice!", true);
            }
        }
    }

    private void viewBorrowedBooks() {
        List<BorrowHistory> histories = borrowHistoryService.findByMember(member.getUUID());

        if (histories.isEmpty()) {
            TableUtil.print("You have no borrowed books.", true);
            return;
        }

        int page = 0;
        int totalPages = (histories.size() + PAGE_SIZE - 1) / PAGE_SIZE;

        while (true) {
            int start = page * PAGE_SIZE;
            int end = Math.min(start + PAGE_SIZE, histories.size());
            List<BorrowHistory> pageHistories = histories.subList(start, end);

            // Build table
            CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
            Table table = new Table(7, BorderStyle.UNICODE_ROUND_BOX_WIDE);
            table.addCell("UUID", cellStyle);
            table.addCell("Book", cellStyle);
            table.addCell("Borrowed", cellStyle);
            table.addCell("Due", cellStyle);
            table.addCell("Returned", cellStyle);
            table.addCell("Renew", cellStyle);
            table.addCell("Status", cellStyle);

            for (BorrowHistory h : pageHistories) {
                table.addCell(h.getUuid());
                String title = "Unknown";
                var book = bookService.findByUUID(h.getBookUuid());
                if (book != null) title = book.getTitle();
                table.addCell(title);
                table.addCell(h.getBorrowDate() == null ? "-" : h.getBorrowDate().toString());
                table.addCell(h.getDueDate() == null ? "-" : h.getDueDate().toString());
                table.addCell(h.getReturnDate() == null ? "-" : h.getReturnDate().toString());
                table.addCell(String.valueOf(h.getRenewCount()));
                table.addCell(h.getReturnDate() == null ? "Borrowed" : "Returned");
            }

            TableUtil.printHeader("My Borrowed Books (Page " + (page + 1) + " of " + totalPages + ")");
            TableUtil.print(table.render(), true);

            System.out.println("[N] Next Page  [P] Previous Page  [E] Exit");
            String input = InputUtil.getText("Enter option:").trim().toUpperCase();

            switch (input) {
                case "E" -> { return; }
                case "N" -> { if (page + 1 < totalPages) page++; else TableUtil.print("Already on last page.", true); }
                case "P" -> { if (page > 0) page--; else TableUtil.print("Already on first page.", true); }
                default -> TableUtil.print("Unknown option!", true);
            }
        }
    }

    private void paginateBooks(List<Book> books, boolean allowBorrowReturn, boolean allowFavorite) {
        if (books.isEmpty()) {
            TableUtil.print("No books to display.", true);
            return;
        }

        int page = 0;
        int totalPages = (books.size() + PAGE_SIZE - 1) / PAGE_SIZE;

        while (true) {
            int start = page * PAGE_SIZE;
            int end = Math.min(start + PAGE_SIZE, books.size());
            List<Book> pageBooks = books.subList(start, end);

            TableUtil.printHeader("Books (Page " + (page + 1) + " of " + totalPages + ")");
            TableUtil.printBooks(pageBooks);

            System.out.println("Options:");
            if (allowBorrowReturn) System.out.println("[B UUID] Borrow  [T UUID] Return");
            if (allowFavorite) System.out.println("[F UUID] Favorite/Unfavorite");
            System.out.println("[N] Next Page  [P] Previous Page  [E] Exit");

            String input = InputUtil.getText("Enter option:").trim();
            if (input.isEmpty()) continue;

            String[] parts = input.split(" ");
            String action = parts[0].toUpperCase();
            String bookUUID = parts.length > 1 ? parts[1] : "";

            if (action.equals("E")) break;
            if (action.equals("N")) { if (page + 1 < totalPages) page++; else TableUtil.print("Already on last page.", true); continue; }
            if (action.equals("P")) { if (page > 0) page--; else TableUtil.print("Already on first page.", true); continue; }

            Book selected = pageBooks.stream()
                    .filter(b -> b.getUUID().equals(bookUUID))
                    .findFirst()
                    .orElse(null);

            if ((action.equals("B") || action.equals("T") || action.equals("F")) && selected == null) {
                TableUtil.print("Invalid UUID!", true);
                continue;
            }

            switch (action) {
                case "B" -> {
                    memberService.borrowBook(member, selected);
                    TableUtil.printHeader("Book borrowed successfully!");
                }
                case "T" -> {
                    memberService.returnBook(member, selected);
                    TableUtil.printHeader("Book returned successfully!");
                }
                case "F" -> {
                    if (member.getFavoriteBooks().contains(selected)) {
                        memberService.unfavoriteBook(member, selected);
                        TableUtil.printHeader("Book removed from favorites!");
                    } else {
                        memberService.favoriteBook(member, selected);
                        TableUtil.printHeader("Book added to favorites!");
                    }
                }
                default -> TableUtil.print("Unknown action!", true);
            }
        }
    }

    private void viewPersonalInfo() {
        TableUtil.printHeader("Personal Info");
        System.out.println("Username: " + member.getUsername());
        System.out.println("Full Name: " + member.getName());
        System.out.println("Email: " + member.getEmail());
        System.out.println("Contact: " + member.getContactNumber());
        InputUtil.getText("Press Enter to return...");
    }
}
