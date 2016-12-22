<%@ page import="com.gcit.lms.entity.Genre"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%
	Integer genreId = Integer.parseInt(request.getParameter("genreId"));
	Genre genre = null;
	AdminService service = new AdminService();
	genre = service.getGenreById(genreId);
%>
<%@include file="index.jsp"%>
<div>
	<h2>Hello Admin!</h2>
	<h3>Enter Genre Details to Edit</h3>
	<form action="editGenre" method="post">
		Enter Genre Name: <input type="text" name="genreName"
			value="<%=genre.getGenreName()%>"><br /> <input
			type="hidden" name="genreId" value="<%=genre.getGenreId()%>">
		<button type="submit">Edit Genre!</button>
	</form>
</div>