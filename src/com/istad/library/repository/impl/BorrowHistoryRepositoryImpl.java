package com.istad.library.repository.impl;

import com.istad.library.model.BorrowHistory;
import com.istad.library.repository.BorrowHistoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BorrowHistoryRepositoryImpl implements BorrowHistoryRepository {
    private final List<BorrowHistory> historyList = new ArrayList<>();

    @Override
    public List<BorrowHistory> findAll() {
        return historyList;
    }

    @Override
    public void insert(BorrowHistory bh) {
        historyList.add(bh);
    }

    @Override
    public void update(BorrowHistory bh) {
        for (int i = 0; i < historyList.size(); i++) {
            if (historyList.get(i).getUuid().equals(bh.getUuid())) {
                historyList.set(i, bh);
                break;
            }
        }
    }

    @Override
    public void delete(String uuid) {
        historyList.removeIf(h -> h.getUuid().equals(uuid));
    }

    @Override
    public List<BorrowHistory> findByMemberUuid(String memberUuid) {
        return historyList.stream()
                .filter(h -> h.getMemberUuid().equals(memberUuid))
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowHistory> findByBookUuid(String bookUuid) {
        return historyList.stream()
                .filter(h -> h.getBookUuid().equals(bookUuid))
                .collect(Collectors.toList());
    }
}
