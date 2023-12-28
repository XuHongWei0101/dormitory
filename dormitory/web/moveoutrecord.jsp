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
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

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

</body>

</html>