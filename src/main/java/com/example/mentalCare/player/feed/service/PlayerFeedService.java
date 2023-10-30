package com.example.mentalCare.player.feed.service;

import com.example.mentalCare.player.feed.dto.FeedReadRes;
import com.example.mentalCare.player.feed.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerFeedService {

    private final FeedRepository feedRepository;

    public List<FeedReadRes> getPlayerFeedList(Long playerId) {
        return feedRepository.findFeedByPlayerId(playerId);
    }

}
