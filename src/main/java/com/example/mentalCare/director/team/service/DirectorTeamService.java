package com.example.mentalCare.director.team.service;

import com.example.mentalCare.common.domain.type.Role;
import com.example.mentalCare.consultant.profile.domain.Consultant;
import com.example.mentalCare.consultant.profile.repository.ConsultantRepository;
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

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class DirectorTeamService {

    private final PlayerRepository playerRepository;
    private final DirectorRepository directorRepository;
    private final ConsultantRepository consultantRepository;
    private final AnswerRepository answerRepository;
    private final DiagnoseRepository diagnoseRepository;


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
                Answer answer = answerRepository.getFirstByPlayerUserIdOrderByCreatedAtDesc(userId);
                for (AnswerDiagnose answerDiagnose : answer.getAnswerDiagnoseList()) {
                    Long diagnoseId = answerDiagnose.getDiagnose().getId();
                    Double diagnoseAvg = answerDiagnose.getAvg();

                    if (resultMap.containsKey(diagnoseId)) {
                        List<Double> diagnoseAvgList = resultMap.get(diagnoseId);
                        diagnoseAvgList.add(diagnoseAvg);
                        resultMap.put(diagnoseId, diagnoseAvgList);
                    } else {
                        resultMap.put(diagnoseId, new ArrayList<>(List.of(diagnoseAvg)));
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

        for (Director teamDirector : directorList) {
            result.add(
                    TeamDirectorInfoReadRes.builder()
                            .id(teamDirector.getId())
                            .imgUrl(teamDirector.getUser().getImgUrl())
                            .name(teamDirector.getUser().getName())
                            .role(teamDirector.getUser().getRole())
                            .build()
            );
        }

        return result;
    }

    /**
     * 감독 정보 조회
     */
    @Transactional(readOnly = true)
    public TeamDirectorInfoReadRes getTeamDirectorDetail(Long directorId) {
        Director director = directorRepository.findById(directorId).get();

        return TeamDirectorInfoReadRes.builder()
                .id(director.getId())
                .imgUrl(director.getUser().getImgUrl())
                .name(director.getUser().getName())
                .teamName(director.getUser().getTeam().getName())
                .role(director.getUser().getRole())
                .build();
    }

    /**
     * 감독 권한 변경 서비스
     */
    @Transactional
    public void changeDirectorRole(DirectorRoleUpdateReq directorRoleUpdateReq) {
        Director director = directorRepository.findById(directorRoleUpdateReq.getId()).get();

        Role directorRole = director.getUser().getRole();
        if (!directorRole.equals(directorRoleUpdateReq.getRole())) {
            director.getUser().setRole(directorRoleUpdateReq.getRole());
        }
    }

    /**
     * 감독 정보 목록 조회
     */
    @Transactional(readOnly = true)
    public List<TeamConsultantInfoReadRes> getTeamConsultantInfoList(Long teamId) {
        List<Consultant> consultantList = consultantRepository.findConsultantByUserTeamId(teamId);

        List<TeamConsultantInfoReadRes> result = new ArrayList<>();

        for (Consultant teamConsultant : consultantList) {
            result.add(
                    TeamConsultantInfoReadRes.builder()
                            .id(teamConsultant.getId())
                            .imgUrl(teamConsultant.getUser().getImgUrl())
                            .name(teamConsultant.getUser().getName())
                            .role(teamConsultant.getUser().getRole())
                            .build()
            );
        }

        return result;
    }

    /**
     * 감독 정보 조회
     */
    @Transactional(readOnly = true)
    public TeamConsultantInfoReadRes getTeamConsultantDetail(Long consultantId) {
        Consultant consultant = consultantRepository.findById(consultantId).get();

        return TeamConsultantInfoReadRes.builder()
                .id(consultant.getId())
                .imgUrl(consultant.getUser().getImgUrl())
                .name(consultant.getUser().getName())
                .teamName(consultant.getUser().getTeam().getName())
                .role(consultant.getUser().getRole())
                .build();
    }

    /**
     * 감독 권한 변경 서비스
     */
    @Transactional
    public void changeConsultantRole(ConsultantRoleUpdateReq consultantRoleUpdateReq) {
        Consultant consultant = consultantRepository.findById(consultantRoleUpdateReq.getId()).get();

        Role consultantRole = consultant.getUser().getRole();
        if (!consultantRole.equals(consultantRoleUpdateReq.getRole())) {
            consultant.getUser().setRole(consultantRoleUpdateReq.getRole());
        }
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
                            .build()
            );
        }

        return result;
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
            Answer answer = answerRepository.getFirstByPlayerUserIdOrderByCreatedAtDesc(userId);
            answerDate = answer.getCreatedAt().toLocalDate();
            for (AnswerDiagnose answerDiagnose : answer.getAnswerDiagnoseList()) {
                avgList.add(answerDiagnose.getAvg());
            }
        }

        Double totalAvg = 0.0;
        for (Double avg : avgList) {
            totalAvg += avg;
        }
        totalAvg /= avgList.size();
        totalAvg = Math.round(totalAvg * 100.0) / 100.0;

        StringBuilder nextMatchDDay = new StringBuilder();

        if (player.getNextMatch() != null) {
            Integer nextMatchDDayNum = (int) DAYS.between(player.getNextMatch(), LocalDate.now());

            if (nextMatchDDayNum >= 0) {
                nextMatchDDay.append("+ ");
            } else {
                nextMatchDDay.append("- ");
                nextMatchDDayNum *= -1;
            }
            nextMatchDDay.append(nextMatchDDayNum);
        }

        return TeamPlayerDetailReadRes.builder()
                .id(playerId)
                .name(player.getUser().getName())
                .teamName(player.getUser().getTeam().getName())
                .nextMatchDDay(nextMatchDDay.toString())
                .imgUrl(player.getUser().getImgUrl())
                .role(player.getUser().getRole())
                .position(player.getPosition())
                .age(player.getUser().getAge())
                .avg(totalAvg)
                .answerDate(answerDate)
                .build();
    }

    /**
     * 선수 포지션 및 권한 변경 서비스
     */
    @Transactional
    public void changePlayerRole(PlayerRoleUpdateReq playerInfoUpdateReq) {
        Player player = playerRepository.findById(playerInfoUpdateReq.getId()).get();

        player.getUser().setRole(playerInfoUpdateReq.getRole());
    }
}
