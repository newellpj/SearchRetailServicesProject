<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap-custom.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/myStyles.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/addBook.css">

<script type="text/javascript" src="./presentationResources/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery-ui.js"></script>
<script type="text/javascript" src="./presentationResources/js/jsCustomScript.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Reviews</title>
<head>

<script>

$(document).ready(function() {
	
	var inputs = document.querySelectorAll( '.inputfile' );
Array.prototype.forEach.call( inputs, function( input )
{
	var label	 = input.nextElementSibling,
		labelVal = label.innerHTML;

	input.addEventListener( 'change', function( e )
	{
		var fileName = '';
		if( this.files && this.files.length > 1 )
			fileName = ( this.getAttribute( 'data-multiple-caption' ) || '' ).replace( '{count}', this.files.length );
		else
			fileName = e.target.value.split( '\\' ).pop();

		if( fileName )
			$('.uploadLabel').html(fileName);

		else
			$('.uploadLabel').html("no file chosen");
	
	});
});
	
	
	if($("#titleTextAdd").val() == '' || $("#authorTextAdd").val() == ''){
			$( '#addBook').prop('disabled', true);
		}
	 
	  $('#authorTextAdd').keyup(function() {
	     if($("#titleTextAdd").val() != '' && $("#authorTextAdd").val() != '') {
	        $('#addBook').prop('disabled', false);
	     }else{
			$( '#addBook').prop('disabled', true);
		 }
	  });
	  
	  $('#titleTextAdd').keyup(function() {
	     if($("#titleTextAdd").val() != '' && $("#authorTextAdd").val() != '') {
	        $('#addBook').prop('disabled', false);
	     }else{
			$('#addBook').prop('disabled', true);
		 }
	  });
	  
	  $('#authorTextAdd').blur(function() {
	     if($("#titleTextAdd").val() != '' && $("#authorTextAdd").val() != '') {
	        $('#addBook').prop('disabled', false);
	     }else{
			$( '#addBook').prop('disabled', true);
		 }
	  });
	  
	  $('#titleTextAdd').blur(function() {
	     if($("#titleTextAdd").val() != '' && $("#authorTextAdd").val() != '') {
	        $('#addBook').prop('disabled', false);
	     }else{
			$('#addBook').prop('disabled', true);
		 }
	  });

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

<br/>
	<div id="add-book-box" class="add-book-box responsive">

		

		<c:if test="${not empty error}">
			<div class="error alert alert-error">${error}</div>
		</c:if>
		<c:if test="${not empty message}">
			<div class="message">${message}</div>
		</c:if>
		
		<p><span style="align-center;text-shadow: 0.5px 0.5px #a8a8a8;">Title and Author are mandatory</span></p>

<br/>
			<form:form id="reviewsForm" action="searchOrAddBook" method="post" commandName="bookReviewsModel">

		
			
				<div class="titleFields responsive">
					Title:
					<input id="titleTextAdd" class="responsive" type='text' name='titleTextAdd'><span class="glyphicon glyphicon-book iconspanAddBook" ></span> 
				</div>
				
				<div class="authorFields responsive">
					Author:
					<input  id="authorTextAdd" class="responsive" type='text' name='authorTextAdd' /><span class="glyphicon glyphicon-pencil iconspanAddBook"></span>
				</div>
				<div class="publisherFields responsive">
					Publisher:
					<input id="publisherTextAdd" class="responsive" type='text' name='publisherTextAdd' /><span class="glyphicon glyphicon-barcode iconspanAddBook"></span>
				</div>
				<div class="excerptFields responsive">
				   <div class="excerptLabel responsive" >Excerpt:</div>
					<div class="textAreaExcept responsive"><textarea style="resize: none; height:212px; margin-left:4em;" maxlength="2000" id="excerptText" name="excerptText" rows="10" cols="63" ></textarea>
					<span class="glyphicon glyphicon-comment iconspanReview responsive"></span></div>
				</div>
					<button id="addBook" name="addBook" class="responsive" type="button"
						value="Add a book.." onclick="performAjaxAddBook();" >
						<span class="glyphicon glyphicon-plus-sign"  ></span>&nbsp; Add a Book... </button>
						
				
				<span style="visibility:hidden;">placeholder</span>
	
				
		
			<br/><br/>
			  <fieldset><p>
                <span style="align-center;text-shadow: 0.5px 0.5px #a8a8a8;">Add attributes to enable better searching</span></p>
                   <div class="tagSearches"> 
						
							<div id="genreSelection1" class="genreSelection1 responsive">
								<input id="genre" type="checkbox" name="genre" value="genre" onclick="renderTagList($(this));"/>Genre 
								<select style="visibility:hidden;" id="genreSelect"></select>
							</div>
							<div class="categorySelection responsive">
								<input id="category" type="checkbox" name="category" value="category" onclick="renderTagList($(this));" />Category
								<select style="visibility:hidden;" id="categorySelect"></select>
							</div>
							<div class="languageSelection responsive">
								<input id="language" type="checkbox" name="language" value="language" onclick="renderTagList($(this));" />Language
								<select style="visibility:hidden;" id="languageSelect"></select>
							</div>
					</div>
					<br/>
					<div class="fileUploaderSection">	
							<p><span style="align-center;text-shadow: 0.5px 0.5px #a8a8a8;">Upload thumbnail image for the book</span></p>
								<input type="file" name="file" id="file" class="inputfile" />
								<label for="file"><img src="./presentationResources/images/button_select.gif" /></label>
								<label  class="uploadLabel">No file chosen</label>
							</div>
							</fieldset>
						<ul id="bookTagsList" class="bookTagsList">
						</ul>

		</form:form>
	</div>
<div class="facebookFooter" >
	<div id="fb-root" ></div>
<div class="fb-like" data-href="http://www.w3schools.com/" data-layout="standard" data-action="like" data-size="small" data-show-faces="true" data-share="true"></div>
</div>
</body>
</html>