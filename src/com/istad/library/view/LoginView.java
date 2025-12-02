package com.istad.library.view;

import com.istad.library.controller.AuthController;
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

public class LoginView {

    private final AuthController authController;

    // Shared services
    private final BookService bookService;
    private final MemberService memberService;
    private final BorrowHistoryService borrowHistoryService;

    public LoginView() {
        this.authController = new AuthController();

        // Initialize shared services
        this.bookService = new BookServiceImpl(new BookRepositoryImpl());
        this.borrowHistoryService = new BorrowHistoryServiceImpl(new BorrowHistoryRepositoryImpl());
        this.memberService = new MemberServiceImpl(new MemberRepositoryImpl(), borrowHistoryService);
    }

    public void start() {
        while (true) {
            TableUtil.print("1) Login  0) Exit", true);
            int choice = Integer.parseInt(InputUtil.getText("Select option:"));
            switch (choice) {
                case 1 -> login();
                case 0 -> {
                    TableUtil.print("Exiting application...", true);
                    System.exit(0);
                }
            }
        }
    }

    private void login() {
        String username = InputUtil.getText("Enter username:");
        String password = InputUtil.getText("Enter password:");

        if (authController.isAdmin(username, password)) {
            TableUtil.print("Admin login successful!", true);
            AdminView adminView = new AdminView(bookService, memberService, borrowHistoryService);
            adminView.adminFeature();
        } else if (authController.authenticateMember(username, password) != null) {
            TableUtil.print("Member login successful!", true);
            Member member = authController.authenticateMember(username, password);
            MemberView memberView = new MemberView(
                    member,
                    memberService,
                    bookService,
                    borrowHistoryService
            );
            memberView.memberDashboard();
        } else {
            TableUtil.print("Invalid username or password!", true);
        }
    }

}
