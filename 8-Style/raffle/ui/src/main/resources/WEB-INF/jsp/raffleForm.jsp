<%@ page info="Raffle Page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="com.pillartech.raffle.domain.*" %>
<html>
  <head>
  	<title>Raffle-in-ator</title>
	<link href="css/raffle.css" rel="stylesheet" type="text/css">
  </head>

<body>
  <div id="contents">
<c:choose>
  <c:when test='${empty raffles}'>
    <h2>Let's Start a new Raffle!</h2>
    <form method="POST" action="index.htm">
	  <label for="raffleName">Raffle Name</label>
      <input type="text" name="raffleName"/><br/>
      <input type="submit" value="Create"/>
    </form>
</c:when>
<c:otherwise>
    <h2>Choose a Raffle!</h2>
    <form method="POST" action="raffle.htm">
      <select name="raffleId">
		<c:forEach var="raffle" items="${raffles}">
		  <option value="<c:out value="${raffle.id}" />">
		  	<c:out value="${raffle.name}" />
		  </option>
		</c:forEach>
	  </select>
      <input type="submit" value="Add Entries"/>
    </form>
</c:otherwise>
</c:choose>
</div>
</body>
</html>