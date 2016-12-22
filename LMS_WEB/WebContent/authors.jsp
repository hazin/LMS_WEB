<%@include file="index.jsp"%>
<div style="margin-top:80px;margin-left:10px;">
<section>
<%-- <% if(request.getAttribute("result") != null){ %>
	<%=request.getAttribute("result")%><br/>
<% } %>
 --%>
${result}<br/> 
<a href="addAuthor.jsp">Add Author</a><br/>
<br/>
<a href="pageAuthors?pageNo=1&searchString=''">List Authors</a>
</section>
</div>