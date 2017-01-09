<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<json:object>
  <json:property name="status" value="${requestScope.responseStatus}"/>
  <c:choose>
    <c:when test="${requestScope.responseStatus eq 'ok'}">
      <json:property name="redirectUrl" value="${requestScope.redirectUrl}" escapeXml="false"/>
    </c:when>
    <c:when test="${requestScope.responseStatus eq 'fail'}">
      <fmt:setLocale value="${sessionScope.locale}"/>
      <fmt:bundle basename="pagecontent" prefix="error.">
        <json:property name="errorTitle" trim="true">
          <fmt:message key="${requestScope.errorTitle}"/>
        </json:property>
        <json:property name="errorMessage" trim="true">
          <fmt:message key="${requestScope.errorMessage}"/>
        </json:property>
      </fmt:bundle>
    </c:when>
  </c:choose>
</json:object>