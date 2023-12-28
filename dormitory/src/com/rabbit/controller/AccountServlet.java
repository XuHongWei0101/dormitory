package com.rabbit.controller;

import com.rabbit.dto.DormitoryAdminDto;
import com.rabbit.dto.SystemAdminDto;
import com.rabbit.service.DormitoryAdminService;
import com.rabbit.service.SystemAdminService;
import com.rabbit.service.impl.DormitoryAdminServiceImpl;
import com.rabbit.service.impl.SystemAdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * AccountServlet 是处理账户操作的 Servlet
 */
@WebServlet("/account")
public class AccountServlet extends HttpServlet {

    private SystemAdminService systemAdminService = new SystemAdminServiceImpl(); // 创建系统管理员服务对象
    private DormitoryAdminService dormitoryAdminService = new DormitoryAdminServiceImpl(); // 创建宿舍管理员服务对象

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
        String method = req.getParameter("method"); // 获取请求参数 method

        // 根据 method 参数的值执行相应的操作
        switch (method){
            case "login": // 处理登录操作
                String username = req.getParameter("username"); // 获取请求参数 username
                String password = req.getParameter("password"); // 获取请求参数 password
                String type = req.getParameter("type"); // 获取请求参数 type

                switch (type){
                    case "systemAdmin": // 如果是系统管理员登录
                        SystemAdminDto systemAdminDto = this.systemAdminService.login(username, password); // 调用系统管理员服务进行登录
                        switch (systemAdminDto.getCode()){ // 根据返回结果的状态码进行处理
                            case -1: // 如果用户名不存在
                                req.setAttribute("usernameError", "用户名不存在"); // 在请求中设置用户名错误提示信息
                                req.getRequestDispatcher("login.jsp").forward(req, resp); // 转发到登录页面，并带上错误信息
                                break;
                            case -2: // 如果密码错误
                                req.setAttribute("passwordError", "密码错误"); // 在请求中设置密码错误提示信息
                                req.getRequestDispatcher("login.jsp").forward(req, resp); // 转发到登录页面，并带上错误信息
                                break;
                            case 0: // 登录成功
                                HttpSession session = req.getSession(); // 获取 HttpSession 对象
                                session.setAttribute("systemAdmin", systemAdminDto.getSystemAdmin()); // 将系统管理员信息存入 session
                                resp.sendRedirect(req.getContextPath() + "/systemadmin.jsp"); // 重定向到系统管理员界面
                                break;
                        }
                        break;
                    case "dormitoryAdmin": // 如果是宿舍管理员登录
                        DormitoryAdminDto dormitoryAdminDto = this.dormitoryAdminService.login(username, password); // 调用宿舍管理员服务进行登录
                        switch (dormitoryAdminDto.getCode()){ // 根据返回结果的状态码进行处理
                            case -1: // 如果用户名不存在
                                req.setAttribute("usernameError", "用户名不存在"); // 在请求中设置用户名错误提示信息
                                req.getRequestDispatcher("login.jsp").forward(req, resp); // 转发到登录页面，并带上错误信息
                                break;
                            case -2: // 如果密码错误
                                req.setAttribute("passwordError", "密码错误"); // 在请求中设置密码错误提示信息
                                req.getRequestDispatcher("login.jsp").forward(req, resp); // 转发到登录页面，并带上错误信息
                                break;
                            case 0: // 登录成功
                                HttpSession session = req.getSession(); // 获取 HttpSession 对象
                                session.setAttribute("dormitoryAdmin", dormitoryAdminDto.getDormitoryAdmin()); // 将宿舍管理员信息存入 session
                                resp.sendRedirect(req.getContextPath() + "/dormitoryadmin.jsp"); // 重定向到宿舍管理员界面
                                break;
                        }
                        break;
                }
                break;
            case "logout": // 处理注销操作
                req.getSession().invalidate(); // 使当前会话失效，即注销用户
                resp.sendRedirect(req.getContextPath() + "/login.jsp"); // 重定向到登录页面
                break;
        }

    }
}