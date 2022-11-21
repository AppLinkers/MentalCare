package com.example.mentalCare.director.team.service;

import com.example.mentalCare.director.team.dto.TeamDiagnoseResultReadRes;
import com.example.mentalCare.team.domain.Team;
import com.example.mentalCare.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectorTeamService {

    private final TeamRepository teamRepository;

//    /**
//     * 팀 진단 유형 정보 조회
//     */
//    @Transactional(readOnly = true)
//    public List<TeamDiagnoseResultReadRes> getTeamDiagnoseResultList(Long teamId) {
//        Team team = teamRepository.findById(teamId).get();
//
//
//    }
}
