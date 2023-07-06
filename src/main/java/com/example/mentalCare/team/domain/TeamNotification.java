package com.example.mentalCare.team.domain;

import javax.persistence.*;

import com.example.mentalCare.director.profile.domain.Director;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TeamNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

    private String title;

    @Column(length=1000)
    private String content;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private Integer view_cnt = 0;

    @Builder
    public TeamNotification(Team team, Director director, String title, String content) {
        this.team = team;
        this.director = director;
        this.title = title;
        this.content = content;
    }

    public void increaseViewCnt() {
        this.view_cnt += 1;
    }
}
