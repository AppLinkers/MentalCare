package com.example.mentalCare.player.consulting.domain;

import com.example.mentalCare.consultant.profile.domain.Consultant;
import com.example.mentalCare.player.profile.domain.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestConsulting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "consultant_id")
    private Consultant consultant;
}
