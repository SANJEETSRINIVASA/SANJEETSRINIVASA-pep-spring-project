package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Integer> {
    boolean existsByPostedBy(int postedBy);
    List<Message> findAllByPostedBy(int postedBy);
}
