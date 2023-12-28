package com.rabbit.service.impl;

import com.rabbit.dao.AbsentDao;
import com.rabbit.dao.impl.AbsentDaoImpl;
import com.rabbit.entity.Absent;
import com.rabbit.service.AbsentService;

import java.util.List;

public class AbsentServieImpl implements AbsentService {

    private AbsentDao absentDao = new AbsentDaoImpl();

    @Override
    public void save(Absent absent) {
        Integer save = this.absentDao.save(absent);
        if(save != 1) throw new RuntimeException("添加缺寝记录失败");
    }

    @Override
    public List<Absent> list() {
        return this.absentDao.list();
    }

    @Override
    public List<Absent> search(String key, String value) {
        if(value.equals("")) return this.absentDao.list();
        return this.absentDao.search(key, value);
    }
}
