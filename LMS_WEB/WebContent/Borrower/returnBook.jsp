<%@include file="index.jsp"%>
<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%
	Borrower borrower = (Borrower) request.getAttribute("borrower");
	@SuppressWarnings("unchecked") List<Book> books = (List<Book>)request.getAttribute("loanedBooks");
%>
<script>
	function viewBook(id) {
		//document.location.href = "deleteAuthor?authorId="+id		
		document.getElementById("bookId").value = id;
		document.viewFrm.submit();
	}
</script>

<div style="margin-top:80px;margin-left:10px;">
<section>
<table class="table">
	<tr>
		<!-- <td>Book Id</td> -->
		<td><b>Book Name</b></td>
		<td><b>Return</b></td>
	</tr>
	<%for(Book b : books) { %>	
	<tr>
		<%-- <td><%=b.getBookId()%></td> --%>
		<td><i><%=b.getTitle()%></i></td>
		<td><button class="btn btn-warning" onclick="javascript:viewBook(<%=b.getBookId()%>);">Return</button></td>
	</tr>
	<% } %>
</table>

<form method="post" action="returnBook" name="viewFrm">
	<input type="hidden" name="bookId" id="bookId"/>
	<input type="hidden" name="cardNo" id="cardNo" value="<%=borrower.getCardNo()%>"/>
</form>

</section>
</div>