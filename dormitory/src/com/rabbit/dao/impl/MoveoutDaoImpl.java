package com.rabbit.dao.impl;

import com.rabbit.dao.MoveoutDao;
import com.rabbit.entity.Moveout;
import com.rabbit.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * MoveoutDaoImpl 实现了 MoveoutDao 接口，用于处理迁出记录相关的数据库操作
 */
public class MoveoutDaoImpl implements MoveoutDao {

    /**
     * 保存迁出记录到数据库
     * @param moveout 要保存的迁出记录对象
     * @return 数据库操作影响的行数
     */
    @Override
    public Integer save(Moveout moveout) {
        // 获取数据库连接
        Connection connection = JDBCUtil.getConnection();
        // 构造 SQL 语句
        String sql = "insert into moveout(student_id, dormitory_id, reason, create_date) values(?,?,?,?)";
        PreparedStatement statement = null;
        Integer result = null;
        try {
            // 创建 PreparedStatement 对象
            statement = connection.prepareStatement(sql);
            // 设置参数
            statement.setInt(1, moveout.getStudentId());
            statement.setInt(2, moveout.getDormitoryId());
            statement.setString(3, moveout.getReason());
            statement.setString(4, moveout.getCreateDate());
            // 执行 SQL 语句
            result = statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 释放资源
            JDBCUtil.release(connection, statement, null);
        }
        return result;
    }

    /**
     * 获取所有迁出记录的列表
     * @return 包含所有迁出记录的列表
     */
    @Override
    public List<Moveout> list() {
        // 获取数据库连接
        Connection connection = JDBCUtil.getConnection();
        // 构造 SQL 语句
        String sql = "select m.id, s.name, d.name, m.reason, m.create_date from moveout m, student s, dormitory d where m.student_id = s.id and m.dormitory_id = d.id";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Moveout> list = new ArrayList<>();
        try {
            // 创建 PreparedStatement 对象
            statement = connection.prepareStatement(sql);
            // 执行 SQL 查询，获取结果集
            resultSet = statement.executeQuery();
            // 遍历结果集并将数据加入列表
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String studentName = resultSet.getString(2);
                String dormitoryName = resultSet.getString(3);
                String reason = resultSet.getString(4);
                String createDate = resultSet.getString(5);
                list.add(new Moveout(id, studentName, dormitoryName, reason, createDate));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 释放资源
            JDBCUtil.release(connection, statement, resultSet);
        }
        return list;
    }

    /**
     * 根据关键字和数值进行搜索迁出记录
     * @param key 搜索关键字
     * @param value 搜索数值
     * @return 符合搜索条件的迁出记录列表
     */
    @Override
    public List<Moveout> search(String key, String value) {
        // 获取数据库连接
        Connection connection = JDBCUtil.getConnection();
        // 构造 SQL 语句
        String sql = "select m.id, s.name, d.name, m.reason, m.create_date from moveout m, student s, dormitory d where m.student_id = s.id and m.dormitory_id = d.id";
        String keyStatement = "";
        if(key.equals("studentName")){
            keyStatement = " and s.name";
        }
        if(key.equals("dormitoryName")){
            keyStatement = " and d.name";
        }
        sql = sql + keyStatement + " like '%"+value+"%'";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Moveout> list = new ArrayList<>();
        try {
            // 创建 PreparedStatement 对象
            statement = connection.prepareStatement(sql);
            // 执行 SQL 查询，获取结果集
            resultSet = statement.executeQuery();
            // 遍历结果集并将数据加入列表
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String studentName = resultSet.getString(2);
                String dormitoryName = resultSet.getString(3);
                String reason = resultSet.getString(4);
                String createDate = resultSet.getString(5);
                list.add(new Moveout(id, studentName, dormitoryName, reason, createDate));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 释放资源
            JDBCUtil.release(connection, statement, resultSet);
        }
        return list;
    }
}