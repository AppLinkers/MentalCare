package com.example.mentalCare.player.feed.domain;

import com.example.mentalCare.common.domain.User;
import com.example.mentalCare.player.profile.domain.Player;
import com.example.mentalCare.player.test.domain.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    private LocalDate date;

    @Builder.Default
    @OneToMany(mappedBy = "feed")
    private List<Comment> commentList = new ArrayList<>();

    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }

    private String content;

}
