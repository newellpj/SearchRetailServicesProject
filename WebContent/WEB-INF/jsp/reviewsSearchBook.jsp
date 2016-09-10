<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap-custom.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/myStyles.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/search.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/font-awesome.css">

<script type="text/javascript" src="./presentationResources/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery-ui.js"></script>
<script type="text/javascript" src="./presentationResources/js/jsCustomScript.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery.jscroll.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Reviews</title>
<head>

<script>
	$(document).ready(function() {

	
var html = $(".bookRevList").html();
		 
	//  if(html != 'undefined' && html != null){
	//		
		//  $('.search-box').jscroll({		  
		//	loadingHtml: "<center><div class='ajax-loader-2'> </div></center>"     
		//  });
	//	  
		 // $('.search').jscroll();
	//	  
	//   }

	
	
		searchPageReadyInit();
		var acc = document.getElementsByClassName("accordion");
		var i;

		for (i = 0; i < acc.length; i++) {
			acc[i].onclick = function(){
				this.classList.toggle("active");
				this.nextElementSibling.classList.toggle("hide");
			}
		}
		
		   
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
<body background="./presentationResources/images/bgimg.jpg">





<button class="glyphicon glyphicon-search accordion responsive"> <span style="font-family:Arial;">Show or Hide Search Books </span></button>


	<div style="float">
<img width='300' height='300' src='./presentationResources/images/Medium_SecondPress.png' style='float:right; margin-right:420px; margin-top:60px;' /></div>

	<div class="search-box responsive">

		<c:if test="${not empty error}">
			<div class="error alert alert-error">${error}</div>
		</c:if>
		<c:if test="${not empty message}">
			<div class="message">${message}</div>
		</c:if>
		
		<p><span style="align-center; font-style:italic;">Please type in the criteria to find a book to review</span></p>


			<form:form id="reviewsForm"  commandName="bookReviewsModel">
	
	
				<div class="titleAndGenre responsive">
					Title:
					<input id="titleText"  type='text' name='titleText' style="width:40%;" ><span class="glyphicon glyphicon-book iconspan2"></span>
					
					
					<input id="genre" type="checkbox" name="genre" value="genre" onclick="renderTagList($(this));"/>Genre 
					<select style="visibility:hidden; width:30%; margin-left:1.7em; opaque:0.75;" id="genreSelect"></select>
				</div>		
				<div class="authorCategory responsive">
					Author:
					<input id="authorText" style="width:40%;" type='text' name='authorText' /><span class="glyphicon glyphicon-pencil iconspan2"></span>
					
					<input id="category" type="checkbox" name="category" value="category" onclick="renderTagList($(this));" />Category
					<select style="visibility:hidden; width:30%; margin-left:0.4em; opaque:0.75;" id="categorySelect"></select>
				</div>	
				
				<div class="publisherLang responsive">
					Publisher:
					<input id="publisherText" style="width:40%;" type='text' name='publisherText' /><span class="glyphicon glyphicon-barcode iconspan2"></span>
					
					<input id="language" type="checkbox" name="language" value="language" onclick="renderTagList($(this));" />Language
					<select style="visibility:hidden; width:30%; opaque:0.75;" id="languageSelect"></select>
		          </div>	
			
			  <div class="tagSearches responsive"> 
					
							
								<button id="searchBook" class="searchBook responsive" name="searchBook" type="button"  onclick="performAjaxSearch();" value="Search.." > 
								<span class="glyphicon glyphicon-eye-open" style="padding-right:0.5em;" ></span>Search...
								</button>
								<button id="resetSearch" class="resetSearch responsive" name="resetSearch" type="button" onclick="resetTheSearch();"  value="Reset" >
										<span class="glyphicon glyphicon-erase" style="padding-right:0.5em;" ></span> Reset...
								</button> 

					
					</div>		
			<br/>

			
		</form:form>
</div>	

<div id="resultsSection" class="resultsSection responsive">
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