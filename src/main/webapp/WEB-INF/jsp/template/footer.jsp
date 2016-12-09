<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="footer.">
  <footer class="footer container-fluid text-center">
    <p> &copy; 2016-2017. EPAM Systems. <fmt:message key="copyright"/></p>
  </footer>
</fmt:bundle>