package com.rabbit.service;

import com.rabbit.entity.Moveout;

import java.util.List;

public interface MoveoutService {
    // 保存一条迁出记录
    public void save(Moveout moveout);
    // 获取所有迁出记录列表
    public List<Moveout> list();
    // 根据关键字和数值进行迁出记录的搜索
    public List<Moveout> search(String key,String value);
}
