<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    
    <jsp:include page="/static/resource.jsp"></jsp:include>

    <title>云梦阁</title>
    
    <!-- The line below must be kept intact for Sencha Cmd to build your application -->
     <script type="text/javascript" src="${contextPath}/static/pages/home/homePageAdmin.js"></script>
    <script type="text/javascript" src="${contextPath}/static/pages/home/homeAdmin.js"></script>
    <script type="text/javascript" src="${contextPath}/static/js/share.js"></script>
    <script type="text/javascript" src="${contextPath}/static/pages/user/changePassword.js"></script>
    <script type="text/javascript" src="${contextPath}/static/pages/user/userList.js"></script>
    <script type="text/javascript" src="${contextPath}/static/pages/user/userMainW.js"></script>
    <script type="text/javascript" src="${contextPath}/static/pages/money/withdrawMoneyDealList.js"></script>
    <script type="text/javascript" src="${contextPath}/static/pages/money/withdrawMoneyDealW.js"></script>
    <script type="text/javascript" src="${contextPath}/static/pages/order/orderList.js"></script>
    <script type="text/javascript" src="${contextPath}/static/pages/order/orderW.js"></script>
    <script type="text/javascript" src="${contextPath}/static/pages/income/incomeList.js"></script>
    <script type="text/javascript">
    var baseUrl = "<%=request.getContextPath()%>";
    </script>
</head>
<body>
	<div id='home'></div>
</body>
</html>
