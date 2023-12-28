package com.rabbit.service.impl;

import com.rabbit.dao.DormitoryDao;
import com.rabbit.dao.MoveoutDao;
import com.rabbit.dao.StudentDao;
import com.rabbit.dao.impl.DormitoryDaoImpl;
import com.rabbit.dao.impl.MoveoutDaoImpl;
import com.rabbit.dao.impl.StudentDaoImpl;
import com.rabbit.entity.Moveout;
import com.rabbit.service.MoveoutService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * MoveoutServiceImpl 实现了 MoveoutService 接口，用于处理学生迁出相关的服务
 */
public class MoveoutServiceImpl implements MoveoutService {

    // 数据访问对象的实例化
    private MoveoutDao moveoutDao = new MoveoutDaoImpl();
    private StudentDao studentDao = new StudentDaoImpl();
    private DormitoryDao dormitoryDao = new DormitoryDaoImpl();

    /**
     * 保存学生的迁出记录
     * @param moveout 要保存的迁出信息对象
     */
    @Override
    public void save(Moveout moveout) {
        // 更新学生状态
        Integer updateStateById = this.studentDao.updateStateById(moveout.getStudentId());
        // 增加宿舍的可用床位数
        Integer addAvailable = this.dormitoryDao.addAvailable(moveout.getDormitoryId());
        // 设置迁出日期
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        moveout.setCreateDate(simpleDateFormat.format(date));
        // 保存迁出记录
        Integer save = this.moveoutDao.save(moveout);
        // 如果有任何一个操作不成功，抛出异常
        if(save != 1 || updateStateById != 1 || addAvailable != 1) {
            throw new RuntimeException("迁出学生失败");
        }
    }

    /**
     * 获取所有迁出记录的列表
     * @return 包含所有迁出记录的列表
     */
    @Override
    public List<Moveout> list() {
        return this.moveoutDao.list();
    }

    /**
     * 根据关键字和数值进行搜索
     * @param key 搜索关键字
     * @param value 搜索数值
     * @return 符合搜索条件的迁出记录列表
     */
    @Override
    public List<Moveout> search(String key, String value) {
        // 如果搜索数值为空，返回所有迁出记录
        if(value.equals("")) {
            return this.moveoutDao.list();
        }
        // 否则根据关键字和数值进行搜索
        return this.moveoutDao.search(key, value);
    }
}