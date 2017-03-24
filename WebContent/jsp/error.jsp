<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邮件自定义发送</title>
<link href="../js/umeditor1.2.3-utf8-jsp/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="../js/umeditor1.2.3-utf8-jsp/third-party/jquery.min.js"></script>
<script type="text/javascript" src="../js/umeditor1.2.3-utf8-jsp/third-party/template.min.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/umeditor1.2.3-utf8-jsp/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/umeditor1.2.3-utf8-jsp/umeditor.min.js"></script>
<script type="text/javascript" src="../js/umeditor1.2.3-utf8-jsp/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>
</head>
<body>
  出错了！！ 错误提示----${msg}
</body>
</html>