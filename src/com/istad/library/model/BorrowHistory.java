package com.istad.library.model;

import java.time.LocalDate;
import java.util.UUID;

public class BorrowHistory {
    private String uuid;
    private String memberUuid;
    private String bookUuid;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate; // nullable
    private int renewCount;

    public BorrowHistory() { }

    public BorrowHistory(String memberUuid, String bookUuid, LocalDate borrowDate, LocalDate dueDate) {
        this.uuid = UUID.randomUUID().toString();
        this.memberUuid = memberUuid;
        this.bookUuid = bookUuid;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.renewCount = 0;
    }

    // Getters and setters
    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public String getMemberUuid() { return memberUuid; }
    public void setMemberUuid(String memberUuid) { this.memberUuid = memberUuid; }

    public String getBookUuid() { return bookUuid; }
    public void setBookUuid(String bookUuid) { this.bookUuid = bookUuid; }

    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public int getRenewCount() { return renewCount; }
    public void setRenewCount(int renewCount) { this.renewCount = renewCount; }

    public void incrementRenewCount() { this.renewCount++; }
}
