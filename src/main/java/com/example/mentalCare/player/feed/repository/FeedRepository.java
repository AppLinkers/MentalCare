package com.example.mentalCare.player.feed.repository;

import com.example.mentalCare.player.feed.domain.Feed;
import com.example.mentalCare.player.feed.dto.FeedReadRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    @Query("select new com.example.mentalCare.player.feed.dto.FeedReadRes(f.id, f.user.name, f.user.imgUrl, f.content, f.date, f.commentList) " +
            "from Feed f " +
            "where f.player.id = :playerId")
    List<FeedReadRes> findFeedByPlayerId(Long playerId);
}
