package com.example.mentalCare.player.feed.dto;

import com.example.mentalCare.player.feed.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class FeedReadRes {

    private Long id;

    private String userName;

    private String userImgUrl;

    private String content;

    private LocalDate date;

    private Long commentCnt;

    private String topComment;

    @Builder
    public FeedReadRes(Long id, String userName, String userImgUrl, String content, LocalDate date, List<Comment> commentList) {
        this.id = id;
        this.userName = userName;
        this.userImgUrl = userImgUrl;
        this.content = content;
        this.date = date;
        this.commentCnt = (long) commentList.size();
        this.topComment = null;
        if (this.commentCnt > 0) {
            this.topComment = commentList.get(0).getContent();
        }
    }
}
