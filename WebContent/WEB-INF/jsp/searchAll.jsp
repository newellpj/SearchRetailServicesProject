<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap-custom.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/myStyles.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/font-awesome.css">

<script type="text/javascript" src="./presentationResources/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery-ui.js"></script>
<script type="text/javascript" src="./presentationResources/js/reviews.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery.jscroll.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery.jscroll.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search all</title>
<head>

<script>
	$(document).ready(function() {

	
	   var html = $(".bookRevList").html();
		 
	
		populateSearchAllOptions();

		
		   
	});
	
	(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.6";
	  fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));
     
</script>

<style>

</style>
</head>
<body background="./presentationResources/images/bgimg.jpg" >

<br/>
	<div id="all-search-box" class="all-search-box">


		<c:if test="${not empty error}">
			<div class="error alert alert-error">${error}</div>
		</c:if>
		<c:if test="${not empty message}">
			<div class="message">${message}</div>
		</c:if>

			<form:form id="reviewsForm"  commandName="bookReviewsModel">
	
		<table style="width:100%;">
			<tr><td>Search a Book to Review</td></tr>		
				<tr>
					<td>
						<select style="width:40px; height:32px; background-color: #A2C5D0; border-radius: 7px 0px 0px 7px !important;" id="searchAllSelect"></select>
						<input id="titleText" style="width:85% !important; border-radius: 0px !important; margin-left: -4px;"  type='text' name='titleText'><span class="glyphicon glyphicon-search iconspan2"></span>
					</td>
		
				</tr>
			</table>
			
			  <div class="tagSearches" style="margin-left:200px !important;"> 
						<table width="100%">
							<tr>
								<td colspan='1'></td><td> <button id="searchBook" name="searchBook" type="button" onclick="performAjaxSearch();" style="width: 110px; height: 42px;" value="Search.." > 
								<span class="glyphicon glyphicon-eye-open" style="padding-right:5px;" ></span>Search...
								</button>
								<button id="resetSearch" class="resetSearch" style="width: 110px; height: 42px;" name="resetSearch" type="button" onclick="resetTheSearch();"  value="Reset" >
										<span class="glyphicon glyphicon-erase" style="padding-right:5px;" ></span>Reset
								</button>
								</td>
									
	
							</tr>
						</table>
					</div>		
			<br/>

			
		</form:form>
</div>	
<div id="resultsSection" class="resultsSection">
		<form:form id="searchResults" class="searchResults">
		
				<div id="search" class="search" style="display:none; width:1000px !important;">
					<ul id="bookRevList" class="bookRevList" >				
					</ul>
			</div>
		
		</form:form>
</div>		
		
	

<div class="facebookFooter" >
	<div id="fb-root" ></div>
<div class="fb-like" data-href="http://www.w3schools.com/" data-layout="standard" data-action="like" data-size="small" data-show-faces="true" data-share="true"></div>
</div>
</body>
</html>