package com.istad.library.repository;

import com.istad.library.model.Member;
import java.util.List;

public interface MemberRepository {
    List<Member> findAll();
    void insert(Member newMember);
    void update(Member updatedMember);
    void delete(String uuid);
    Member findByUUID(String uuid);
    Member findByUsername(String username);
    List<Member> searchByName(String name);
}
