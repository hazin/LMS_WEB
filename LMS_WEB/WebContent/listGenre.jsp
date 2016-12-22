<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
	List<Genre> genre = new AdminService().getAllGenres();
%>
<%@include file="index.jsp"%>
<script>
	var genreId;
	
	function deleteGenre(id) {
		//document.location.href = "deleteAuthor?authorId="+id;
		
		document.getElementById("genreId").value = id;
		document.deleteFrm.submit();
	}
	
	function setId(id){
		genreId = id;
	}
	
	function getId(){		
		document.getElementById("genreEditId").value = genreId;	
	}
</script>

<div style="margin-top:80px;margin-left:10px;">
<section>
<table class="table">
	<tr>
		<!-- <td>Genre Id</td> -->
		<td><b>Genre Name</b></td>
		<td><b>Edit</b></td>
		<td><b>Delete</b></td>
	</tr>
	<%for(Genre g : genre) { %>	
	<tr>
		<%-- <td><%=g.getGenreId()%></td> --%>
		<td><%=g.getGenreName()%></td>
		
		<td><button class="btn btn-info"  
		onclick="javascript:location.href='editGenre?genreId=<%=g.getGenreId()%>&genreName=<%=g.getGenreName()%>'">Edit</button></td>
		
		<%-- <td><button class="btn btn-success" data-toggle="modal" data-target="#myModal" onclick="javascript:setId(<%=g.getGenreId()%>);">Edit</button></td> --%>
		<td><button class="btn btn-danger" onclick="javascript:deleteGenre(<%=g.getGenreId()%>);">Delete</button></td>
	</tr>
	<% } %>
</table>
<form action="deleteGenre" method="post" name="deleteFrm">
	<input type="hidden" name="genreId" id="genreId"/>
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
        <h4 class="modal-title">Update Genre Name</h4>
      </div>
      <div class="modal-body">
        <form method="post" action="editGenre">
        <input type="hidden" name="genreEditId" id="genreEditId"/><br/>
        <input type="text" placeholder="Enter Genre's new Name" name="genreName"/><br/>
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