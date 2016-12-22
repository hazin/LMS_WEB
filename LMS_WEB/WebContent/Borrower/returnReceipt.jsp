<%@include file="../index.jsp"%>
<%@page import="com.gcit.lms.entity.BookLoan"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="java.util.List"%>
<%
	BookLoan loanedBook = (BookLoan) request.getAttribute("loanedBooks");
%>

<div style="margin-top: 80px; margin-left: 10px;">
<section>
<h4>
<b>Branch Name:</b> <%=loanedBook.getBranch().getBranchName()%>
<br>
<b>Branch Address:</b> <%=loanedBook.getBranch().getBranchAddress()%> 
<br>
<br>
<%-- Card No: <%=loanedBook.getBorrower().getCardNo()%>  --%>
<br>
<b>Borrower Name:</b> <%=loanedBook.getBorrower().getBorrowerName()%> 
</h4>

</section>
	<section>
		<table class="table">
			<col width="130">
			<tr>
				<!-- <td>Book Id</td> -->
				<td><b>Book Name</b></td>
			</tr>
			<tr>
				<%-- <td><%=loanedBook.getBook().getBookId()%></td> --%>
				<td><i><%=loanedBook.getBook().getTitle()%></i></td>
			</tr>			
		</table>
		<b>Date Checked Out:</b> <%=loanedBook.getDateOut()%>
		<br>		
		<b>Date Returned:</b> <%=loanedBook.getDateIn()%>
		<br>
		<b>Due Date:</b> <%=loanedBook.getDueDate()%>
	</section>
</div>