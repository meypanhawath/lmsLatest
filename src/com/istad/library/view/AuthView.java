package com.istad.library.view;

import com.istad.library.model.Member;
import com.istad.library.repository.impl.BookRepositoryImpl;
import com.istad.library.repository.impl.BorrowHistoryRepositoryImpl;
import com.istad.library.repository.impl.MemberRepositoryImpl;
import com.istad.library.service.BookService;
import com.istad.library.service.BorrowHistoryService;
import com.istad.library.service.MemberService;
import com.istad.library.service.impl.BookServiceImpl;
import com.istad.library.service.impl.BorrowHistoryServiceImpl;
import com.istad.library.service.impl.MemberServiceImpl;
import com.istad.library.util.InputUtil;
import com.istad.library.util.TableUtil;

public class AuthView {

    private final MemberService memberService;
    private final BookService bookService;
    private final BorrowHistoryService borrowHistoryService;

    public AuthView() {
        // Create repositories
        var bookRepo = new BookRepositoryImpl();
        var memberRepo = new MemberRepositoryImpl();
        var borrowHistoryRepo = new BorrowHistoryRepositoryImpl();

        // Create shared services
        this.bookService = new BookServiceImpl(bookRepo);
        this.borrowHistoryService = new BorrowHistoryServiceImpl(borrowHistoryRepo);
        this.memberService = new MemberServiceImpl(memberRepo, borrowHistoryService);
    }

    public void start() {
        while (true) {
            TableUtil.printHeader("Login Menu");
            TableUtil.print("1) Login", true);
            TableUtil.print("0) Exit", true);

            String choice = InputUtil.getText("Select option:");
            switch (choice) {
                case "1" -> login();
                case "0" -> {
                    TableUtil.print("Exiting application...", true);
                    System.exit(0);
                }
                default -> TableUtil.print("Invalid option!", true);
            }
        }
    }

    private void login() {
        String username = InputUtil.getText("Enter username:");
        String password = InputUtil.getText("Enter password:");

        if (username.equals("admin") && password.equals("admin123")) {
            AdminView adminView = new AdminView(bookService, memberService, borrowHistoryService);
            adminView.adminFeature();
            return;
        }

        Member member = memberService.findByUsername(username);
        if (member != null && member.getPassword().equals(password)) {
            MemberView memberView = new MemberView(member, memberService, bookService, borrowHistoryService);
            memberView.memberDashboard();
        } else {
            TableUtil.print("Invalid username or password!", true);
        }
    }

}
