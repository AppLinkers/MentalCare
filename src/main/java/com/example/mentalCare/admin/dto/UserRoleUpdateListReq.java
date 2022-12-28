package com.example.mentalCare.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserRoleUpdateListReq {

    private List<UserRoleUpdateReq> userRoleUpdateReqList = new ArrayList<>();

    public void add(UserRoleUpdateReq userRoleUpdateReq) {
        this.userRoleUpdateReqList.add(userRoleUpdateReq);
    }

}
