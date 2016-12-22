<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
	Book book = new Book();
	book = (Book)request.getAttribute("CompleteBook");
%>
<%@include file="index.jsp"%>

<style>
table {
	margin: 0 auto; /* or margin: 0 auto 0 auto */
}
</style>

<script>
	var authorId;
	var genreId;
	
	function deleteAuthor(id) {
		//document.location.href = "deleteAuthor?authorId="+id;
		
		document.getElementById("authorId").value = id;
		document.deleteAuthorFrm.submit();
	}
	
	function deleteBook(id) {
		//document.location.href = "deleteAuthor?authorId="+id;
		
		document.getElementById("bookDeleteId").value = id;
		document.deleteFrm.submit();
	}
	
	function deleteGenre(id) {
		//document.location.href = "deleteAuthor?authorId="+id;
		
		document.getElementById("genreId").value = id;
		document.deleteGenreFrm.submit();
	}
	
	function setAuthorId(id){
		authorId = id;		
	}
	
	function setGenreId(id){
		genreId = id;		
	}
	
	function getGenreId(){		
		document.getElementById("genreEditId").value = genreId;	
	}
	
	function getAuthorId(){		
		document.getElementById("authorEditId").value = authorId;	
	}
	/* function editBook(id) {
		//document.location.href = "deleteAuthor?authorId="+id;
		document.getElementById("bookEditId").value = id;
		document.editFrm.submit();
	} */
</script>

<div style="margin-top: 80px; margin-left: 10px;">
	<section>
		<table class="table">
			<col width="130">
			<tr>
				<!-- <td>Book Id</td> -->
				<td><b>Book Name</b></td>
			</tr>
			<tr>
				<%-- <td><%=book.getBookId()%></td> --%>
				<td><i><%=book.getTitle()%></i></td>
				<!-- <td><button class="btn btn-success" data-toggle="modal"
						data-target="#bookEditModal">Edit</button></td> -->
				<td><button class="btn btn-danger"
						onclick="javascript:deleteBook(<%=book.getBookId()%>);">Delete</button></td>
			</tr>

			<tr>
				<!-- <td>Author Id</td> -->
				<td><b>Author Name</b></td>
			</tr>
			<%for(Author a : book.getAuthors()) { %>
			<tr>
				<%-- <td><%=a.getAuthorId()%></td> --%>
				<td><%=a.getAuthorName()%></td>
				<%-- <td><button class="btn btn-success" data-toggle="modal"
						data-target="#authorEditModal"
						onclick="javascript:setAuthorId(<%=a.getAuthorId()%>);">Edit</button></td> --%>
				<td><button class="btn btn-danger"
						onclick="javascript:deleteAuthor(<%=a.getAuthorId()%>);">Delete</button></td>
			</tr>
			<% } %>
			<tr>
			
			<%-- <td><button class="btn btn-info"  
	onclick="javascript:location.href='editAuthor?authorId=<%=a.getAuthorId()%>&authorName=<%=a.getAuthorName()%>'">Edit Author</button></td> --%>
			
				 <td><button class="btn btn-info" data-toggle="modal"
						data-target="#addAuthorModal">Edit Author</button></td>
			</tr>

			<tr>
				<!-- <td>Genre Id</td> -->
				<td><b>Genre Name</b></td>
			</tr>
			<%for(Genre g : book.getGenres()) { %>
			<tr>
				<%-- <td><%=g.getGenreId()%></td> --%>
				<td><%=g.getGenreName()%></td>
				<%-- <td><button class="btn btn-success" data-toggle="modal"
						data-target="#genreEditModal"
						onclick="javascript:setGenreId(<%=g.getGenreId()%>);">Edit</button></td> --%>
				<td><button class="btn btn-danger"
						onclick="javascript:deleteGenre(<%=g.getGenreId()%>);">Delete</button></td>
			</tr>
			<% } %>
			<tr>
				<td><button class="btn btn-info" data-toggle="modal"
						data-target="#addGenreModal">Edit Genre</button></td>
			</tr>
		</table>

		<%-- <table class="table">
<col width="130">
	<tr>
		<td><button class="btn btn-success" data-toggle="modal" data-target="#myModal">Edit</button></td>
		<td><button class="btn btn-danger" onclick="javascript:deleteBook(<%=book.getBookId()%>);">Delete</button></td>
	</tr>
</table> --%>
	</section>
	<form action="deleteBook" method="post" name="deleteFrm">
		<input type="hidden" name="bookDeleteId" id="bookDeleteId" />
	</form>
	
	<form action="deleteBookAuthor" method="post" name="deleteAuthorFrm">
	<input type="hidden" name="bookEditId" value="<%=book.getBookId()%>" />
	<input type="hidden" name="authorId" id="authorId"/>
	</form>
	
	<form action="deleteBookGenre" method="post" name="deleteGenreFrm">
	<input type="hidden" name="bookEditId" value="<%=book.getBookId()%>" />
	<input type="hidden" name="genreId" id="genreId"/>
	</form>
	
	<!-- <form action="editBook" method="post" name="editFrm">
	<input type="hidden" name="bookEditId" id="bookEditId"/>
</form> -->

	<!-- Modal -->
	<div id="bookEditModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Update Book Title</h4>
				</div>
				<div class="modal-body">
					<form method="post" action="updateBook">
						<input type="hidden" name="bookEditId"
							value="<%=book.getBookId()%>" /><br /> <input type="text"
							placeholder="Enter Book's new Title" name="bookTitle"
							value="<%=book.getTitle()%>" /><br /> 
							<br /> <input type="submit" />
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Modal -->
<div id="addAuthorModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Add New Author To Book</h4>
			</div>
			<div class="modal-body">
				<form method="post" action="addAuthorBook">
					<input type="hidden" name="bookEditId"
						value="<%=book.getBookId()%>" /> <input type="text"
						placeholder="Enter Author Name" name="authorAddName" /><br /> <br/><input
						type="submit" />
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>

	</div>
</div>

<!-- Modal -->
<div id="addGenreModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Add New Genre To Book</h4>
			</div>
			<div class="modal-body">
				<form method="post" action="addGenreBook">
					<input type="hidden" name="bookEditId"
						value="<%=book.getBookId()%>" /> <input type="text"
						placeholder="Enter Genre Name" name="genreAddName" /><br /> 
						<br/>
						<input
						type="submit" />
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>

	</div>
</div>

<!-- Modal -->
<div id="authorEditModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Update Author's Name</h4>
			</div>
			<div class="modal-body">
				<form method="post" action="editAuthorBook">
				<input type="hidden" name="authorEditId" id="authorEditId"/>
					<input type="hidden" name="bookEditId"
						value="<%=book.getBookId()%>" /> 
					<input type="text"
						placeholder="Enter Authors's new Name" name="authorName" /><br /> 
						<br/> <input type="submit" onclick="javascript:getAuthorId();"/>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>

	</div>
</div>

<!-- Modal -->
<div id="genreEditModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Update Genre's Name</h4>
			</div>
			<div class="modal-body">
				<form method="post" action="editGenreBook">
					<input type="hidden" name="genreEditId" id="genreEditId"/>
					<input type="hidden" name="bookEditId"
						value="<%=book.getBookId()%>" /> <input type="text"
						placeholder="Enter Genre's new Name" name="genreEditName" /><br/> 
						<br/>
						<input
						type="submit" onclick="javascript:getGenreId();"/>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>

	</div>
</div>