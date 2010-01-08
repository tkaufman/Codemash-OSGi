<%@ page info="Raffle Page"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.pillartech.raffle.domain.*"%>
<html>
<head>
  <title>Raffle-in-ator</title>
  <link href="css/raffle.css" rel="stylesheet" type="text/css">
</head>

<body>
  <div id="contents">
  <h2>Pick Winners for a raffle</h2>
  <form method="POST" action="winners.htm">
	<fieldset>
	<label for="rafffleId">Raffle</label>
    <select name="raffleId">
	  <c:forEach var="raffle" items="${raffles}">
		<option value="<c:out value="${raffle.id}" />"><c:out
			value="${raffle.name}" /></option>
	  </c:forEach>
    </select><br/>
    <label for="numWinners"># of Winners</label>
    <input type="text" name="numWinners"></input><br/>
    <input type="submit" value="Pick em" />
    </fieldset>
  </form>
  </div>
</body>
</html>