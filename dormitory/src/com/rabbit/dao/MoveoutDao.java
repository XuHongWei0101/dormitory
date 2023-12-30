package com.rabbit.dao;

import com.rabbit.entity.Moveout;

import java.util.List;

public interface MoveoutDao {
    // 保存一条迁出记录，并返回保存后的记录ID
    public Integer save(Moveout moveout);
    // 获取所有迁出记录的列表
    public List<Moveout> list();
    // 根据关键字和数值进行迁出记录的搜索
    public List<Moveout> search(String key,String value);
    // 更新迁出记录信息
    public void update(Moveout moveout);
}
