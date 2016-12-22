<%@include file="index.jsp"%>

<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
	String cardNo = request.getParameter("cardNo");
	List<LibraryBranch> branches = new AdminService().getAllBranches();
%>
<script>		
	function setId(id){
		document.getElementById("branchViewId").value = id;	
		console.log(<%=cardNo%>);
		document.viewFrm.submit();
	}
</script>
<div style="margin-top:80px;margin-left:10px;">
<section>
<table class="table">
	<tr>
		<!-- <td>Branch Id</td> -->
		<td><b>Branch Name</b></td>
		<td><b>Branch Address</b></td>
		
	</tr>
	<%for(LibraryBranch b : branches) { %>	
	<tr>
		<%-- <td><%=b.getBranchId()%></td> --%>
		<td><b><%=b.getBranchName()%></b></td>
		<td><b><%=b.getBranchAddress()%></b></td>
		<td><button class="btn btn-success" onclick="javascript:setId(<%=b.getBranchId()%>);">Select Branch</button></td>
	</tr>
	<% } %>
</table>
<!-- Needs to go back to parent folder to find servlet file -->
<form action="../viewAvailBooks" method="post" name="viewFrm">
	<input type="hidden" name="branchViewId" id="branchViewId"/>
	<input type="hidden" name="cardNo" id="cardNo" value="<%=cardNo%>"/>
</form>
</section>
</div>