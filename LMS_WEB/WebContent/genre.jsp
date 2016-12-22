<%@include file="index.jsp"%>
<div style="margin-top:80px;margin-left:10px;">
<section>
<%-- <% if(request.getAttribute("result") != null){ %>
	<%=request.getAttribute("result")%><br/>
<% } %>
 --%>
${result}<br/> 
<a href="addGenre.jsp">Add Genre</a><br/>
<br/>
<a href="listGenre.jsp">List Genres</a>
</section>
</div>