<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<json:object>
  <json:property name="isResultSuccess" value="${requestScope.isResultSuccess}"/>
  <c:choose>
    <c:when test="${requestScope.isResultSuccess}">
      <c:if test="${not empty requestScope.successValue}">
        <json:property
            name="${requestScope.successValueName}"
            value="${requestScope.successValue}"
            escapeXml="false"/>
      </c:if>
    </c:when>
    <c:otherwise>
      <fmt:setLocale value="${sessionScope.locale}"/>
      <fmt:bundle basename="pagecontent" prefix="error.">
        <json:property name="errorTitle" trim="true">
          <fmt:message key="${requestScope.errorTitle}"/>
        </json:property>
        <json:property name="errorMessage" trim="true">
          <fmt:message key="${requestScope.errorMessage}"/>
        </json:property>
      </fmt:bundle>
    </c:otherwise>
  </c:choose>
</json:object>
