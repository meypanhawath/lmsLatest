package com.istad.library.service.impl;

import com.istad.library.model.Book;
import com.istad.library.model.BorrowHistory;
import com.istad.library.model.Member;
import com.istad.library.repository.MemberRepository;
import com.istad.library.service.BorrowHistoryService;
import com.istad.library.service.MemberService;

import java.time.LocalDate;
import java.util.List;

public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final BorrowHistoryService borrowHistoryService; // may be null
    private static final int DEFAULT_BORROW_DAYS = 14;
    private static final int RENEW_DAYS = 7;

    // Original constructor (backward compatible)
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.borrowHistoryService = null;
    }

    // New overloaded constructor used by AdminView (inject borrow history service)
    public MemberServiceImpl(MemberRepository memberRepository, BorrowHistoryService borrowHistoryService) {
        this.memberRepository = memberRepository;
        this.borrowHistoryService = borrowHistoryService;
    }

    @Override
    public List<Member> listMembers() {
        return memberRepository.findAll();
    }

    @Override
    public void addNew(Member newMember) {
        memberRepository.insert(newMember);
    }

    @Override
    public void update(Member member) {
        memberRepository.update(member);
    }

    @Override
    public void delete(String uuid) {
        memberRepository.delete(uuid);
    }

    @Override
    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Override
    public Member findByUUID(String uuid) {
        return memberRepository.findAll()
                .stream()
                .filter(b -> b.getUUID().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Member> searchByName(String name) {
        return memberRepository.searchByName(name);
    }

    @Override
    public void borrowBook(Member member, Book book) {
        if (book == null) return;
        if (!book.getIsAvailable()) return;
        if (!member.getBorrowedBooks().contains(book)) {
            member.getBorrowedBooks().add(book);
            book.setIsAvailable(false);

            if (borrowHistoryService != null) {
                LocalDate borrowDate = LocalDate.now();
                BorrowHistory bh = new BorrowHistory(member.getUUID(), book.getUUID(),
                        borrowDate, borrowDate.plusDays(DEFAULT_BORROW_DAYS));
                borrowHistoryService.add(bh);
            }
        }
    }

    @Override
    public void renewBook(Member member, Book book) {
        if (book == null) return;
        if (!member.getBorrowedBooks().contains(book)) return;

        if (borrowHistoryService != null) {
            List<BorrowHistory> histories = borrowHistoryService.findByMember(member.getUUID());
            for (BorrowHistory h : histories) {
                if (h.getBookUuid().equals(book.getUUID()) && h.getReturnDate() == null) {
                    h.incrementRenewCount();
                    h.setDueDate(h.getDueDate().plusDays(RENEW_DAYS));
                    borrowHistoryService.update(h);
                    break;
                }
            }
        }
    }

    @Override
    public void returnBook(Member member, Book book) {
        if (book == null) return;
        if (!member.getBorrowedBooks().contains(book)) return;

        member.getBorrowedBooks().remove(book);
        book.setIsAvailable(true);

        if (borrowHistoryService != null) {
            List<BorrowHistory> histories = borrowHistoryService.findByMember(member.getUUID());
            for (BorrowHistory h : histories) {
                if (h.getBookUuid().equals(book.getUUID()) && h.getReturnDate() == null) {
                    h.setReturnDate(LocalDate.now());
                    borrowHistoryService.update(h);
                    break;
                }
            }
        }
    }

    @Override
    public void favoriteBook(Member member, Book book) {
        if (book == null) return;
        if (!member.getFavoriteBooks().contains(book)) {
            member.getFavoriteBooks().add(book);
        }
    }

    @Override
    public void unfavoriteBook(Member member, Book book) {
        if (book == null) return;
        member.getFavoriteBooks().remove(book);
    }
}
