<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ms" uri="http://epam.mksn.by/MentalAid/tag/msTags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<html lang="${sessionScope.locale}">
<head>
  <title>{Question Title}</title>
  <jsp:include page="template/links.jsp"/>
  <link rel="stylesheet" href="<c:url value="/css/question.css"/>">
  <link rel="stylesheet" href="<c:url value="/css/stars.css"/>">
</head>
<body>
<jsp:include page="template/navbar.jsp">
  <jsp:param name="isNavbarLess" value="false"/>
</jsp:include>
<div class="container">
  <div class="question">
    <h3>Blog Post Title</h3>
    <hr class="question-separator">
    <p class="description">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, vero, obcaecati, aut,
      error
      quam sapiente nemo saepe quibusdam sit excepturi nam quia corporis eligendi eos magni recusandae laborum minus
      inventore? Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, vero, obcaecati, aut, error
      quam sapiente nemo saepe quibusdam sit excepturi nam quia corporis eligendi eos magni recusandae laborum minus
      inventore? Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, vero, obcaecati, aut, error
      quam sapiente nemo saepe quibusdam sit excepturi nam quia corporis eligendi eos magni recusandae laborum minus
      inventore? Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, vero, obcaecati, aut, error
      quam sapiente nemo saepe quibusdam sit excepturi nam quia corporis eligendi eos magni recusandae laborum minus
      inventore? Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, vero, obcaecati, aut, error
      quam sapiente nemo saepe quibusdam sit excepturi nam quia corporis eligendi eos magni recusandae laborum minus
      inventore? Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, vero, obcaecati, aut, error
      quam sapiente nemo saepe quibusdam sit excepturi nam quia corporis eligendi eos magni recusandae laborum minus
      inventore? Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, vero, obcaecati, aut, error
      quam sapiente nemo saepe quibusdam sit excepturi nam quia corporis eligendi eos magni recusandae laborum minus
      inventore? Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, vero, obcaecati, aut, error
      quam sapiente nemo saepe quibusdam sit excepturi nam quia corporis eligendi eos magni recusandae laborum minus
      inventore?
    </p>
    <h5 class="text-right">
      Asked by <a href="#">appleWow</a> at
      <span class="glyphicon glyphicon-time"></span> August 24, 2013 at 9:00 PM
    </h5>
  </div>

  <div class="answers">
    <div class="answers-header">
      <h3>5 Answers</h3>
      <hr class="question-separator">
    </div>
    <div class="answer">
      <div class="media">
        <div class="media-body">
          <p>
            Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras
            purus
            odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate
            fringilla.
            Donec lacinia congue felis in faucibus.
          </p>
          <div class="text-right">
            <ms:starRating value="2.5"/>
            <h5 class="media-heading">
              by <a href="#">NesKwi</a> at
              August 25, 2014 at 9:30 PM
            </h5>
          </div>
        </div>
      </div>
      <hr>
    </div>
    <div class="answer">
      <div class="media">
        <div class="media-body">
          <p>
            Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras
            purus
            odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate
            fringilla.
            Donec lacinia congue felis in faucibus.
          </p>
          <div class="text-right">
            <ms:starRating value="2.5"/>
            <h5 class="media-heading">
              by <a href="#">NesKwi</a> at
              August 25, 2014 at 9:30 PM
            </h5>
          </div>
        </div>
      </div>
      <hr>
    </div>
    <div class="answer">
      <div class="media">
        <div class="media-body">
          <p>
            Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras
            purus
            odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate
            fringilla.
            Donec lacinia congue felis in faucibus.
          </p>
          <div class="text-right">
            <ms:starRating value="2.5"/>
            <h5 class="media-heading">
              by <a href="#">NesKwi</a> at
              August 25, 2014 at 9:30 PM
            </h5>
          </div>
        </div>
      </div>
      <hr>
    </div>
    <div class="answer">
      <div class="media">
        <div class="media-body">
          <p>
            Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras
            purus
            odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate
            fringilla.
            Donec lacinia congue felis in faucibus.
          </p>
          <div class="text-right">
            <ms:starRating value="2.5"/>
            <h5 class="media-heading">
              by <a href="#">NesKwi</a> at
              August 25, 2014 at 9:30 PM
            </h5>
          </div>
        </div>
      </div>
      <hr>
    </div>
    <div class="answer">
      <div class="media">
        <div class="media-body">
          <p>
            Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras
            purus
            odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate
            fringilla.
            Donec lacinia congue felis in faucibus.
          </p>
          <div class="text-right">
            <ms:starRating value="2.5"/>
            <h5 class="media-heading">
              by <a href="#">NesKwi</a> at
              August 25, 2014 at 9:30 PM
            </h5>
          </div>
        </div>
      </div>
      <hr>
    </div>
    <div class="answer">
      <div class="media">
        <div class="media-body">
          <p>
            Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras
            purus
            odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate
            fringilla.
            Donec lacinia congue felis in faucibus.
          </p>
          <div class="text-right">
            <ms:starRating value="2.5"/>
            <h5 class="media-heading">
              by <a href="#">NesKwi</a> at
              August 25, 2014 at 9:30 PM
            </h5>
          </div>
        </div>
      </div>
      <hr>
    </div>
  </div>

  <div class="well">
    <h4>Leave an answer:</h4>
    <form role="form">
      <div class="form-group">
        <label>
          <textarea class="form-control" rows="3"></textarea>
        </label>
      </div>
      <button type="submit" class="btn btn-primary">Submit</button>
    </form>
  </div>
</div>
<jsp:include page="template/footer.jsp"/>
</body>
</html>
