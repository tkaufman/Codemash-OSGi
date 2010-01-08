<%@ page info="Entries Page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="com.pillartech.raffle.domain.*" %>
<html>
  <head>
  	<title>Raffle-in-ator</title>
	<link href="css/raffle.css" rel="stylesheet" type="text/css">
  </head>

<body>
  <div id="contents">
  <h2>Add an Entry to the <c:out value="${raffle.name}"/> raffle</h2>
  <form method="POST" action="entries.htm">
	<fieldset>
	  <legend>Entry Information</legend>
	    <input type="hidden" name="raffleId" value="<c:out value="${raffle.id}" />" />
	    <label for="entryName">Name</label>
	    <input type="text" name="entryName" /><br/>
	    <label for "entryEmail">Email</label>
	    <input type="text" name="entryEmail" /><br/>
	    <input type="submit" value="Add Entry"/><br/>
	  </fieldset>
	</form>
	<c:if test="${message != null}">
   	  <small><c:out value="${message}" /></small>
   </c:if>
  </div>
</body>
</html>

