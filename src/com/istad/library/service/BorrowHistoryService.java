package com.istad.library.service;

import com.istad.library.model.BorrowHistory;
import java.util.List;

public interface BorrowHistoryService {
    List<BorrowHistory> listAll();
    void add(BorrowHistory bh);
    void update(BorrowHistory bh);
    void delete(String uuid);
    List<BorrowHistory> findByMember(String memberUuid);
    List<BorrowHistory> findByBook(String bookUuid);
}
