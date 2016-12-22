<%@include file="index.jsp"%>
<div style="margin-top:80px;margin-left:10px;">
<section>
${result}
<form method="post" action="../logIn">
	<input type="text" placeholder="Enter Card Number" name="cardNo"/><br/>
	<br/>
	<input type="submit"/>
</form>
</section>
</div>