<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
	List<Publisher> publishers = new AdminService().getAllPublishers();
%>
<%@include file="index.jsp"%>
<script>
	var pubId;
	
	function deletePublisher(id) {
		//document.location.href = "deleteAuthor?authorId="+id;
		
		document.getElementById("pubId").value = id;
		document.deleteFrm.submit();
	}
	
	function setId(id){
		pubId = id;
	}
	
	function getId(){		
		document.getElementById("pubEditId").value = pubId;	
	}
</script>

<div style="margin-top:80px;margin-left:10px;">
<section>
<table class="table">
	<tr>
		<!-- <td>Publisher Id</td> -->
		<td><b>Publisher Name</b></td>
		<td><b>Publisher Address</b></td>
		<td><b>Publisher Phone</b></td>
		<td><b>Edit</b></td>
		<td><b>Delete</b></td>
	</tr>
	<%for(Publisher p : publishers) { %>	
	<tr>
		<%-- <td><%=p.getPublisherId()%></td> --%>
		<td><%=p.getPublisherName()%></td>
		<td><%=p.getPublisherAddress()%></td>
		<td><%=p.getPublisherPhone()%></td>
		
		<td><a class="btn btn-info" href='editPublisher?publisherId=<%=p.getPublisherId()%>&publisherName=<%=p.getPublisherName()%>
		&publisherAddress=<%=p.getPublisherAddress()%>&publisherPhone<%=p.getPublisherPhone()%>'>Edit</a></td>
		
		<%-- <td><button class="btn btn-success" data-toggle="modal" data-target="#myModal" onclick="javascript:setId(<%=p.getPublisherId()%>);">Edit</button></td> --%>
		<td><button class="btn btn-danger" onclick="javascript:deletePublisher(<%=p.getPublisherId()%>);">Delete</button></td>
	</tr>
	<% } %>
</table>
<form action="deletePublisher" method="post" name="deleteFrm">
	<input type="hidden" name="pubId" id="pubId"/>
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
        <h4 class="modal-title">Update Borrower Name</h4>
      </div>
      <div class="modal-body">
        <form method="post" action="editPublisher">
        <input type="hidden" name="pubEditId" id="pubEditId"/><br/>
        <input type="text" placeholder="Enter Publisher's new Name" name="pubName"/><br/>
        <br/>
        <input type="text" placeholder="Enter Publisher's new Address" name="pubAddress"/><br/>
        <br/>
        <input type="text" placeholder="Enter Publisher's new Phone" name="pubPhone"/><br/>
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