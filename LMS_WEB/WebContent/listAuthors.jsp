<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
	List<Author> authors = new AdminService().getAllAuthors();
	Author authorToEdit = new Author();

	AdminService service = new AdminService();
	
	
	String searchString = (String) request.getAttribute("searchString");
	Integer count = service.getAuthorCount(searchString);
	System.out.println("count:"+count);
	int pages = 0;
	Integer pageSize = Integer.valueOf(10);
	if(count%pageSize > 0){
		pages = count/10 + 1;
	}else{
		pages = count/10;
	}
	
%>
<%@include file="index.jsp"%>

<script>
	var authorId;
	
	function deleteAuthor(id) {
		//document.location.href = "deleteAuthor?authorId="+id;
		
		document.getElementById("authorId").value = id;
		document.deleteFrm.submit();
	}
	
	function setId(id){
		authorId = id;
	}
	
	function getId(){		
		document.getElementById("authorEditId").value = authorId;	
	}
</script>

<script type="text/javascript">

function searchAuthors(){
	
	$.ajax({
		  url: "searchAuthors",
		  data: 
		  { 
			  searchString: $('#searchString').val()
			  
		  }
		}).done(function(data) {
		 	$("#authorsTable").html(data);
		});
}

</script> 

<div style="margin-top: 80px; margin-left: 10px;">

<form action="searchAuthors" method="post">
			<div class="input-group">
				<input type="text" class="form-control" placeholder="Author Name"
					aria-describedby="basic-addon1" name="searchString" id="searchString" onkeypress="searchAuthors()">
				
			</div>
		</form>


	<section id="authorsTable">
		<nav aria-label="Page navigation">
			<ul class="pagination">
				<li><a  href="#" aria-label="Previous"> <span
						aria-hidden="true">&laquo;</span>
				</a></li>
				<%
					for (int i = 1; i <= pages; i++) {
				%>
				<li><a id="pageLink" href="pageAuthors?pageNo=<%=i%><% if(searchString != null) out.print("&searchString="+searchString);%>"><%=i%></a></li>
				<%
					}
				%>
				<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>
		</nav>
		<table class="table">
			<tr>
				<!-- <td>Author Id</td> -->
				<td><b>Author Name</b></td>
				<td><b>Edit</b></td>
				<td><b>Delete</b></td>
			</tr>
			<%System.out.println("authors#:"+authors.size());
			int i =1;	
			for (Author a : authors) 	{
				
				if (i<=10  ){
				
			%>
			<tr>
				<%-- <td><%=a.getAuthorId()%></td> --%>
				<td><i><%=a.getAuthorName()%></i></td>


				<td><button class="btn btn-info"
						onclick="javascript:location.href='editAuthor?authorId=<%=a.getAuthorId()%>&authorName=<%=a.getAuthorName()%>'">Edit</button></td>

				<td><button class="btn btn-danger"
						onclick="javascript:deleteAuthor(<%=a.getAuthorId()%>);">Delete</button></td>
				
			</tr>
			
			<%
					} i++;
			}
				%>
		</table>
		<form action="deleteAuthor" method="post" name="deleteFrm">
			<input type="hidden" name="authorId" id="authorId" />
		</form>

	</section>
</div>

