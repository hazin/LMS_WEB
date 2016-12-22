<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
	List<Borrower> borrowers = new AdminService().getAllBorrowers();
%>
<%@include file="index.jsp"%>
<script>
	var borrowerId;
	
	function deleteBorrower(id) {
		//document.location.href = "deleteAuthor?authorId="+id;
		
		document.getElementById("cardNo").value = id;
		document.deleteFrm.submit();
	}
	
	function setId(id){
		borrowerId = id;
	}
	
	function getId(){		
		document.getElementById("cardEditNo").value = borrowerId;	
	}
</script>

<div style="margin-top:80px;margin-left:10px;">
<section>
<table class="table">
	<tr>
		<!-- <td>Card No.</td> -->
		<td><b>Borrower Name</b></td>
		<td><b>Borrower Address</b></td>
		<td><b>Borrower Phone</b></td>
		<td><b>Edit</b></td>
		<td><b>Delete</b></td>
	</tr>
	<%for(Borrower b : borrowers) { %>	
	<tr>
		<%-- <td><%=b.getCardNo()%></td> --%>
		<td><%=b.getBorrowerName()%></td>
		<td><%=b.getBorrowerAddress()%></td>
		<td><%=b.getBorrowerPhone()%></td>
		
		<td><button class="btn btn-info"  
		onclick="javascript:location.href='editBorrower?cardNo=<%=b.getCardNo()%>&borrowerName=<%=b.getBorrowerName()%>&borrowerAddress=<%=b.getBorrowerAddress()%>&borrowerPhone=<%=b.getBorrowerPhone()%>'">Edit</button></td>
		
		<%-- <td><button class="btn btn-success" data-toggle="modal" data-target="#myModal" onclick="javascript:setId(<%=b.getCardNo()%>);">Edit</button></td> --%>
		<td><button class="btn btn-danger" onclick="javascript:deleteBorrower(<%=b.getCardNo()%>);">Delete</button></td>
	</tr>
	<% } %>
</table>
<form action="deleteBorrower" method="post" name="deleteFrm">
	<input type="hidden" name="cardNo" id="cardNo"/>
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
        <h4 class="modal-title">Update Borrower</h4>
      </div>
      <div class="modal-body">
        <form method="post" action="editBorrower">
        <input type="hidden" name="cardEditNo" id="cardEditNo"/><br/>
        <input type="text" placeholder="Enter Borrower's new Name" name="borrowerName"/><br/>
        <br/>
        <input type="text" placeholder="Enter Borrower's new Address" name="borrowerAddress"/><br/>
        <br/>
        <input type="text" placeholder="Enter Borrower's new Phone" name="borrowerPhone"/><br/>
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