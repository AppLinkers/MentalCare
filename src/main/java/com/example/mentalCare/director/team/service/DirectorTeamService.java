package com.example.mentalCare.director.team.service;

import com.example.mentalCare.common.domain.type.Role;
import com.example.mentalCare.director.profile.domain.Director;
import com.example.mentalCare.director.profile.repository.DirectorRepository;
import com.example.mentalCare.director.team.dto.*;
import com.example.mentalCare.player.profile.domain.Player;
import com.example.mentalCare.player.profile.repository.PlayerRepository;
import com.example.mentalCare.player.test.domain.Answer;
import com.example.mentalCare.player.test.domain.AnswerDiagnose;
import com.example.mentalCare.player.test.repository.AnswerRepository;
import com.example.mentalCare.player.test.repository.DiagnoseRepository;
import com.example.mentalCare.team.domain.TeamNotification;
import com.example.mentalCare.team.repository.TeamNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DirectorTeamService {

    private final PlayerRepository playerRepository;
    private final DirectorRepository directorRepository;
    private final AnswerRepository answerRepository;
    private final DiagnoseRepository diagnoseRepository;
    private final TeamNotificationRepository teamNotificationRepository;

    /**
     * 팀 진단 유형 정보 조회
     * 쿼리문으로 작성해보기 (Group By, Join 이용)
     */
    @Transactional(readOnly = true)
    public List<TeamDiagnoseResultReadRes> getTeamDiagnoseResultList(Long teamId) {
        Map<Long, List<Double>> resultMap = new HashMap<>();

        List<Player> playerList = playerRepository.findPlayerByUserTeamId(teamId);

        for (Player player : playerList) {
            Long userId = player.getUser().getId();
            if (answerRepository.existsByPlayer_UserId(userId)) {
                Answer answer = answerRepository.getFirstByPlayerUserIdOrderByUpdatedAtDesc(userId);
                for (AnswerDiagnose answerDiagnose : answer.getAnswerDiagnoseList()) {
                    Long diagnoseId = answerDiagnose.getDiagnose().getId();
                    Double diagnoseAvg = answerDiagnose.getAvg();

                    if (resultMap.containsKey(diagnoseId)) {
                        List<Double> diagnoseAvgList = resultMap.get(diagnoseId);
                        diagnoseAvgList.add(diagnoseAvg);
                        resultMap.put(diagnoseId, diagnoseAvgList);
                    } else {
                        resultMap.put(diagnoseId, List.of(diagnoseAvg));
                    }
                }
            }
        }

        List<TeamDiagnoseResultReadRes> result = new ArrayList<>();
        for (Map.Entry<Long, List<Double>> entry : resultMap.entrySet()) {
            Long diagnoseId = entry.getKey();
            List<Double> diagnoseAvgList = entry.getValue();

            Double totalDiagnoseAvg = 0.0;
            for (Double diagnoseAvg : diagnoseAvgList) {
                totalDiagnoseAvg += diagnoseAvg;
            }
            totalDiagnoseAvg /= diagnoseAvgList.size();

            result.add(
                    TeamDiagnoseResultReadRes.builder()
                            .title(diagnoseRepository.findById(diagnoseId).get().getTitle())
                            .avg(totalDiagnoseAvg)
                            .build()
            );

        }

        return result;
    }

    /**
     * 감독 정보 목록 조회
     */
    @Transactional(readOnly = true)
    public List<TeamDirectorInfoReadRes> getTeamDirectorInfoList(Long teamId) {
        List<Director> directorList = directorRepository.findDirectorByUserTeamId(teamId);

        List<TeamDirectorInfoReadRes> result = new ArrayList<>();

        for (Director director : directorList) {
            result.add(
                    TeamDirectorInfoReadRes.builder()
                            .id(director.getId())
                            .imgUrl(director.getUser().getImgUrl())
                            .name(director.getUser().getName())
                            .role(director.getUser().getRole())
                            .age(director.getUser().getAge())
                            .build()
            );
        }

        return result;
    }

    /**
     * 선수 정보 목록 조회
     */
    @Transactional(readOnly = true)
    public List<TeamPlayerInfoReadRes> getTeamPlayerInfoList(Long teamId) {
        List<Player> playerList = playerRepository.findPlayerByUserTeamId(teamId);

        List<TeamPlayerInfoReadRes> result = new ArrayList<>();

        for (Player player : playerList) {
            result.add(
                    TeamPlayerInfoReadRes.builder()
                            .id(player.getId())
                            .imgUrl(player.getUser().getImgUrl())
                            .name(player.getUser().getName())
                            .role(player.getUser().getRole())
                            .position(player.getPosition())
                            .age(player.getUser().getAge())
                            .build()
            );
        }

        return result;
    }

    /**
     * 감독 정보 목록 -> 감독 권한 수정 정보 request
     */
    public DirectorRoleUpdateListReq teamDirectorInfoListToDirectorRoleUpdateListReq(List<TeamDirectorInfoReadRes> teamDirectorInfoList) {
        DirectorRoleUpdateListReq result = new DirectorRoleUpdateListReq();
        for (TeamDirectorInfoReadRes teamDirectorInfoReadRes : teamDirectorInfoList) {
            result.add(
                    DirectorRoleUpdateReq.builder()
                            .id(teamDirectorInfoReadRes.getId())
                            .role(teamDirectorInfoReadRes.getRole())
                            .build()
            );
        }

        return result;
    }

    /**
     * 감독 권한 변경 서비스
     */
    @Transactional
    public void changeDirectorRoleList(DirectorRoleUpdateListReq directorRoleUpdateListReq) {
        for (DirectorRoleUpdateReq directorRoleUpdateReq : directorRoleUpdateListReq.getDirectorRoleUpdateReqList()) {
            Director director = directorRepository.findById(directorRoleUpdateReq.getId()).get();

            Role directorRole = director.getUser().getRole();
            if (!directorRole.equals(directorRoleUpdateReq.getRole())) {
                director.getUser().setRole(directorRoleUpdateReq.getRole());
            }

        }
    }

    /**
     * 선수 상세 정보 조회
     */
    @Transactional(readOnly = true)
    public TeamPlayerDetailReadRes getTeamPlayerDetail(Long playerId) {
        Player player = playerRepository.findById(playerId).get();
        Long userId = player.getUser().getId();

        List<Double> avgList = new ArrayList<>();
        LocalDate answerDate = null;

        if (answerRepository.existsByPlayer_UserId(userId)) {
            Answer answer = answerRepository.getFirstByPlayerUserIdOrderByUpdatedAtDesc(userId);
            answerDate = answer.getUpdatedAt().toLocalDate();
            for (AnswerDiagnose answerDiagnose : answer.getAnswerDiagnoseList()) {
                avgList.add(answerDiagnose.getAvg());
            }
        }


        return TeamPlayerDetailReadRes.builder()
                .id(playerId)
                .name(player.getUser().getName())
                .imgUrl(player.getUser().getImgUrl())
                .role(player.getUser().getRole())
                .position(player.getPosition())
                .age(player.getUser().getAge())
                .avgList(avgList)
                .answerDate(answerDate)
                .build();
    }

    /**
     * 선수 상세 정보 -> 선수 포지션 및 권한 변경 정보 request
     */
    public PlayerInfoUpdateReq teamPlayerDetailReadResToPlayerInfoUpdateReq(TeamPlayerDetailReadRes teamPlayerDetailReadRes) {
        return PlayerInfoUpdateReq.builder()
                .id(teamPlayerDetailReadRes.getId())
                .position(teamPlayerDetailReadRes.getPosition())
                .role(teamPlayerDetailReadRes.getRole())
                .build();
    }

    /**
     * 선수 포지션 및 권한 변경 서비스
     */
    @Transactional
    public void changePlayerInfo(PlayerInfoUpdateReq playerInfoUpdateReq) {
        Player player = playerRepository.findById(playerInfoUpdateReq.getId()).get();

        player.setPosition(playerInfoUpdateReq.getPosition());
        player.getUser().setRole(playerInfoUpdateReq.getRole());
    }

    /**
     * 팀 공지사항 작성 서비스
     */
    @Transactional
    public void notificationWrite(String userLoginId, TeamNotificationWriteReq teamNotificationWriteReq) {
        Director director = directorRepository.findDirectorByUserLogin_id(userLoginId);
        TeamNotification teamNotification = TeamNotification.builder()
                .team(director.getUser().getTeam())
                .director(director)
                .title(teamNotificationWriteReq.getTitle())
                .content(teamNotificationWriteReq.getContent())
                .build();

        teamNotificationRepository.save(teamNotification);
    }
}
