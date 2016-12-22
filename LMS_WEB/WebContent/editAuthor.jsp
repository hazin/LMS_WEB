<%@ page import="com.gcit.lms.entity.Author"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%
	Integer authorId = Integer.parseInt(request.getParameter("authorId"));
	Author author = null;
	AdminService service = new AdminService();
	author = service.getAuthorById(authorId);
%>
<%@include file="index.jsp"%>
<div>
	<h2>Hello Admin!</h2>
	<h3>Enter Author Details to Edit</h3>
	<form action="editAuthor" method="post">
		Enter Author Name: <input type="text" name="authorName"
			value="<%=author.getAuthorName()%>"><br /> <input
			type="hidden" name="authorId" value="<%=author.getAuthorId()%>">
		<button type="submit">Edit Author!</button>
	</form>
</div>