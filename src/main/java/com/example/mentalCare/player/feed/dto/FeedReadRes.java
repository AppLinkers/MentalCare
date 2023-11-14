package com.example.mentalCare.player.feed.dto;

import com.example.mentalCare.player.feed.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class FeedReadRes {

    private Long id;

    private String userLoginId;

    private String userName;

    private String userImgUrl;

    private String content;
    private String url;

    private LocalDate date;

    private Long commentCnt;

    private String topComment;

    private String topCommentWriter;

    @Builder
    public FeedReadRes(Long id, String userLoginId, String userName, String userImgUrl, String content,String url ,LocalDate date, List<Comment> commentList, String topCommentWriter) {
        this.id = id;
        this.userLoginId = userLoginId;
        this.userName = userName;
        this.userImgUrl = userImgUrl;
        this.content = content;
        this.url = url;
        this.date = date;
        this.commentCnt = (long) commentList.size();
        this.topComment = null;
        this.topCommentWriter = null;
        if (this.commentCnt > 0) {
            this.topComment = commentList.get(0).getContent();
            this.topCommentWriter = commentList.get(0).getUser().getName();
        }
    }
}
