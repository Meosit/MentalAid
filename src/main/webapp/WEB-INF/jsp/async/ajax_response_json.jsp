<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<json:object>
  <json:property name="status" value="${requestScope.responseStatus}"/>
  <c:choose>
    <c:when test="${requestScope.responseStatus eq 'fail'}">
      <json:property name="errorTtile" trim="true">
        <fmt:message bundle="pagecontent" key="${requestScope.errorTitle}"/>
      </json:property>
      <json:property name="errorMessage" trim="true">
        <fmt:message bundle="pagecontent" key="${requestScope.errorMessage}"/>
      </json:property>
    </c:when>
  </c:choose>
</json:object>