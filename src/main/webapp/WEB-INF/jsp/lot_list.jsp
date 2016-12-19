<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="login.">
  <html lang="${sessionScope.locale}">
  <head>
    <title><fmt:message key="title"/></title>
    <jsp:include page="template/links.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/lot_list.css"/>">
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
  </head>
  <body>
  <jsp:include page="template/navbar.jsp">
    <jsp:param name="isNavbarLess" value="false"/>
  </jsp:include>
  <div class="container">
    <div class="well well-sm">
      <strong>Category Title</strong>
      <div class="btn-group">
        <a href="#" id="list" class="btn btn-default btn-sm">
          <span class="glyphicon glyphicon-th-list"></span> List
        </a>
        <a href="#" id="grid" class="btn btn-default btn-sm">
          <span class="glyphicon glyphicon-th"></span> Grid
        </a>
      </div>
    </div>
    <div id="products" class="row list-group">
      <div class="item list-group-item  col-xs-4 col-lg-4">
        <div class="thumbnail">
          <img class="group list-group-image" src="http://placehold.it/400x250/000/fff" alt=""/>
          <div class="caption">
            <h4 class="group inner list-group-item-heading">
              Product title dfjvslfkn dslj nljdn slkd nlksdn lsk dnlsdn lsdklskdn</h4>
            <p class="group inner list-group-item-text">
              Product description... Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
              sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.Product
              description... Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
              sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.Product
              description... Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
              sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.Product
              description... Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
              sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.Product
              description... Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
              sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.Product
              description... Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
              sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.Product
              description... Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
              sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.Product
              description... Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
              sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.Product
              description... Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
              sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.Product
              description... Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
              sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.Product
              description... Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
              sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.Product
              description... Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
              sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.Product
              description... Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
              sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.</p>
            <div class="row">
              <div class="col-xs-12 col-md-6">
                <p class="lead">
                  $21.000</p>
              </div>
              <div class="col-xs-12 col-md-6">
                <a class="btn btn-success" href="http://www.jquery2dotnet.com">Add to cart</a>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="item list-group-item  col-xs-4 col-lg-4">
        <div class="thumbnail">
          <img class="group list-group-image" src="http://placehold.it/400x250/000/fff" alt=""/>
          <div class="caption">
            <h4 class="group inner list-group-item-heading">
              Product title</h4>
            <p class="group inner list-group-item-text">
              Product description... Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
              sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.</p>
            <div class="row">
              <div class="col-xs-12 col-md-6">
                <p class="lead">
                  $21.000</p>
              </div>
              <div class="col-xs-12 col-md-6">
                <a class="btn btn-success" href="http://www.jquery2dotnet.com">Add to cart</a>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="item list-group-item col-xs-4 col-lg-4">
        <div class="thumbnail">
          <img class="group list-group-image" src="http://placehold.it/400x250/000/fff" alt=""/>
          <div class="caption">
            <h4 class="group inner list-group-item-heading">
              Product title</h4>
            <p class="group inner list-group-item-text">
              Product description... Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
              sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.</p>
            <div class="row">
              <div class="col-xs-12 col-md-6">
                <p class="lead">
                  $21.000</p>
              </div>
              <div class="col-xs-12 col-md-6">
                <a class="btn btn-success" href="http://www.jquery2dotnet.com">Add to cart</a>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="item list-group-item col-xs-4 col-lg-4">
        <div class="thumbnail">
          <img class="group list-group-image" src="http://placehold.it/400x250/000/fff" alt=""/>
          <div class="caption">
            <h4 class="group inner list-group-item-heading">
              Product title</h4>
            <p class="group inner list-group-item-text">
              Product description... Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
              sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.</p>
            <div class="row">
              <div class="col-xs-12 col-md-6">
                <p class="lead">
                  $21.000</p>
              </div>
              <div class="col-xs-12 col-md-6">
                <a class="btn btn-success" href="http://www.jquery2dotnet.com">Add to cart</a>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="item list-group-item col-xs-4 col-lg-4">
        <div class="thumbnail">
          <img class="group list-group-image" src="http://placehold.it/400x250/000/fff" alt=""/>
          <div class="caption">
            <h4 class="group inner list-group-item-heading">
              Product title</h4>
            <p class="group inner list-group-item-text">
              Product description... Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
              sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.</p>
            <div class="row">
              <div class="col-xs-12 col-md-6">
                <p class="lead">
                  $21.000</p>
              </div>
              <div class="col-xs-12 col-md-6">
                <a class="btn btn-success" href="http://www.jquery2dotnet.com">Add to cart</a>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="item list-group-item col-xs-4 col-lg-4">
        <div class="thumbnail">
          <img class="group list-group-image" src="http://placehold.it/400x250/000/fff" alt=""/>
          <div class="caption">
            <h4 class="group inner list-group-item-heading">
              Product title</h4>
            <p class="group inner list-group-item-text">
              Product description... Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
              sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.</p>
            <div class="row">
              <div class="col-xs-12 col-md-6">
                <p class="lead">
                  $21.000</p>
              </div>
              <div class="col-xs-12 col-md-6">
                <a class="btn btn-success" href="http://www.jquery2dotnet.com">Add to cart</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <jsp:include page="template/footer.jsp"/>
  </body>
  </html>
</fmt:bundle>
