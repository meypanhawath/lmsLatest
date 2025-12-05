package com.istad.library.view;

import com.istad.library.controller.BookController;
import com.istad.library.model.Book;
import com.istad.library.model.BorrowHistory;
import com.istad.library.model.Member;
import com.istad.library.service.BookService;
import com.istad.library.service.BorrowHistoryService;
import com.istad.library.service.MemberService;
import com.istad.library.util.InputUtil;
import com.istad.library.util.InputValidator;
import com.istad.library.util.TableUtil;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdminView {

    private final BookController bookController;
    private final MemberService memberService;
    private final BorrowHistoryService borrowHistoryService;

    public AdminView(BookService bookService, MemberService memberService, BorrowHistoryService borrowHistoryService) {
        this.bookController = new BookController(bookService);
        this.memberService = memberService;
        this.borrowHistoryService = borrowHistoryService;
    }

    public void adminFeature() {
        while (true) {
            CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
            Table table = new Table(2, BorderStyle.UNICODE_ROUND_BOX_WIDE);
            table.addCell("Admin Menu", cellStyle);
            table.addCell("", cellStyle);
            table.addCell("1) Manage Books");
            table.addCell("2) Manage Members");
            table.addCell("3) View Borrow History");
            table.addCell("0) Logout");
            TableUtil.print(table.render(), true);

            int choice = Integer.parseInt(InputUtil.getText("Select option:"));
            switch (choice) {
                case 1 -> manageBooks();
                case 2 -> manageMembers();
                case 3 -> viewBorrowHistory();
                case 0 -> {
                    TableUtil.print("Logging out...", true);
                    return;
                }
            }
        }
    }

    private void manageBooks() {
        BookView bookView = new BookView(bookController);
        bookView.bookManagementMenu();
    }

    private static final int MEMBER_PAGE_SIZE = 5;

    private void paginateMembers(List<Member> members) {
        if (members.isEmpty()) {
            TableUtil.print("No members to display.", true);
            return;
        }

        int page = 0;
        int totalPages = (members.size() + MEMBER_PAGE_SIZE - 1) / MEMBER_PAGE_SIZE;

        while (true) {
            int start = page * MEMBER_PAGE_SIZE;
            int end = Math.min(start + MEMBER_PAGE_SIZE, members.size());
            List<Member> pageMembers = members.subList(start, end);

            TableUtil.printHeader("Members (Page " + (page + 1) + " of " + totalPages + ")");
            TableUtil.printMember(pageMembers);

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

    private static final int HISTORY_PAGE_SIZE = 5;

    private void paginateBorrowHistory(List<BorrowHistory> histories) {
        if (histories.isEmpty()) {
            TableUtil.print("No borrow history to display.", true);
            return;
        }

        int page = 0;
        int totalPages = (histories.size() + HISTORY_PAGE_SIZE - 1) / HISTORY_PAGE_SIZE;

        while (true) {
            int start = page * HISTORY_PAGE_SIZE;
            int end = Math.min(start + HISTORY_PAGE_SIZE, histories.size());
            List<BorrowHistory> pageHistories = histories.subList(start, end);

            TableUtil.printHeader("Borrow History (Page " + (page + 1) + " of " + totalPages + ")");
            TableUtil.printBorrowHistory(pageHistories, bookController.getBookService());

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


    private void manageMembers() {
        while (true) {
            CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
            Table table = new Table(2, BorderStyle.UNICODE_ROUND_BOX_WIDE);
            table.addCell("Member Management", cellStyle);
            table.addCell("", cellStyle);
            table.addCell("1) View All Members");
            table.addCell("2) Add New Member");
            table.addCell("3) Update Member");
            table.addCell("4) Delete Member");
            table.addCell("5) Search Member");
            table.addCell("0) Back");
            TableUtil.print(table.render(), true);
            int choice = Integer.parseInt(InputUtil.getText("Select option:"));
            switch (choice) {
                case 1 -> viewMembers();
                case 2 -> addMember();
                case 3 -> updateMember();
                case 4 -> deleteMember();
                case 5 -> searchMember();
                case 0 -> {
                    return;
                }
            }
        }
    }

    private void viewMembers() {
        List<Member> members = new ArrayList<>(memberService.listMembers());
        Collections.reverse(members);
        paginateMembers(members);
    }


    private void viewBorrowHistory() {
        List<BorrowHistory> histories = new ArrayList<>(borrowHistoryService.listAll());
        Collections.reverse(histories);
        paginateBorrowHistory(histories);
    }

    private void addMember() {
        String username;
        do {
            username = InputUtil.getText("Enter username:");
            if (!InputValidator.isNotEmpty(username))
                System.out.println("Username cannot be empty!");
            else if (!InputValidator.isUniqueUsername(username, memberService))
                System.out.println("Username already exists!");
        } while (!InputValidator.isNotEmpty(username) || !InputValidator.isUniqueUsername(username, memberService));

        String fullName;
        do {
            fullName = InputUtil.getText("Enter full name:");
            if (!InputValidator.isNotEmpty(fullName))
                System.out.println("Full name cannot be empty!");
        } while (!InputValidator.isNotEmpty(fullName));

        String email;
        do {
            email = InputUtil.getText("Enter email:");
            if (!InputValidator.isValidEmail(email))
                System.out.println("Invalid email format!");
        } while (!InputValidator.isValidEmail(email));

        String contact;
        do {
            contact = InputUtil.getText("Enter 10-digit contact number:");
            if (!InputValidator.isValidPhone(contact))
                System.out.println("Invalid contact number! Must be 10 digits.");
        } while (!InputValidator.isValidPhone(contact));

        String password;
        do {
            password = InputUtil.getText("Enter password:");
            if (!InputValidator.isNotEmpty(password))
                System.out.println("Password cannot be empty!");
        } while (!InputValidator.isNotEmpty(password));

        Member newMember = new Member(
                java.util.UUID.randomUUID().toString(),
                username,
                password,
                fullName,
                email,
                contact
        );
        memberService.addNew(newMember);
        System.out.println("Member added successfully!");
    }


    private void updateMember() {
        String uuid = InputUtil.getText("Enter Member UUID to update:");
        Member existing = memberService.findByUUID(uuid);
        if (existing == null) {
            TableUtil.print("Member not found!", true);
            return;
        }
        String username = InputUtil.getText("Enter new username (" + existing.getUsername() + "):");
        String password = InputUtil.getText("Enter new password (" + existing.getPassword() + "):");
        String name = InputUtil.getText("Enter new full name (" + existing.getName() + "):");
        String email = InputUtil.getText("Enter new email (" + existing.getEmail() + "):");
        String contact = InputUtil.getText("Enter new contact number (" + existing.getContactNumber() + "):");
        existing.setUsername(username);
        existing.setPassword(password);
        existing.setName(name);
        existing.setEmail(email);
        existing.setContactNumber(contact);
        memberService.update(existing);
        TableUtil.printHeader("Member updated successfully!");
    }

    private void deleteMember() {
        String uuid = InputUtil.getText("Enter Member UUID to delete:");
        Book existing = bookController.getBookService().findByUUID(uuid);
        if (existing == null) {
            TableUtil.print("Book not found!", true);
            return;
        }
        memberService.delete(uuid);
        TableUtil.printHeader("Member deleted successfully!");
    }

    private void searchMember() {
        String name = InputUtil.getText("Enter name to search:");
        List<Member> result = memberService.searchByName(name);
        if (result == null) {
            TableUtil.print("Book not found!", true);
            return;
        }
        TableUtil.printMember(result);
    }
}
