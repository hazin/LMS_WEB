<%@ page import="com.gcit.lms.entity.Publisher"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%
	Integer publisherId = Integer.parseInt(request.getParameter("publisherId"));
	Publisher publisher = null;
	AdminService service = new AdminService();
	publisher = service.getPublisherById(publisherId);
%>
<%@include file="index.jsp"%>
<div>
	<h2>Hello Admin!</h2>
	<h3>Enter Branch Details to Edit</h3>
	
	<form action="editPublisher" method="post">
		Enter Publisher Name: <input type="text" name="publisherName"
			value="<%=publisher.getPublisherName()%>"><br /> <input
			type="hidden" name="publisherId" value="<%=publisher.getPublisherId()%>">
			
		<form action="editPublisher" method="post">
		Enter Publisher Address: <input type="text" name="publisherAddress"
			value="<%=publisher.getPublisherAddress()%>"><br /><%--  <input
			type="hidden" name="branchId" value="<%=branch.getBranchId()%>">
			 --%>
			 
		<form action="editPublisher" method="post">
		Enter Publisher Phone: <input type="text" name="publisherPhone"
			value="<%=publisher.getPublisherPhone()%>"><br />	 
			
		<button type="submit">Edit Branch!</button>
	</form>
</div>