<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
	List<Author> authors = new AdminService().getAllAuthors();
	List<Genre> genres = new AdminService().getAllGenres();
%>

<%@include file="index.jsp"%>

<div style="margin-top:80px;margin-left:10px;">
<section>
<form method="post" action="addBook">
	<input type="text" placeholder="Enter Title" name="title"/><br/>
	<br/>
	<select multiple name="authorId">
		<%for(Author a : authors) { %>	
			<option value="<%=a.getAuthorId()%>"><%=a.getAuthorName()%></option>
		<% } %>
	</select><br/>
	<br/>
	<select multiple name="genreId">
		<%for(Genre g : genres) { %>	
			<option value="<%=g.getGenreId()%>"><%=g.getGenreName()%></option>
		<% } %>
	</select><br/>
	
	</select><br/>
	<br/>
	<input type="submit"/>

</form>
</section>
</div>