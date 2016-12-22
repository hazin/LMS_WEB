<%@ page import="com.gcit.lms.entity.LibraryBranch"%>
<%@ page import="com.gcit.lms.service.AdminService"%>
<%
	Integer branchId = Integer.parseInt(request.getParameter("branchId"));
	LibraryBranch branch = null;
	AdminService service = new AdminService();
	branch = service.getBranchById(branchId);
%>
<%@include file="index.jsp"%>
<div>
	<h2>Hello Admin!</h2>
	<h3>Enter Branch Details to Edit</h3>
	
	<form action="editBranch" method="post">
		Enter Branch Name: <input type="text" name="branchName"
			value="<%=branch.getBranchName()%>"><br /> <input
			type="hidden" name="branchId" value="<%=branch.getBranchId()%>">
			
		<form action="editBranch" method="post">
		Enter Branch Address: <input type="text" name="branchAddress"
			value="<%=branch.getBranchAddress()%>"><br /><%--  <input
			type="hidden" name="branchId" value="<%=branch.getBranchId()%>">
			 --%>
			
		<button type="submit">Edit Branch!</button>
	</form>
</div>