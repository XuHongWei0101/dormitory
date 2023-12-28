package com.rabbit.dao;

import com.rabbit.entity.Absent;

import java.util.List;

public interface AbsentDao {
    public Integer save(Absent absent);
    public List<Absent> list();
    public List<Absent> search(String key,String value);
}
