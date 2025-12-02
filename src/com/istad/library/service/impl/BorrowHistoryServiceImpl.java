package com.istad.library.service.impl;

import com.istad.library.model.BorrowHistory;
import com.istad.library.repository.BorrowHistoryRepository;
import com.istad.library.service.BorrowHistoryService;

import java.util.List;

public class BorrowHistoryServiceImpl implements BorrowHistoryService {

    private final BorrowHistoryRepository repo;

    public BorrowHistoryServiceImpl(BorrowHistoryRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<BorrowHistory> listAll() { return repo.findAll(); }

    @Override
    public void add(BorrowHistory bh) { repo.insert(bh); }

    @Override
    public void update(BorrowHistory bh) { repo.update(bh); }

    @Override
    public void delete(String uuid) { repo.delete(uuid); }

    @Override
    public List<BorrowHistory> findByMember(String memberUuid) { return repo.findByMemberUuid(memberUuid); }

    @Override
    public List<BorrowHistory> findByBook(String bookUuid) { return repo.findByBookUuid(bookUuid); }
}
