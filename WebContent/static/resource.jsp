<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <link rel="stylesheet" type="text/css"
		href="${contextPath}/static/font-awesome/css/font-awesome.min.css" />
    
    <script type="text/javascript" src="${contextPath}/static/extjs/build/ext-all-debug.js"></script>
 	
 	<script type="text/javascript">
	   // Ext.Loader.setPath('Abby.app', '${contextPath}/static/pages');
	    Ext.Loader.setConfig({
	        enabled: true,
	        disableCaching:false,
	        paths: {
	            'Abby.app': '${contextPath}/static/pages'
	        }
	      });
	</script>
 	
   <script type="text/javascript">
   </script>
    
    </head>
    </html>
