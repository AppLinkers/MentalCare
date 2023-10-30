package com.example.mentalCare.player.feed.repository;

import com.example.mentalCare.player.feed.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
