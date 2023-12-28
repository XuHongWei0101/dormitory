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
                    <form role="form" class="form-inline" action="moveout?method=search" method="post">
                        <div class="form-group">
                            <%--@declare id="name"--%><label for="name">字段：</label>
                            <select name="key" class="form-control">
                                <option value="number">学号</option>
                                <option value="name">姓名</option>
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
                        <th>宿舍</th>
                        <th>学号</th>
                        <th>姓名</th>
                        <th>性别</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="student">
                        <tr>
                            <td>${student.id}</td>
                            <td>${student.dormitoryName}</td>
                            <td>${student.number}</td>
                            <td>${student.name}</td>
                            <td>${student.gender}</td>
                            <td>${student.state}</td>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-danger"
                                            data-id="${student.id}"
                                            data-dormitory-id="${student.dormitoryId}"
                                            data-toggle="modal"
                                            data-target="#delUserModal">
                                        <i class="fa fa-user-o">迁出</i>
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <!-- 迁出模态框示例（Modal） -->
                <form method="post" action="moveout?method=moveout"
                      class="form-horizontal" style="margin-top: 0px" role="form"
                      id="form_data" style="margin: 20px;">

                    <div class="modal fade" id="delUserModal" tabindex="-1"
                         role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">×</button>
                                    <h4 class="modal-title" id="myModalLabel">用户信息</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">

                                        <div class="form-group">
                                            <div class="col-sm-9">
                                                <h3 class="col-sm-18 control-label" id="deleteLabel">删除信息</h3>
                                                <input type="hidden" class="form-control" id="tab"
                                                       name="tab" placeholder="" value="dor_admin"> <input
                                                    type="hidden" class="form-control" id="id"
                                                    name="studentId" placeholder="">
                                                <input type="hidden" class="form-control"
                                                       name="dormitoryId" id="dormitoryId">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <%--@declare id="user_id"--%><label for="user_id" class="col-sm-3 control-label">迁出原因</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control"
                                                       name="reason">
                                            </div>
                                        </div>

                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                    <button type="submit" class="btn btn-danger">迁出</button>
                                    <span id="tip"> </span>
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
    $('#delUserModal').on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget)
        var id = button.data('id')
        var dormitoryId = button.data('dormitory-id')
        var modal = $(this)
        modal.find('.modal-title').text('学生迁出登记')
        modal.find('#deleteLabel').text('将迁出学号为  ' + id + ' 的学生')
        modal.find('#id').val(id)
        modal.find('#dormitoryId').val(dormitoryId)
    })

    // 监听表单提交事件
    $('#form_data').on('submit', function(event) {
        event.preventDefault(); // 阻止表单默认的提交行为

        // 获取表单数据
        var formData = $(this).serialize();

        // 使用Ajax提交表单数据进行迁出操作
        $.ajax({
            type: 'POST',
            url: 'moveout?method=moveout',
            data: formData,
            success: function(response) {
                // 迁出成功后显示提示信息
                alert("迁出成功！");
                // 关闭迁出模态框
                $('#delUserModal').modal('hide');
                // 刷新页面
                location.reload(); // 刷新整个页面
            },
            error: function() {
                // 处理迁出失败的情况
                alert("迁出失败，请重试！");
            }
        });
    });
</script>

</body>

</html>