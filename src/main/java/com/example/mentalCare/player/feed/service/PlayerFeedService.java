package com.example.mentalCare.player.feed.service;

import com.example.mentalCare.common.domain.User;
import com.example.mentalCare.common.repository.UserRepository;
import com.example.mentalCare.player.feed.domain.Feed;
import com.example.mentalCare.player.feed.dto.FeedReadRes;
import com.example.mentalCare.player.feed.dto.FeedWriteReq;
import com.example.mentalCare.player.feed.repository.FeedRepository;
import com.example.mentalCare.player.profile.domain.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerFeedService {

    private final UserRepository userRepository;
    private final FeedRepository feedRepository;

    public List<FeedReadRes> getPlayerFeedList(Long playerId) {
        return feedRepository.findFeedByPlayerId(playerId);
    }

    public void feedWrite(String userLoginId, Player player, FeedWriteReq feedWriteReq) {
        User user = userRepository.findUserByLoginId(userLoginId).get();
        Feed feed = Feed.builder()
                .player(player)
                .user(user)
                .content(feedWriteReq.getContent())
                .build();

        feedRepository.save(feed);
    }
}
