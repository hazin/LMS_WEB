<%@include file="index.jsp"%>
<div style="margin-top:80px;margin-left:10px;">
<section>
<%-- <% if(request.getAttribute("result") != null){ %>
	<%=request.getAttribute("result")%><br/>
<% } %>
 --%>
${result}<br/> 
<a href="addBook.jsp">Add Book</a><br/>
<br/>
<a href="listBooks.jsp">List Books</a>
</section>
</div>
<!-- this is a comment -->