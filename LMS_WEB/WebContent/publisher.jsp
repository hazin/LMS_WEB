<%@include file="index.jsp"%>
<div style="margin-top: 80px; margin-left: 10px;">
	<section>
		<%-- <% if(request.getAttribute("result") != null){ %>
	<%=request.getAttribute("result")%><br/>
<% } %>
 --%>
		${result}<br /> <a href="addPublisher.jsp">Add Publisher</a><br /> <br />
		<a href="listPublisher.jsp">List Publishers</a>
	</section>
</div>