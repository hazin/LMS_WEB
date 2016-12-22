<%@include file="../index.jsp"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="java.util.List"%>
<%
	LibraryBranch branch = (LibraryBranch) request.getAttribute("branch");
	Borrower borrower = (Borrower) request.getAttribute("borrower");
	@SuppressWarnings("unchecked") List<Book> books = (List<Book>)request.getAttribute("booklist");
%>
<script>
	function viewBook(id) {
		//document.location.href = "deleteAuthor?authorId="+id		
		document.getElementById("bookLoanId").value = id;
		document.viewFrm.submit();
	}
</script>

<div style="margin-top:80px;margin-left:10px;">
<section>
<h3><b> <%=branch.getBranchName()%></b> </h3>
<h4><b> <%=branch.getBranchAddress()%> </b></h4>

<table class="table">
	<tr>
		<!-- <td>Book Id</td> -->
		<td><b>Book Name</b></td>
		<td><b>Check-Out</b></td>
	</tr>
	<%for(Book b : books) { %>	
	<tr>
		<%-- <td><%=b.getBookId()%></td> --%>
		<td><i><%=b.getTitle()%></i></td>
		<td><button class="btn btn-success" onclick="javascript:viewBook(<%=b.getBookId()%>);">Check-Out</button></td>
	</tr>
	<% } %>
</table>

<form method="post" action="loanBook" name="viewFrm">
	<input type="hidden" name="bookLoanId" id="bookLoanId"/>
	<input type="hidden" name="cardNo" id="cardNo" value="<%=borrower.getCardNo()%>"/>
	<input type="hidden" name="loanBranchId" id="loanBranchId" value="<%=branch.getBranchId()%>"/>
</form>

</section>
</div>