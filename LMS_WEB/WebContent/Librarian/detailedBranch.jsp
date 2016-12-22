<%@page import="com.gcit.lms.entity.BookCopies"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
 	
    @SuppressWarnings("unchecked")  List<BookCopies> libBookList = (List<BookCopies>)request.getAttribute("CompleteCopies");
	LibraryBranch branch = new LibraryBranch();
	branch = (LibraryBranch)request.getAttribute("CompleteBranch");
%>
<%@include file="/index.jsp"%>
<script>
	
	var bookId;
	
	function UpdateCopies(id){
		bookId = id;
	}
	
	function getId(){		
		document.getElementById("bookEditId").value = bookId;	
	}
</script>


<style>
table {
	margin: 0 auto; /* or margin: 0 auto 0 auto */
}
</style>

<div style="margin-top:80px;margin-left:10px;">
<section>
<h3> <%=branch.getBranchName()%> </h3>
<h4> <%=branch.getBranchAddress()%> </h4>


<td><button class="btn btn-info"  
		onclick="javascript:location.href='editBranch?branchId=<%=branch.getBranchId()%>&branchName=<%=branch.getBranchName()%>&branchAddress=<%=branch.getBranchAddress()%>'">Edit</button></td>


<!-- <button class="btn btn-primary" data-toggle="modal" data-target="#branchModal">Update Branch Info</button> -->
<br>
<br>
</section>

<section>
<table class="table">
	<tr>
		<td><b>Book Info</b></td>
		<td><b>No. Of Copies</b></td>
	</tr>
	<%for(BookCopies booklist : libBookList) { %>	
	<tr>
		<td>'<%=booklist.getBook().getTitle() + "' by "
				+ booklist.getBook().getAuthors().get(0).getAuthorName()%></td>
		<td><%=booklist.getNoOfCopies()%></td>
		
		
		<td><button class="btn btn-info"  
		onclick="javascript:location.href='editNoOfCopies?branchId=<%=branch.getBranchId()%>&noOfCopies=<%=booklist.getNoOfCopies()%>&bookId=<%=booklist.getBook().getBookId()%>'">Edit</button></td>
		
		<%-- <td><button class="btn btn-primary" data-toggle="modal" data-target="#copiesModal" onclick="javascript:UpdateCopies(<%=booklist.getBook().getBookId()%>);">Update</button></td> --%>
	</tr>
	<% } %>
</table>
</section>
</div>

<!-- Modal -->
<div id="copiesModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Update No. Of Copies</h4>
      </div>
      <div class="modal-body">
        <form method="post" action="editNoOfCopies">
        <input type="hidden" name="bookEditId" id="bookEditId"/>
        <input type="hidden" name="branchEditId" value="<%=branch.getBranchId()%>"/>
        <input type="text" placeholder="Enter Number Of Copies" name="newNoOfCopies"/><br/>
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


<!-- Modal -->
<div id="branchModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Update Branch</h4>
      </div>
      <div class="modal-body">
        <form method="post" action="editLibBranch">
        <input type="hidden" name="branchUpdateId" value="<%=branch.getBranchId()%>" /><br/>
        <input type="text" placeholder="Enter Branch's new Name" name="branchName"/><br/>
        <br/>
        <input type="text" placeholder="Enter Branch's new Address" name="branchAddress"/><br/>
        <br/>
        <input type="submit"/>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>