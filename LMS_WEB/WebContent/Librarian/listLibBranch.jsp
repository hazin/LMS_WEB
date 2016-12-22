<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
	List<LibraryBranch> branches = new AdminService().getAllBranches();
%>
<%@include file="./index.jsp"%>
<script>
		
	function setId(id){
		document.getElementById("branchViewId").value = id;
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
		<td><%=b.getBranchName()%></td>
		<td><%=b.getBranchAddress()%></td>
		<td><button class="btn btn-primary" onclick="javascript:setId(<%=b.getBranchId()%>);">Select Branch</button></td>
	</tr>
	<% } %>
</table>
<!-- Needs to go back to parent folder to find servlet file -->
<form action="../viewLibBooks" method="post" name="viewFrm">
	<input type="hidden" name="branchViewId" id="branchViewId"/>
</form>
</section>
</div>