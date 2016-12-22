<%@include file="index.jsp"%>
<div style="margin-top:80px;margin-left:10px;">
<section>
<%-- <% if(request.getAttribute("result") != null){ %>
	<%=request.getAttribute("result")%><br/>
<% } %>
 --%>
${result}<br/> 
<a href="addBranch.jsp">Add Branch</a><br/>
<br/>
<a href="listBranch.jsp">List Branch</a>
</section>
</div>