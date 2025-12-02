package com.istad.library.controller;

import com.istad.library.model.Member;
import com.istad.library.repository.impl.MemberRepositoryImpl;
import com.istad.library.service.MemberService;
import com.istad.library.service.impl.MemberServiceImpl;

public class AuthController {

    private final MemberService memberService;
    private final String adminUsername = "admin";
    private final String adminPassword = "admin123";

    public AuthController() {
        this.memberService = new MemberServiceImpl(new MemberRepositoryImpl());
    }

    public boolean isAdmin(String username, String password) {
        return adminUsername.equals(username) && adminPassword.equals(password);
    }

    public Member authenticateMember(String username, String password) {
        Member member = memberService.findByUsername(username);
        if (member != null && member.getPassword().equals(password)) {
            return member;
        }
        return null;
    }
}
