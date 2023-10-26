package com.example.mentalCare.consultant.profile.domain;

import com.example.mentalCare.common.domain.User;
import com.example.mentalCare.player.feed.domain.Comment;
import com.example.mentalCare.player.profile.domain.Player;
import com.example.mentalCare.player.test.domain.AnswerDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Consultant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "consultant")
    private List<Player> playerList = new ArrayList<>();

    public void addPlayer(Player player) {
        this.playerList.add(player);
    }

}
