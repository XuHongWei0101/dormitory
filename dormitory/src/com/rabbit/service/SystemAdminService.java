package com.rabbit.service;

import com.rabbit.dto.SystemAdminDto;

public interface SystemAdminService {
    public SystemAdminDto login(String username,String password);
}
