<%@ page info="Raffle Page"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.pillartech.raffle.domain.*"%>
<html>
<head>
<title>Raffle-in-ator</title>
</head>

<body>
<h2>Pick Winners for a raffle</h2>
<form method="POST" action="winners.htm">
  <select name="raffleId">
	<c:forEach var="raffle" items="${raffles}">
		<option value="<c:out value="${raffle.id}" />"><c:out
			value="${raffle.name}" /></option>
	</c:forEach>
  </select>
  # of Winners:&nbsp;<input type="text" name="numWinners"></input>
  <input type="submit" value="Pick em" /></form>
</body>
</html>