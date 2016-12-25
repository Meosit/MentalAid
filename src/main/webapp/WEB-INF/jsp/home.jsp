<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
          <div class="question">
            <div>
              <h4><strong><a href="#">Title of the post</a></strong></h4>
            </div>
            <div>
              <p class="fade-long">
                Lorem ipsum dolor sit amet, id nec conceptam conclusionemque. Et eam tation option. Utinam salutatus ex
                eum. Ne mea dicit tibique facilisi, ea mei omittam explicari conclusionemque, ad nobis propriae
                quaerendum
                sea.
              </p>
            </div>
            <div>
              <p>
                <span class="text-nowrap"><i class="icon-user"></i> by <a href="#">John</a></span>
                <span class="text-nowrap">| <i class="icon-calendar"></i> Sept 16th, 2012</span>
                <span class="text-nowrap">| <i class="icon-comment"></i> <a href="#">3 Answers</a></span>
              </p>
            </div>
          </div>
          <hr>
        </div>
      </div>
      <jsp:include page="template/footer.jsp"/>
    </body>
  </html>
</fmt:bundle>
