<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
	List<LibraryBranch> branches = new AdminService().getAllBranches();
%>
<%@include file="index.jsp"%>
<script>
	var branchId;
	
	function deleteBranch(id) {
		//document.location.href = "deleteAuthor?authorId="+id;
		
		document.getElementById("branchId").value = id;
		document.deleteFrm.submit();
	}
	
	function setId(id){
		branchId = id;
	}
	
	function getId(){		
		document.getElementById("branchEditId").value = branchId;	
	}
</script>

<div style="margin-top:80px;margin-left:10px;">
<section>
<table class="table">
	<tr>
		<!-- <td>Branch Id</td> -->
		<td><b>Branch Name</b></td>
		<td><b>Branch Address</b></td>
		<td><b>Edit</b></td>
		<td><b>Delete</b></td>
	</tr>
	<%for(LibraryBranch b : branches) { %>	
	<tr>
		<%-- <td><%=b.getBranchId()%></td> --%>
		<td><%=b.getBranchName()%></td>
		<td><%=b.getBranchAddress()%></td>
		
		<td><button class="btn btn-info"  
		onclick="javascript:location.href='editBranch.jsp?branchId=<%=b.getBranchId()%>&branchName=<%=b.getBranchName()%>'">Edit</button></td>
		
		<%-- <td><button class="btn btn-success" data-toggle="modal" data-target="#myModal" onclick="javascript:setId(<%=b.getBranchId()%>);">Edit</button></td> --%>
		<td><button class="btn btn-danger" onclick="javascript:deleteBranch(<%=b.getBranchId()%>);">Delete</button></td>
	</tr>
	<% } %>
</table>
<form action="deleteBranch" method="post" name="deleteFrm">
	<input type="hidden" name="branchId" id="branchId"/>
</form>

</section>
</div>

<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Update Branch</h4>
      </div>
      <div class="modal-body">
        <form method="post" action="editBranch">
        <input type="hidden" name="branchEditId" id="branchEditId"/><br/>
        <input type="text" placeholder="Enter Branch's new Name" name="branchName"/><br/>
        <br/>
        <input type="text" placeholder="Enter Branch's new Address" name="branchAddress"/><br/>
        <br/>
        <input type="submit" onclick="javascript:getId();"/>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>