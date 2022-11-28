package com.example.mentalCare.director.team.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DirectorRoleUpdateListReq {

    private List<DirectorRoleUpdateReq> directorRoleUpdateReqList = new ArrayList<>();

    public void add(DirectorRoleUpdateReq directorRoleUpdateReq) {
        this.directorRoleUpdateReqList.add(directorRoleUpdateReq);
    }
}
