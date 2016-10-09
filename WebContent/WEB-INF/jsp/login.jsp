<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>

<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap-custom.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/font-awesome.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/myStyles.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/login.css">
<script type="text/javascript" src="./presentationResources/js/matchHeight.js"></script>
<title>Login Page</title>
<style>



</style>

<script>
	 if (top.location!= self.location) {
		
		var href = self.location.href;
		
		//alert("href is : "+href);
		 
	    top.location = self.location.href;
	 }
	 
	 $( document ).ready(function() {
		$(div).matchHeight(byRow);
	  });

</script>

</head>
<body background="./presentationResources/images/bgimg.jpg" >

	

	<div id="login-box" class="login-box responsive">

	
		<div class="logoimg responsive"><img  width='560' height='430' src='./presentationResources/images/paperandglassFoldedCoffeeStain2.png'/></div>
		
	

		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty message}">
			<div class="message">${message}</div>
		</c:if>
			<br/><br/>


		<form 
			action="<c:url value='/j_spring_security_check' />" method='POST'>

			<div id="loginFields" class="loginFields responsive">
			
					<p>Sign in already a user otherwise <a href="signup">Sign up</a></p>
			
				<div id="users" class="userFields responsive">
					Username:
					<input class="usernameInput responsive" type='text' name='username' placeholder="Username" style="width:60%;"><span class="glyphicon glyphicon-user iconspanLogin"></span>
				 </div>
				<div id="pass"  class="passFields responsive">
					Password:
					<input class="usernameInput responsive" placeholder="Password" type='password' name='password' style="width:60%; margin-left:0.3em;"/><span class="glyphicon glyphicon-lock iconspanLogin"></span>
				</div>
				
					
			</div>	
			<div >
		<button name="submit" type="submit" class="loginButton responsive" 
						value="Login" >	 <span class="glyphicon glyphicon-log-in"></span> &nbsp; Login </button>
			</div>			
		</form>
	</div>

</body>
</html>