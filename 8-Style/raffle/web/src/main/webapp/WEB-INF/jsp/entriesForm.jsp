<%@ page info="Entries Page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="com.pillartech.raffle.domain.*" %>
<html>
  <head>
  	<title>Raffle-in-ator</title>
  </head>

<body>
   <h2>Add an Entry to the <c:out value="${raffle.name}"/> raffle</h2>
   <form method="POST" action="entries.htm">
     <input type="hidden" name="raffleId" value="<c:out value="${raffle.id}" />" />
     Name:&nbsp;<input type="text" name="entryName" />
     Email:&nbsp;<input type="text" name="entryEmail" />
     <input type="submit" value="Add Entry"/>
   </form>
   <c:if test="${message != null}">
   	 <h4><c:out value="${message}" /></h4>
   </c:if>
</body>
</html>