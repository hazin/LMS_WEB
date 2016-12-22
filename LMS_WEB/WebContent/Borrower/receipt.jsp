<%@include file="../index.jsp"%>
<%@page import="com.gcit.lms.entity.BookLoan"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="java.util.List"%>
<%
	BookLoan loanedBook = (BookLoan) request.getAttribute("receipt");
%>

<div style="margin-top: 80px; margin-left: 10px;">
<section>
<h4>
<b>Branch Name:</b> <%=loanedBook.getBranch().getBranchName()%>
<br>
<b>Branch Address:</b> <%=loanedBook.getBranch().getBranchAddress()%> 
<br>
<br>
<%-- <b>Card No:</b> <%=loanedBook.getBorrower().getCardNo()%>  --%>
<br>
<b>Borrower Name:</b> <%=loanedBook.getBorrower().getBorrowerName()%> 
</h4>

</section>
	<section>
		<table class="table">
			<col width="130">
			<tr>
				<!-- <td>Book Id</td> -->
				<td>Book Name</td>
			</tr>
			<tr>
				<%-- <td><%=loanedBook.getBook().getBookId()%></td> --%>
				<td><i><%=loanedBook.getBook().getTitle()%></i></td>
			</tr>

			<tr>
				<!-- <td>Author Id</td> -->
				<td>Author Name</td>
			</tr>
			<%for(Author a : loanedBook.getBook().getAuthors()) { %>
			<tr>
				<%-- <td><%=a.getAuthorId()%></td> --%>
				<td><%=a.getAuthorName()%></td>
			</tr>
			<% } %>

			<tr>
				<!-- <td>Genre Id</td> -->
				<td><b>Genre Name</b></td>
			</tr>
			<%for(Genre g : loanedBook.getBook().getGenres()) { %>
			<tr>
				<%-- <td><%=g.getGenreId()%></td> --%>
				<td><%=g.getGenreName()%></td>
			</tr>
			<% } %>
		</table>
	</section>
</div>