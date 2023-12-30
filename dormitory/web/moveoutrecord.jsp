<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <!-- 引入 Bootstrap -->
    <script src="js/jquery-2.2.3.min.js"></script>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 引入 font-awesome -->
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <script src="js/bootstrap.js"></script>
    <title>宿舍管理系统</title>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-10">

            <!-- 顶部搜索部分 -->
            <div class="panel panel-default">
                <div class="panel-heading">搜索</div>
                <div class="panel-body">
                    <form role="form" class="form-inline" action="moveout?method=recordSearch" method="post">
                        <div class="form-group">
                            <%--@declare id="name"--%><label for="name">字段：</label>
                            <select name="key" class="form-control">
                                <option value="studentName">学生</option>
                                <option value="dormitoryName">宿舍</option>
                            </select>
                        </div>
                        <div class="form-group" style="margin-left: 20px">
                            <%--@declare id="value"--%><label for="value">值：</label>
                            <input type="text" class="form-control" name="value" placeholder="字段值" maxlength="12" style="width: 130px">
                        </div>
                        <div class="form-group " style="margin-left: 20px">
                            <button type="submit" class="btn btn-info ">
										<span style="margin-right: 5px"
                                              class="glyphicon glyphicon-search" aria-hidden="true">
										</span>开始搜索
                            </button>
                        </div>
                    </form>
                </div>
            </div>
            <!-- 列表展示-->
            <div class="table-responsive">
                <table class="table table-hover ">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>学生</th>
                        <th>宿舍</th>
                        <th>迁出原因</th>
                        <th>迁出日期</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="moveout">
                        <tr>
                            <td>${moveout.id}</td>
                            <td>${moveout.studentName}</td>
                            <td>${moveout.dormitoryName}</td>
                            <td>${moveout.reason}</td>
                            <td>${moveout.createDate}</td>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default "
                                            data-id="${moveout.id}"
                                            data-reason="${moveout.reason}"
                                            data-toggle="modal"
                                            data-target="#updateMoveoutModal">
                                        <i class="fa fa-edit"></i> <i>修改</i>
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <!-- update框示例（Modal） -->
                <form method="post" action="moveout?method=update" class="form-horizontal" style="margin-top: 0px" role="form"
                      id="form_data" style="margin: 20px;">
                    <div class="modal fade" id="updateMoveoutModal" tabindex="-1"
                         role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">x</button>
                                    <h4 class="modal-title" id="myModalLabel">修改迁出记录信息</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">

                                        <div class="form-group">
                                            <label for="moveout_id" class="col-sm-3 control-label">ID</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="moveout_id"
                                                       name="moveoutId" value="${moveout.id}" placeholder="请输入迁出记录ID">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="moveout_reason" class="col-sm-3 control-label">迁出原因</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="moveout_reason"
                                                       name="reason" value="${moveout.reason}" placeholder="请输入迁出原因">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="moveout_date" class="col-sm-3 control-label">迁出日期</label>
                                            <div class="col-sm-9">
                                                <input type="date" required class="form-control" id="moveout_date"
                                                       name="date" value="${moveout.createDate}" placeholder="请输入迁出日期">
                                            </div>
                                        </div>

                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                    <button type="submit" class="btn btn-primary">提交</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>


            </div>
        </div>
    </div>
</div>
</div>

<script>
    // 检查是否需要刷新页面
    var list = ${list};
    if (list.length === 0 && !sessionStorage.getItem("refreshed")) {
        alert("没有该信息");
        sessionStorage.setItem("refreshed", "true"); // 记录已经刷新过
        location.reload();
    } else {
        // 清除标记，以便下次搜索时重新刷新页面
        sessionStorage.removeItem("refreshed");
    }
</script>

<script>
    $(document).ready(function() {
        // 监听表单提交事件
        $("#form_data").submit(function(event) {
            // 阻止表单自动提交
            event.preventDefault();

            // 使用 AJAX 提交表单数据
            $.ajax({
                type: "POST",
                url: "moveout?method=update",
                data: $("#form_data").serialize(), // 序列化表单数据
                success: function(response) {
                    // 更新成功后弹出提示框
                    alert("更新成功");
                    // 关闭模态框
                    $("#updateMoveoutModal").modal("hide");
                    // 刷新页面
                    location.reload();
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    // 更新失败后的处理
                    alert("更新失败，请重试");
                }
            });
        });
    });
</script>

</body>

</html>