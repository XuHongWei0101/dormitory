package com.rabbit.dao;

import com.rabbit.entity.SystemAdmin;

public interface SystemAdminDao {
    public SystemAdmin findByUsername(String username);
}
