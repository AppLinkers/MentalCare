package com.example.mentalCare.player.feed.controller;

import com.example.mentalCare.player.feed.service.PlayerFeedService;
import com.example.mentalCare.player.profile.domain.Player;
import com.example.mentalCare.player.profile.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/player/feed")
@RequiredArgsConstructor
public class PlayerFeedController {

    private final PlayerRepository playerRepository;
    private final PlayerFeedService playerFeedService;

    @GetMapping("")
    public String feedPage(
            Model model,
            @RequestParam(name = "id", required = false) Long id
    ) {
        Player player;
        if (id == null) {
            String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
            player = playerRepository.findPlayerByUserLoginId(userLoginId);
        } else {
            player = playerRepository.findById(id).get();
        }

        model.addAttribute("name", player.getUser().getName());
        model.addAttribute("feedReadResList", playerFeedService.getPlayerFeedList(player.getId()));

        return player.getUser().getName();
    }
}
