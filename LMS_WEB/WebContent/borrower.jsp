<%@include file="index.jsp"%>
<div style="margin-top:80px;margin-left:10px;">
<section>
<%-- <% if(request.getAttribute("result") != null){ %>
	<%=request.getAttribute("result")%><br/>
<% } %>
 --%>
${result}<br/> 
<a href="addBorrower.jsp">Add Borrower</a><br/>
<br/>
<a href="listBorrower.jsp">List Borrower</a>
</section>
</div>