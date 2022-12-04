package com.example.mentalCare.director.profile.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class DirectorProfileUpdateReq {

    private String teamCode;

    private MultipartFile imgFile;

}
