package com.istad.library.util;

import com.istad.library.model.Book;
import com.istad.library.model.BorrowHistory;
import com.istad.library.model.Member;
import com.istad.library.service.BookService;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;

public class TableUtil {

    public static void printMenu() {
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        Table table = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE);
        table.setColumnWidth(0, 50, 100);
        table.addCell("Application Menu", cellStyle);
        table.addCell("1)List All  2)Search  3)Add new  4)Update", cellStyle);
        table.addCell("5)Delete  0)Exit", cellStyle);
        print(table.render(), true);
    }

    public static void adminMenu(){
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        Table table = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE);
        table.setColumnWidth(0, 50,50);
        table.addCell("Admin Menu", cellStyle);
        table.addCell("1) Add Books             2) Add Members");
        table.addCell("3) View All Books        4) Search Book");
        table.addCell("5) View Borrow Records   0) Logout");

        print(table.render(), true);

        print("", true);
        print("Select option: ", false);

    }

    public static void printBooks(List<Book> books) {
        printBooks(books, true);
    }

    public static void printBooks(List<Book> books, boolean showUUID) {
        int columns = showUUID ? 6 : 5;
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        Table table = new Table(columns, BorderStyle.UNICODE_ROUND_BOX_WIDE);

        if (showUUID) table.addCell("UUID", cellStyle);
        table.addCell("Title", cellStyle);
        table.addCell("Author", cellStyle);
        table.addCell("ISBN", cellStyle);
        table.addCell("Published", cellStyle);
        table.addCell("Status", cellStyle);

        for (Book b : books) {
            if (showUUID) table.addCell(b.getUUID());
            table.addCell(b.getTitle());
            table.addCell(b.getAuthor());
            table.addCell(b.getIsbn());
            table.addCell(String.valueOf(b.getPublishedYear()));
            table.addCell(b.getIsAvailable() ? "Available" : "Unavailable");
        }

        System.out.println(table.render());
    }

    public static void printMember(List<Member> members) {
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        Table table = new Table(6, BorderStyle.UNICODE_ROUND_BOX_WIDE);
        table.addCell("UUID", cellStyle);
        table.addCell("Username", cellStyle);
        table.addCell("Name", cellStyle);
        table.addCell("Email", cellStyle);
        table.addCell("Contact", cellStyle);
        table.addCell("Status", cellStyle);

        for (Member m : members) {
            table.addCell(m.getUUID());
            table.addCell(m.getUsername());
            table.addCell(m.getName());
            table.addCell(m.getEmail());
            table.addCell(m.getContactNumber());
            table.addCell("Active");
        }

        print(table.render(), true);
    }

    public static void printBorrowHistory(List<BorrowHistory> list, BookService bookService) {
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        Table table = new Table(7, BorderStyle.UNICODE_ROUND_BOX_WIDE);
        table.addCell("UUID", cellStyle);
        table.addCell("Book", cellStyle);
        table.addCell("Member UUID", cellStyle);
        table.addCell("Borrowed", cellStyle);
        table.addCell("Due", cellStyle);
        table.addCell("Returned", cellStyle);
        table.addCell("Renew", cellStyle);

        for (BorrowHistory h : list) {
            table.addCell(h.getUuid());
            String title = h.getBookUuid();
            if (bookService != null && bookService.findByUUID(h.getBookUuid()) != null) {
                title = bookService.findByUUID(h.getBookUuid()).getTitle();
            }
            table.addCell(title);
            table.addCell(h.getMemberUuid());
            table.addCell(h.getBorrowDate() == null ? "-" : h.getBorrowDate().toString());
            table.addCell(h.getDueDate() == null ? "-" : h.getDueDate().toString());
            table.addCell(h.getReturnDate() == null ? "-" : h.getReturnDate().toString());
            table.addCell(String.valueOf(h.getRenewCount()));
        }

        System.out.println(table.render());
    }

    public static void print(String text, boolean isNewLine) {
        if (isNewLine)
            System.out.println(text);
        else
            System.out.print(text);
    }

    public static void printHeader(String text) {
        Table table = new Table(1,
                BorderStyle.UNICODE_ROUND_BOX_WIDE);
        table.addCell(text);
        print(table.render(), true);
    }

/*    public static void printStaffList(List<Staff> staffList) {
        // 1. Create table with 6 columns and border style
        Table table = new Table(6, BorderStyle.UNICODE_ROUND_BOX_WIDE);

        // 2. Add table header
        table.addCell("ID");
        table.addCell("NAME");
        table.addCell("GENDER");
        table.addCell("POSITION");
        table.addCell("SALARY");
        table.addCell("STATUS");

        // 3. Add table data
        for (Staff staff : staffList) {
            table.addCell(staff.getId());
            table.addCell(staff.getName());
            table.addCell(staff.getGender());
            table.addCell(staff.getPosition());
            table.addCell(staff.getSalary().toString());
            table.addCell(staff.getStatus().toString());
        }

        // 4. Render table
        print(table.render(), true);
    }*/

}