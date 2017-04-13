<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<style type="text/css">
   #content{
     padding-left: 70px;
   }
   h3{
    padding-left: 180px;
   }
</style>
</head>
<body>
  <div id="content">
  <h3>文件预览</h1>
  <table border="1px">
     <tbody>
        <tr>
           <th width="100px" height="50px">姓名</th>
           <th width="100px">附件名</th>
           <th width="200px">邮箱</th>
           <th width="300px">附件查询路径</th>
           <c:if test="${findType eq 'near'}">
              <th width="300px">提示信息</th>
           </c:if>
        </tr>
        <c:forEach items="${sendBeans}" var="s">
        	 <tr>
        	   <td align="center" height="30px">${s.userName}</td>
        	   <td align="center">${s.attachFileName}</td>
        	   <td align="center">${s.email}</td>
        	   <td align="center">
        	       <c:if test="${findType eq 'sigle'}">
        	   		${s.attchFilePath}
        	       </c:if>
        	       <c:if test="${findType eq 'near'}">
        	   		    <c:forEach items="${s.attchFilePathes}" var="p">
        	   		         ${p};&nbsp;&nbsp;&nbsp;
        	   		    </c:forEach>
        	       </c:if>
        	   </td>
        	   <c:if test="${findType eq 'near'}">
              		<th width="300px"><font color="red">${s.errorMsg}</font></th>
               </c:if>
            </tr>
        </c:forEach>
     </tbody>	
  </table>
  <form action="send.do" method="post">
      <input type="hidden" name="findType" value="${findType}"/><br>
      <input type="hidden" name="subject" value="${subject}"/><br>
      <textarea rows="5" cols="15" style="display: none;" name="content">
      	${content}
      </textarea>
      <textarea rows="5" cols="15" style="display: none;" name="sendUserInfoStr">
      	${sendUserInfos}
      </textarea>
      <input type="submit" value="执行发送">
  </form>
</div>
<div class="clear"></div>
<script type="text/javascript">
    //实例化编辑器
    var um = UM.getEditor('myEditor');
    
    function subCheck(){
    	if($("#nickName").val()==""){
    		alert("发件人姓名不能为空!");
    		return false;
    	}else if($("#subject").val()==""){
    		alert("邮件主题不能为空!");
    		return false;
    	}else if($("#emailInfoPath").val()==""){
    		alert("请选择收件人邮箱附件名文件!");
    		return false;
    	}else if($("#attachmentPath").val()==""){
    		alert("请选择附件查询目录!");
    		return false;
    	}else if(!UM.getEditor('myEditor').hasContents()){
    		alert("邮件内容不能为空!");
    		return false;
    	}else{
    		var contentStr = UM.getEditor('myEditor').getContent();
    		$("#content").val(contentStr);
    		return true;
    	}
    	
    }
</script>
</body>
</html>