<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../include/constants.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>500 - 出问题了!休息一下!</title>
<link href="/css/base${build}.css?${constantVersion}" type="text/css" rel="stylesheet" />
<link href="/css/layout${build}.css?${constantVersion}" type="text/css" rel="stylesheet" />
<link href="/css/error${build}.css?${constantVersion}" type="text/css" rel="stylesheet" />
</head>
<body>
<div id="container">
    <%@include file="../include/header.jsp"%>
    <div id="main">
        <div class="errorReqBody">
        	<img src="/images/500.png">服务器端错误!

  <!--
  
错误码： <%=request.getAttribute("javax.servlet.error.status_code")%> <br>
信息： <%=request.getAttribute("javax.servlet.error.message")%> <br>
 异常： <%=request.getAttribute("javax.servlet.error.exception_type")%>  <br>
详细错误: 
<% exception.printStackTrace(response.getWriter());%>
 -->

        </div>
    </div>
    <%@include file="../include/footer.jsp"%>
</div>

</body>
</html>



