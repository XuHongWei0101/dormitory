package com.rabbit.dto;

import com.rabbit.entity.DormitoryAdmin;

public class DormitoryAdminDto {
    private Integer code;
    private DormitoryAdmin dormitoryAdmin;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public DormitoryAdmin getDormitoryAdmin() {
        return dormitoryAdmin;
    }

    public void setDormitoryAdmin(DormitoryAdmin dormitoryAdmin) {
        this.dormitoryAdmin = dormitoryAdmin;
    }
}
