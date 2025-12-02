package com.istad.library.repository;

import com.istad.library.model.BorrowHistory;
import java.util.List;

public interface BorrowHistoryRepository {
    List<BorrowHistory> findAll();
    void insert(BorrowHistory bh);
    void update(BorrowHistory bh);
    void delete(String uuid);
    List<BorrowHistory> findByMemberUuid(String memberUuid);
    List<BorrowHistory> findByBookUuid(String bookUuid);
}
