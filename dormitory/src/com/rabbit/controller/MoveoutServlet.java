package com.rabbit.controller;

import com.rabbit.entity.Moveout;
import com.rabbit.service.MoveoutService;
import com.rabbit.service.StudentService;
import com.rabbit.service.impl.MoveoutServiceImpl;
import com.rabbit.service.impl.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * MoveoutServlet 是处理迁出记录操作的 Servlet
 */
@WebServlet("/moveout")
public class MoveoutServlet extends HttpServlet {

    private StudentService studentService = new StudentServiceImpl(); // 创建学生服务对象
    private MoveoutService moveoutService = new MoveoutServiceImpl(); // 创建迁出服务对象

    /**
     * 处理 GET 请求
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp); // GET 请求转发到 doPost 方法处理
    }

    /**
     * 处理 POST 请求
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); // 设置请求字符编码为 UTF-8
        String method = req.getParameter("method"); // 获取请求参数 method

        // 根据 method 参数的值执行相应的操作
        switch (method){
            case "list":
                req.setAttribute("list",this.studentService.moveoutList()); // 设置请求属性 list，值为迁出列表
                req.getRequestDispatcher("moveoutregister.jsp").forward(req, resp); // 转发到 moveoutregister.jsp 页面
                break;
            case "moveout":
                String studentIdStr = req.getParameter("studentId"); // 获取请求参数 studentId
                Integer studentId = Integer.parseInt(studentIdStr); // 将 studentId 转换为整数
                String dormitoryIdStr = req.getParameter("dormitoryId"); // 获取请求参数 dormitoryId
                Integer dormitoryId = Integer.parseInt(dormitoryIdStr); // 将 dormitoryId 转换为整数
                String reason = req.getParameter("reason"); // 获取请求参数 reason
                this.moveoutService.save(new Moveout(studentId,dormitoryId,reason)); // 调用迁出服务保存迁出记录
                req.setAttribute("moveoutSuccessMessage", "迁出成功"); // 在迁出成功后设置一个属性来存储迁出成功的消息
                resp.sendRedirect("moveout?method=list"); // 重定向到前端页面
                break;
            case "search":
                String key = req.getParameter("key"); // 获取请求参数 key
                String value = req.getParameter("value"); // 获取请求参数 value
                req.setAttribute("list", this.studentService.searchForMoveout(key, value)); // 设置请求属性 list，值为搜索到的迁出列表
                req.getRequestDispatcher("moveoutregister.jsp").forward(req, resp); // 转发到 moveoutregister.jsp 页面
                break;
            case "record":
                req.setAttribute("list", this.moveoutService.list()); // 设置请求属性 list，值为所有迁出记录列表
                req.getRequestDispatcher("moveoutrecord.jsp").forward(req, resp); // 转发到 moveoutrecord.jsp 页面
                break;
            case "recordSearch":
                key = req.getParameter("key"); // 获取请求参数 key
                value = req.getParameter("value"); // 获取请求参数 value
                req.setAttribute("list", this.moveoutService.search(key, value)); // 设置请求属性 list，值为搜索到的迁出记录列表
                req.getRequestDispatcher("moveoutrecord.jsp").forward(req, resp); // 转发到 moveoutrecord.jsp 页面
                break;
            case "update":
                String idString = req.getParameter("moveoutId"); // 获取前端传递的迁出记录ID
                if (idString != null && !idString.isEmpty()) {
                    int moveoutId = Integer.parseInt(idString); // 获取前端传递的迁出记录ID
                    String updateReason = req.getParameter("reason"); // 获取请求参数reason
                    String updateDate = req.getParameter("date"); // 获取请求参数Date
                    Moveout updatedMoveout = new Moveout(moveoutId, updateDate, updateReason); // 创建更新后的迁出记录对象
                    this.moveoutService.update(updatedMoveout); // 调用迁出服务更新迁出记录
                } else {
                    // 处理id为null或者空字符串的情况，例如抛出异常或者进行其他处理
                    throw new IllegalArgumentException("Moveout ID is null or empty");
                }
        }
    }
}