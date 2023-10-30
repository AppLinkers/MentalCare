package com.example.mentalCare.player.feed.service;

import com.example.mentalCare.common.domain.User;
import com.example.mentalCare.common.repository.UserRepository;
import com.example.mentalCare.player.feed.domain.Comment;
import com.example.mentalCare.player.feed.domain.Feed;
import com.example.mentalCare.player.feed.dto.CommentWriteReq;
import com.example.mentalCare.player.feed.dto.FeedReadRes;
import com.example.mentalCare.player.feed.dto.FeedReadWithCommentRes;
import com.example.mentalCare.player.feed.dto.FeedWriteReq;
import com.example.mentalCare.player.feed.repository.CommentRepository;
import com.example.mentalCare.player.feed.repository.FeedRepository;
import com.example.mentalCare.player.profile.domain.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerFeedService {

    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<FeedReadRes> getPlayerFeedList(Long playerId) {
        List<FeedReadRes> response = new ArrayList<>();

        List<Feed> feedList = feedRepository.findFeedByPlayerId(playerId);
        for (Feed feed : feedList) {
            response.add(FeedReadRes.builder()
                    .id(feed.getId())
                    .userName(feed.getUser().getName())
                    .userImgUrl(feed.getUser().getImgUrl())
                    .content(feed.getContent())
                    .date(feed.getDate())
                    .commentList(feed.getCommentList())
                    .build());
        }
        return response;
    }

    @Transactional
    public void feedWrite(String userLoginId, Player player, FeedWriteReq feedWriteReq) {
        User user = userRepository.findUserByLoginId(userLoginId).get();
        Feed feed = Feed.builder()
                .player(player)
                .user(user)
                .content(feedWriteReq.getContent())
                .build();

        feedRepository.save(feed);
    }

    @Transactional(readOnly = true)
    public FeedReadWithCommentRes getFeedDetail(Long feedId) {
        Feed feed = feedRepository.findById(feedId).get();

        return FeedReadWithCommentRes.builder()
                .id(feed.getId())
                .userName(feed.getUser().getName())
                .userImgUrl(feed.getUser().getImgUrl())
                .content(feed.getContent())
                .date(feed.getDate())
                .commentList(feed.getCommentList())
                .build();
    }

    @Transactional
    public void commentWrite(String userLoginId, Long id, CommentWriteReq commentWriteReq) {
        User user = userRepository.findUserByLoginId(userLoginId).get();
        Feed feed = feedRepository.findById(id).get();

        Comment comment = Comment.builder()
                .feed(feed)
                .content(commentWriteReq.getContent())
                .user(user)
                .build();

        Comment savedComment = commentRepository.save(comment);

        feed.addComment(savedComment);
        feedRepository.save(feed);
    }
}
