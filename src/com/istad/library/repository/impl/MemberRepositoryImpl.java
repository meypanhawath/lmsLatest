package com.istad.library.repository.impl;

import com.istad.library.model.Member;
import com.istad.library.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MemberRepositoryImpl implements MemberRepository {

    private static final List<Member> members = new ArrayList<>();

    public MemberRepositoryImpl() {
        Member m1 = new Member(UUID.randomUUID().toString(), "alice01", "pass123", "Alice", "alice@example.com", "0123456789");
        Member m2 = new Member(UUID.randomUUID().toString(), "bob02", "pass123", "Bob", "bob@example.com", "0987654321");
        Member m3 = new Member(UUID.randomUUID().toString(), "bob02", "pass123", "Bob", "bob@example.com", "0987654321");
        Member m4 = new Member(UUID.randomUUID().toString(), "bob02", "pass123", "Bob", "bob@example.com", "0987654321");
        Member m5 = new Member(UUID.randomUUID().toString(), "bob02", "pass123", "Bob", "bob@example.com", "0987654321");
        Member m6 = new Member(UUID.randomUUID().toString(), "bob02", "pass123", "Bob", "bob@example.com", "0987654321");
        Member m7 = new Member(UUID.randomUUID().toString(), "bob02", "pass123", "Bob", "bob@example.com", "0987654321");
        Member m8 = new Member(UUID.randomUUID().toString(), "bob02", "pass123", "Bob", "bob@example.com", "0987654321");
        members.add(m1);
        members.add(m2);
        members.add(m3);
        members.add(m4);
        members.add(m5);
        members.add(m6);
        members.add(m7);
        members.add(m8);
    }

    @Override
    public List<Member> findAll() {
        return members;
    }

    @Override
    public void insert(Member newMember) {
        members.add(newMember);
    }

    @Override
    public void update(Member updatedMember) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getUUID().equals(updatedMember.getUUID())) {
                members.set(i, updatedMember);
                break;
            }
        }
    }

    @Override
    public void delete(String uuid) {
        members.removeIf(m -> m.getUUID().equals(uuid));
    }

    @Override
    public Member findByUUID(String uuid) {
        return members.stream()
                .filter(m -> m.getUUID().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Member findByUsername(String username) {
        return members.stream()
                .filter(m -> m.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Member> searchByName(String name) {
        List<Member> result = new ArrayList<>();
        for (Member m : members) {
            if (m.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(m);
            }
        }
        return result;
    }
}