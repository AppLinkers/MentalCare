package com.example.mentalCare.player.feed.controller;

import com.example.mentalCare.player.feed.dto.CommentWriteReq;
import com.example.mentalCare.player.feed.dto.FeedWriteReq;
import com.example.mentalCare.player.feed.service.PlayerFeedService;
import com.example.mentalCare.player.profile.domain.Player;
import com.example.mentalCare.player.profile.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        return "z-renew/player/feed";
    }

    @GetMapping("/form")
    public String feedWritePage(
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
        model.addAttribute("feedWriteReq", new FeedWriteReq());

        return "z-renew/player/feed_form";
    }

    @PostMapping("")
    public String feedWrite(
            @RequestParam(name = "id", required = false) Long id,
            FeedWriteReq feedWriteReq
    ) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        Player player;
        if (id == null) {
            player = playerRepository.findPlayerByUserLoginId(userLoginId);
        } else {
            player = playerRepository.findById(id).get();
        }

        playerFeedService.feedWrite(userLoginId, player, feedWriteReq);

        return "redirect:/player/feed?id=" + id;
    }

    @GetMapping("/{id}/detail")
    public String feedDetailPage(Model model, @PathVariable Long id) {
        model.addAttribute("feedReadResWithCommentList", playerFeedService.getFeedDetail(id));
        model.addAttribute("commentWriteReq", new CommentWriteReq());

        return "z-renew/player/feed_detail";
    }

    @PostMapping("/{id}/comment")
    public String commentWrite(@PathVariable Long id, CommentWriteReq commentWriteReq) {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        playerFeedService.commentWrite(userLoginId, id, commentWriteReq);

        return "redirect:/player/feed/" + id + "/detail";
    }
}
