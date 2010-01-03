<%@ page info="Entries Page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="com.pillartech.raffle.domain.*" %>
<html>
  <head>
  	<title>Raffle-in-ator</title>
  </head>

<body>
  <h2>Congratulations to the following winners:</h2>
	<c:forEach var="winner" items="${winners}">
	  	<b><c:out value="${winner}" /></b>
	  	<br/>
	</c:forEach>

  <a href="<c:url value="/index.htm"/>">Return to Main Page</a>

</body>
</html>