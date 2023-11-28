package com.example.mentalCare.player.consulting.service;

import com.example.mentalCare.consultant.profile.domain.Consultant;
import com.example.mentalCare.consultant.profile.repository.ConsultantRepository;
import com.example.mentalCare.player.consulting.domain.RequestConsulting;
import com.example.mentalCare.player.consulting.dto.ConsultantInfoReadRes;
import com.example.mentalCare.player.consulting.repository.RequestConsultingRepository;
import com.example.mentalCare.player.profile.domain.Player;
import com.example.mentalCare.player.profile.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerConsultingService {

    private final RequestConsultingRepository requestConsultingRepository;
    private final ConsultantRepository consultantRepository;
    private final PlayerRepository playerRepository;


    public List<ConsultantInfoReadRes> getConsultantInfoReadResList(String userLoginId) {
        List<ConsultantInfoReadRes> response =new ArrayList<>();
        List<Consultant> consultantList = consultantRepository.findAll();

        consultantList.forEach(
                consultant -> {
                    Boolean hasRequest = Boolean.FALSE;
                    if (requestConsultingRepository.findByConsultantAndPlayerUserLogin_id(consultant, userLoginId).isPresent()) {
                        hasRequest = Boolean.TRUE;
                    }

                    ConsultantInfoReadRes consultantInfoReadRes = ConsultantInfoReadRes.builder()
                            .id(consultant.getId())
                            .imgUrl(consultant.getUser().getImgUrl())
                            .name(consultant.getUser().getName())
                            .age(consultant.getUser().getAge())
                            .hasRequest(hasRequest)
                            .build();

                    response.add(consultantInfoReadRes);
                }
        );

        return response;
    }

    public void requestConsulting(String userLoginId, Long consultantId) {
        Player player = playerRepository.findPlayerByUserLoginId(userLoginId);
        Consultant consultant = consultantRepository.findById(consultantId).get();

        RequestConsulting requestConsulting = RequestConsulting.builder()
                .player(player)
                .consultant(consultant)
                .build();

        requestConsultingRepository.save(requestConsulting);
    }
}
