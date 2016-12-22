<%@ page import="com.gcit.lms.entity.Borrower"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%
	Integer cardNo = Integer.parseInt(request.getParameter("cardNo"));
	Borrower borrower = null;
	AdminService service = new AdminService();
	borrower = service.getBorrowerById(cardNo);
	
%>
<%@include file="index.jsp"%>
<div>
	<h2>Hello Admin!</h2>
	<h3>Enter Borrower Details to Edit</h3>
	
	<form action="editBorrower" method="post">
		Enter Borrower Name: <input type="text" name="borrowerName"
			value="<%=borrower.getBorrowerName()%>"><br /> <input
			type="hidden" name="cardNo" value="<%=borrower.getCardNo()%>">
			
		<form action="editBorrower" method="post">
		Enter Borrower Address: <input type="text" name="borrowerAddress"
			value="<%=borrower.getBorrowerAddress()%>"><br/><%--  <input
			type="hidden" name="branchId" value="<%=branch.getBranchId()%>">
			 --%>
			 
		<form action="editBorrower" method="post">
		Enter Borrower Phone: <input type="text" name="borrowerPhone"
			value="<%=borrower.getBorrowerPhone()%>"><br/>	 
			
		<button type="submit">Edit Branch!</button>
	</form>
</div>