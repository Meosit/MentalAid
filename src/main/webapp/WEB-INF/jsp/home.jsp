<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ms" uri="http://epam.mksn.by/MentalAid/tag/msTags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="home.">
  <html lang="${sessionScope.locale}">
    <head>
      <title><fmt:message key="title"/></title>
      <jsp:include page="template/links.jsp"/>
      <link rel="stylesheet" href="<c:url value="/css/home.css"/>">
    </head>
    <body>
      <jsp:include page="template/navbar.jsp">
        <jsp:param name="isNavbarLess" value="false"/>
      </jsp:include>
      <div class="container">
        <div class="row">
          <div id="custom-search-input">
            <div class="input-group">
              <input type="text" class="form-control input-lg" placeholder="Search for questions"/>
              <span class="input-group-btn">
                <button class="btn btn-info btn-lg" type="button">
                    <i class="glyphicon glyphicon-search"></i>
                </button>
              </span>
            </div>
          </div>
        </div>
        <div>
          <c:forEach var="question" items="${requestScope.questions}">
            <div class="question">
              <div>
                <h4><strong><a href="#"><c:out value="${question.title}"/></a></strong></h4>
              </div>
              <div>
                <p class="fade-long">
                  <c:out value="${question.description}"/>
                </p>
              </div>
              <div>
                <p>
                  <span class="text-nowrap"><span class="glyphicon glyphicon-user"></span> <a
                      href="#">${question.creatorUsername}</a></span>
                  <span class="text-nowrap">| <span class="glyphicon glyphicon-calendar"></span> <fmt:formatDate
                      value="${question.createdAt}" pattern="dd MMMM, yyyy HH:mm"/></span>
                  <span class="text-nowrap">| <span class="glyphicon glyphicon-comment"></span> <a
                      href="#">${question.answerCount} Answers</a></span>
                </p>
              </div>
            </div>
            <hr>
          </c:forEach>
        </div>
        <div class="row text-center">
          <c:url value="/controller?cmd=get_home_page&page=" var="baseUrl"/>
          <ms:bootstrapPagination baseUrl="${baseUrl}" currentPage="${requestScope.currentPage}"
                                  pageCount="${requestScope.pageCount}"/>
        </div>
      </div>
      <jsp:include page="template/footer.jsp"/>
    </body>
  </html>
</fmt:bundle>
