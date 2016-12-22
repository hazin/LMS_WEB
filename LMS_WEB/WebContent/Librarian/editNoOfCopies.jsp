<%@page import="com.gcit.lms.entity.BookCopies"%>
<%@ page import="com.gcit.lms.entity.Borrower"%>
<%@ page import="com.gcit.lms.entity.LibraryBranch"%>
<%@ page import="com.gcit.lms.entity.Book"%>
<%@ page import="com.gcit.lms.service.LibrarianService"%>
<%@ page import="java.util.List"%>
<%@include file="index.jsp"%>

<%
	List<BookCopies> libBookList = (List<BookCopies>)request.getAttribute("CompleteCopies");
	Integer bookId = Integer.parseInt(request.getParameter("bookId"));
	Book book = null;
	Integer branchId = Integer.parseInt(request.getParameter("branchId"));
	LibraryBranch branch = null;
	LibrarianService librarian= new LibrarianService();
	
	BookCopies bookCopies = bookCopies.getnoOfCopies(bookId, branchId);
%>
<%@include file="index.jsp"%>
<div>
	<h2>Hello Admin!</h2>
	<h3>Enter Borrower Details to Edit</h3>
	<form action="editNoOfCopies" method="post">
		Enter Number of Copies: <input type="text" name="noOfCopies"
			value="<%=booklist.getNoOfCopies()%>"><br \/>
			
		<button type="submit">Edit Copies!</button>
	</form>
</div>