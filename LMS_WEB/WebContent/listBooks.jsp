<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
	List<Book> books = new AdminService().getAllBooks();
%>
<%@include file="index.jsp"%>
<script>
	function viewBook(id) {
		//document.location.href = "deleteAuthor?authorId="+id;
		console.log(id);
		document.getElementById("bookViewId").value = id;
		document.viewFrm.submit();
	}
</script>

<div style="margin-top:80px;margin-left:10px;">
<section>
<table class="table">
	<tr>
		<!-- <td>Book Id</td> -->
		<td><b>Book Name</b></td>
		<td><b>View</b></td>
	</tr>
	<%for(Book b : books) { %>	
	<tr>
		<%-- <td><%=b.getBookId()%></td> --%>
		<td><i><%=b.getTitle()%></i></td>
		<td><button class="btn btn-success" onclick="javascript:viewBook(<%=b.getBookId()%>);">View</button></td>
	</tr>
	<% } %>
</table>

<form action="viewBook" method="post" name="viewFrm">
	<input type="hidden" name="bookViewId" id="bookViewId"/>
</form>

</section>
</div>