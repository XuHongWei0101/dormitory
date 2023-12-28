package com.rabbit.service.impl;

import com.rabbit.dao.SystemAdminDao;
import com.rabbit.dao.impl.SystemAdminDaoImpl;
import com.rabbit.dto.SystemAdminDto;
import com.rabbit.entity.SystemAdmin;
import com.rabbit.service.SystemAdminService;

public class SystemAdminServiceImpl implements SystemAdminService {

    private SystemAdminDao systemAdminDao = new SystemAdminDaoImpl();

    @Override
    public SystemAdminDto login(String username, String password) {
        //1、通过username查询数据库
        //2、查询结果为空，username错误
        //3、查询结果不为空，再判断password是否正确
        SystemAdmin systemAdmin = this.systemAdminDao.findByUsername(username);
        SystemAdminDto systemAdminDto = new SystemAdminDto();
        if(systemAdmin == null){
            systemAdminDto.setCode(-1);
        } else {
            if(!systemAdmin.getPassword().equals(password)){
                systemAdminDto.setCode(-2);
            } else {
                systemAdminDto.setCode(0);
                systemAdminDto.setSystemAdmin(systemAdmin);
            }
        }
        return systemAdminDto;
    }
}
