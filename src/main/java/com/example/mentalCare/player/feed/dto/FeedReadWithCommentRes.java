package com.example.mentalCare.player.feed.dto;

import com.example.mentalCare.player.feed.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class FeedReadWithCommentRes {

    private Long id;

    private String userLoginId;

    private String userName;

    private String userImgUrl;

    private String content;

    private String url;

    private LocalDate date;

    private List<CommentReadRes> commentList;

    @Builder
    public FeedReadWithCommentRes(Long id, String userLoginId, String userName, String userImgUrl, String content,String url ,LocalDate date, List<Comment> commentList) {
        this.id = id;
        this.userLoginId = userLoginId;
        this.userName = userName;
        this.userImgUrl = userImgUrl;
        this.content = content;
        this.url = url;
        this.date = date;
        this.commentList = new ArrayList<>();

        for (Comment comment : commentList) {
            this.commentList.add(
                    CommentReadRes.builder()
                            .id(comment.getId())
                            .userLoginId(comment.getUser().getLogin_id())
                            .userName(comment.getUser().getName())
                            .userImgUrl(comment.getUser().getImgUrl())
                            .content(comment.getContent())
                            .date(comment.getDate())
                            .build()
            );
        }
    }
}
