<%@include file="../index.jsp"%>
<%@page import="com.gcit.lms.entity.Borrower"%>
<%
	Borrower borrower = (Borrower) request.getAttribute("borrower");
%>
<div style="margin-top:80px;margin-left:10px;">
<script>		
	function submitId(){		
		document.userForm.submit();
	}
	function submitRetId(){		
		document.userRetForm.submit();
	}
</script>
<section>
<button class="btn btn-lg btn-primary" onclick="javascript:submitId();">Check Out Book</button>   
 <form action="Borrower/listBranches.jsp" method="post" name="userForm">
	 <input type="hidden" id="cardNo" name="cardNo" value="<%=borrower.getCardNo()%>">
</form>
<br>
<br>
<button class="btn btn-lg btn-primary" onclick="javascript:submitRetId();">Return Book</button>
<form action="loanedBooks" method="post" name="userRetForm">
	 <input type="hidden" id="cardNo" name="cardNo" value="<%=borrower.getCardNo()%>">
</form>
</section>
</div>