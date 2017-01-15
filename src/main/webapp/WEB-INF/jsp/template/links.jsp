<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="icon" href="<c:url value="/img/logo.png"/>">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="authorized" content="${not empty sessionScope.user ? 'true' : 'false'}">
<link rel="stylesheet" href="<c:url value="/css/lib/bootstrap.css"/>">
<link rel="stylesheet" href="<c:url value="/css/main.css"/>">
<script src="<c:url value="/js/lib/jquery-3.1.1.js"/>"></script>
<script src="<c:url value="/js/lib/bootstrap.js"/>"></script>
<script src="<c:url value="/js/localization/${sessionScope.locale}.js"/>"></script>

