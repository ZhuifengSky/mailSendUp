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
<link href="js/umeditor1.2.3-utf8-jsp/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/umeditor1.2.3-utf8-jsp/third-party/jquery.min.js"></script>
<script type="text/javascript" src="js/umeditor1.2.3-utf8-jsp/third-party/template.min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/umeditor1.2.3-utf8-jsp/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="js/umeditor1.2.3-utf8-jsp/umeditor.min.js"></script>
<script type="text/javascript" src="js/umeditor1.2.3-utf8-jsp/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
 <style type="text/css">
        h1{
            font-family: "微软雅黑";
            font-weight: normal;
        }
        body{
            font-family: "微软雅黑";
            font-weight: normal;
            font-size: 15px;
        }
        .btn {
            display: inline-block;
            *display: inline;
            padding: 4px 12px;
            margin-bottom: 0;
            *margin-left: .3em;
            font-size: 14px;
            line-height: 20px;
            color: black;
            text-align: center;
            text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
            vertical-align: middle;
            cursor: pointer;
            background-color: #F45551;
            *background-color: #e6e6e6;
            background-image: -moz-linear-gradient(top, #ffffff, #e6e6e6);
            background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#ffffff), to(#e6e6e6));
            background-image: -webkit-linear-gradient(top, #ffffff, #e6e6e6);
            background-image: -o-linear-gradient(top, #ffffff, #e6e6e6);
            background-image: linear-gradient(to bottom, #ffffff, #e6e6e6);
            background-repeat: repeat-x;
            border: 1px solid #cccccc;
            *border: 0;
            border-color: #e6e6e6 #e6e6e6 #bfbfbf;
            border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
            border-bottom-color: #b3b3b3;
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            border-radius: 4px;
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffffff', endColorstr='#ffe6e6e6', GradientType=0);
            filter: progid:DXImageTransform.Microsoft.gradient(enabled=false);
            *zoom: 1;
            -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
            -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
            box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
        }

        .btn:hover,
        .btn:focus,
        .btn:active,
        .btn.active,
        .btn.disabled,
        .btn[disabled] {
            color: #333333;
            background-color: #e6e6e6;
            *background-color: #d9d9d9;
        }

        .btn:active,
        .btn.active {
            background-color: #cccccc \9;
        }

        .btn:first-child {
            *margin-left: 0;
        }

        .btn:hover,
        .btn:focus {
            color: #333333;
            text-decoration: none;
            background-position: 0 -15px;
            -webkit-transition: background-position 0.1s linear;
            -moz-transition: background-position 0.1s linear;
            -o-transition: background-position 0.1s linear;
            transition: background-position 0.1s linear;
        }

        .btn:focus {
            outline: thin dotted #333;
            outline: 5px auto -webkit-focus-ring-color;
            outline-offset: -2px;
        }

        .btn.active,
        .btn:active {
            background-image: none;
            outline: 0;
            -webkit-box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px rgba(0, 0, 0, 0.05);
            -moz-box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px rgba(0, 0, 0, 0.05);
            box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px rgba(0, 0, 0, 0.05);
        }

        .btn.disabled,
        .btn[disabled] {
            cursor: default;
            background-image: none;
            opacity: 0.65;
            filter: alpha(opacity=65);
            -webkit-box-shadow: none;
            -moz-box-shadow: none;
            box-shadow: none;
        }
        
    </style>
</head>
<body>
  <form action="sendMail/preView.do" method="post" enctype="multipart/form-data">
  <table>
     <thead>
       <tr>
           <th colspan="2">邮件自定义发送</th>
       </tr>
       <tr height="10px"></tr>
     </thead>
     <tbody>
        <tr>
           <td align="right">邮件主题:</td>
           <td><input type="text" name="subject" id="subject" style="width: 200px; height: 20px"></td>
        </tr>
        <tr height="5px"></tr>
        <tr>
           <td align="right">收件人邮箱附件文件:</td>
           <td><input type="file" name="file" id="emailInfoPath" style="width: 200px; height: 20px"></td>
        </tr>
        <tr height="8px"></tr>
        <tr>
           <td align="right">附件查询目录位置:</td>
           <td><input type="text" name="attachmentPath" id="attachmentPath" style="width: 200px; height: 20px">&nbsp;&nbsp;<font color="red" size="2">示例：G:\mail\file 或/Users/sky/Documents/attachFile</font></td>
        </tr>
        <tr height="5px"></tr>
        <tr>
           <td align="right">附件文件后缀:</td>
           <td><input type="text" name="attachmentSuffix" id="attachmentSuffix" style="width: 200px; height: 20px">&nbsp;&nbsp;<font color="red" size="2">示例：tar</font></td>
        </tr>
        <tr height="5px"></tr>
        <tr>
           <td align="right">匹配方式:</td>
           <td>
              <select name="findType" id="findType" style="width: 200px; height: 30px">
                 <option value="">请选择</option>
           		 <option value="sigle">单一匹配</option>
           		 <option value="near">模糊匹配</option>
              </select>&nbsp;&nbsp;<font color="red" size="2">模糊匹配----附件名(1).附件名(2)....</font>
           </td>
        </tr>
        <tr height="5px"></tr>
        <tr>
           <td align="right">邮件内容:</td>
           <td>
               <script type="text/plain" id="myEditor" style="width:550px;height:250px;"></script>
           	   <input type="hidden" name="content" id="content"><font color="red">tips: 【姓名】 会被替换为收件人姓名  【邮箱】 会被替换为收件人邮箱</font>
           </td>
        </tr>
        <tr height="5px"></tr>
        <tr>
        	<td colspan="2" align="center">
        		<button class="btn" type="submit" onclick="return subCheck()">结果预览</button>
        	</td>
        </tr>
     </tbody>	
  </table>
</form>
<div class="clear"></div>
<script type="text/javascript">
    //实例化编辑器
    var um = UM.getEditor('myEditor');
    
    function subCheck(){
    	if($("#subject").val()==""){
    		alert("邮件主题不能为空!");
    		return false;
    	}else if($("#emailInfoPath").val()==""){
    		alert("请选择收件人邮箱附件名文件!");
    		return false;
    	}else if($("#attachmentPath").val()==""){
    		alert("请输入附件查询目录!");
    		return false;
    	}else if($("#attachmentSuffix").val()==""){
    		alert("请输入附件后缀!");
    		return false;
    	}else if($("#findType").val()==""){
    		alert("请选择匹配方式!");
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