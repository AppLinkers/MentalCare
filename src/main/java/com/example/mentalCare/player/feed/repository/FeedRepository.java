package com.example.mentalCare.player.feed.repository;

import com.example.mentalCare.player.feed.domain.Feed;
import com.example.mentalCare.player.feed.dto.FeedReadRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    @Query("select f " +
            "from Feed f " +
            "where f.player.id = :playerId")
    List<Feed> findFeedByPlayerId(Long playerId);
}
