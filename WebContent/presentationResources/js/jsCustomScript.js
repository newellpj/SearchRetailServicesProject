
var searchAllMap = {};

function jscroller(){
 $('.infinite-scroll').jscroll({
     loadingHtml: "<div class='ajax-loader-2 help-inline pull-right'> Loading...'</div>",
     padding: 20,
     nextSelector: 'a.jscroll-next:last',
     contentSelector: 'li'
 });
}

function checkAllFields(){
	if($("#authorText").val() == '' && $("#titleText").val() == '' && $("#publisherText").val() == '' &&
		  ($("#genreSelect").val() == '' || $("#genreSelect").val() == null) && 
		  ($("#categorySelect").val() == '' || $("#categorySelect").val() == null) && 
		   ($("#languageSelect").val() == '' || $("#languageSelect").val() == null)){
			   $( '#searchBook').prop('disabled', true);
		   }
}


function searchDocsPageInit(){
	
	$(function(){ // DOM ready

		  // ::: TAGS BOX

		  $("#tags input").on({
		    focusout : function() {
		      var txt = this.value.replace(/[^a-z0-9\+\-\.\#]/ig,''); // allowed characters
		      if(txt) $("<span/>", {text:txt.toLowerCase(), insertBefore:this});
		      this.value = "";
		    },
		    keyup : function(ev) {
		      // if: comma|enter (delimit more keyCodes with | pipe)
		      if(/(188|13)/.test(ev.which)) $(this).focusout(); 
		    }
		  });
		  $('#tags').on('click', 'span', function() {
		     $(this).remove(); 
		  });

		});
	
	
	  if($("#authorText").val() == '' && $("#titleText").val() == '' && $("#keywordsText").val() == '' ){
				$( '#searchBook').prop('disabled', true);
		}
			

		     $('#authorText').keyup(function() {
				 
		        if($("#authorText").val() != '' ) {
		           $('#searchBook').prop('disabled', false);
		        }else{
					checkAllFields();
				 }
		     });
		
			  $('#titleText').keyup(function() {
			         if($("#titleText").val() != '') {
			            $('#searchBook').prop('disabled', false);
			         }else{
						checkAllFields();
					 }
			   });
			   
			   $('#titleText').blur(function() {
			         if($("#titleText").val() != '' ) {
			            $('#searchBook').prop('disabled', false);
			         }else{
						checkAllFields();
					 }
			   });
			   
			    $('#authorText').blur(function() {
			         if($("#authorText").val() != '' ) {
			            $('#searchBook').prop('disabled', false);
			         }else{
						 checkAllFields();
					 }
			   });
			   
			    $('#keywordsText').keyup(function() {
			         if($("#keywordsText").val() != '') {
			            $('#searchBook').prop('disabled', false);
			         }else{
						 checkAllFields();
					 }
			   });
			   
			    $('#keywordsText').blur(function() {
			         if($("#keywordsText").val() != '') {
			            $('#searchBook').prop('disabled', false);
			         }else{
						checkAllFields();
					 }
			   });
	
	
}


function searchPageReadyInit(){
	   
	   
	   
	   if($("#authorText").val() == '' && $("#titleText").val() == '' && $("#publisherText").val() == '' &&
		  ($("#genreSelect").val() == '' || $("#genreSelect").val() == null) && 
		  ($("#categorySelect").val() == '' || $("#categorySelect").val() == null) && 
		   ($("#languageSelect").val() == '' || $("#languageSelect").val() == null)){
			$( '#searchBook').prop('disabled', true);
		}
		
		$('#genreSelect').change(function() {
			 
	         if($("#genreSelect").val() != '' && $("#genreSelect").val() != null) {
	           $('#searchBook').prop('disabled', false);
	        }else{
				checkAllFields();
			 }
	     });
		 
		 $('#genre').change(function() {
			 
	        if($("#genreSelect").val() != '' && $("#genreSelect").val() != null && document.getElementById("genre").checked) {
	           $('#searchBook').prop('disabled', false);
	        }else{
				 checkAllFields();
			 }
	     });
		 
		 
		 $('#categorySelect').change(function() {
			 
	         if($("#categorySelect").val() != '' && $("#categorySelect").val() != null ) {
	           $('#searchBook').prop('disabled', false);
	        }else{
				 checkAllFields();
			 }
	     });
		 
		 $('#category').change(function() {
			 
	         if($("#categorySelect").val() != '' && $("#categorySelect").val() != null && document.getElementById("category").checked) {
	           $('#searchBook').prop('disabled', false);
	        }else{
				 checkAllFields();
			 }
	     });
		 
		 
		 $('#languageSelect').change(function() {
			
	        if($("#languageSelect").val() != '' && $("#languageSelect").val() != null) {
	           $('#searchBook').prop('disabled', false);
	        }else{
				 checkAllFields();
			 }
	     });
		 
		 $('#language').change(function() {
			 
	        if($("#languageSelect").val() != '' && $("#languageSelect").val() != null && document.getElementById("language").checked) {
	           $('#searchBook').prop('disabled', false);
	        }else{
				checkAllFields();
			 }
	     });
		
	    
	     $('#authorText').keyup(function() {
			 
	        if($("#authorText").val() != '' ) {
	           $('#searchBook').prop('disabled', false);
	        }else{
				checkAllFields();
			 }
	     });
	
		  $('#titleText').keyup(function() {
		         if($("#titleText").val() != '') {
		            $('#searchBook').prop('disabled', false);
		         }else{
					checkAllFields();
				 }
		   });
		   
		   $('#titleText').blur(function() {
		         if($("#titleText").val() != '' ) {
		            $('#searchBook').prop('disabled', false);
		         }else{
					checkAllFields();
				 }
		   });
		   
		    $('#authorText').blur(function() {
		         if($("#authorText").val() != '' ) {
		            $('#searchBook').prop('disabled', false);
		         }else{
					 checkAllFields();
				 }
		   });
		   
		    $('#publisherText').keyup(function() {
		         if($("#publisherText").val() != '') {
		            $('#searchBook').prop('disabled', false);
		         }else{
					 checkAllFields();
				 }
		   });
		   
		    $('#publisherText').blur(function() {
		         if($("#publisherText").val() != '') {
		            $('#searchBook').prop('disabled', false);
		         }else{
					checkAllFields();
				 }
		   });
		   
}


function noBookToReview(){
	
	var errorDialog = $("<div ></div>").dialog({
				hide: 'fade',
				maxWidth: 300,
				modal: true,
				show: 'fade',
				open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
					buttons: [
				{
					'class': 'btn btn-primary',
					click: function(e) {
						$(this).dialog("close");
						
					
					},
					text: 'OK'
				}
			
			],	
				title: 'Please search or add book ',
				width: ( 300 )
			});
		
		var msg = "You need to search or add a book to review";


		
		$(errorDialog).html('<p>'+msg+'</p>');
        $('.ui-dialog-buttonset').css("backgroundImage", "url('')");
        $('.ui-dialog-buttonset').css("backgroundColor", "#c3c3c3");
		
		 $(errorDialog).dialog("open");

}


	function switchActive(obj){
		var id = $(obj).attr("id");	
		document.getElementById(id).style.backgroundColor="#f6f6f6";
	    document.getElementById(id).style.backgroundImage="url(./presentationResources/images/arrow-up.gif)";
        document.getElementById(id).style.backgroundRepeat="no-repeat";
		document.getElementById(id).style.backgroundPosition="center bottom";		 

		
		
        $('#myMenu ul li').each(function(){
			var idFound = $(this).attr("id");
			
			if(idFound != id){
				document.getElementById(idFound).style.backgroundColor="#e9e9e9";
				document.getElementById(idFound).style.backgroundImage="";
				
			}  
        });
    }

	function LastSlide(paginateForward) {
		//fetch more objects
		
		getRSSFeed(true, paginateForward);
	}
	
	function getRSSFeed(paginating, paginateForward){
		
		var rssFeedURL = $('#searchAllSelect').val();
		var rssFeedName = $('#searchAllSelect option:selected').text();
		var searchText = $('#searchText').val();
		
			$('#all-search-box').find('input, textarea, button, select').attr("disabled", true);
			$('.bx-controls-direction').css("visibility", "hidden");
			$('.bx-pager').css("visibility", "hidden");
			
	//	$('#searchText').val();	
		
		if("All" == rssFeedName){ //append all feed urls excl image of day
			rssFeedURL = "";
				
			$('#searchAllSelect option').each(function(){
				var textVal = this.text;
					if(textVal != "Image of Day" && textVal != "All"){
						
						if("" == rssFeedURL){
							rssFeedURL = this.value;
						}else{
							rssFeedURL =  rssFeedURL+","+this.value;
						}
					}
			});
		}
		
		
		
		var dlg = $("<div></div>").dialog({
				hide: 'fade',
				maxWidth: 500,
				modal: true,
				show: 'fade',
				height: 200,
				title: 'Retrieving feeds',
				width: ( ('__proto__' in {}) ? '600' : 600 )
			});

			$(dlg).parent().find('button').remove();
			$(dlg).html("<div class='ajax-loader-2 help-inline pull-right'></div><div><p>Finding "+rssFeedName+" feeds... </p></div>");	
			$(dlg).dialog( "option", "modal", true );
			
			
			$(dlg).dialog("open");
			
			var requestURL = (paginating) ? 'getPaginatedFeed': 'getFeeds';
			
			$.ajax({
				url: requestURL,
				dataType: 'JSON',
				data: { 
					rssFeedURL: rssFeedURL,
					rssFeedName: rssFeedName,
					_paginateForward: paginateForward,
					searchCriteria: searchText
				},
				processData: true,
				contentType: 'application/json; charset=utf-8',
				type: 'GET',
				success:  function(feedMessageArr) {
					
				    $(dlg).dialog("close");
					
					$('#all-search-box').find('input, textarea, button, select').attr("disabled", false);
					$('.bx-controls-direction').css("visibility", "visible");
					$('.bx-pager').css("visibility", "visible");
					
					$( "#feedsSliderSegment" ).html(""); //these are the search result divs that get added upon pagination of search results	
					$( "#feedsSliderSegment" ).append("<ul id='feedsSlider' class='bxslider2'>");
	
					var totalFeedsSize = feedMessageArr[0]['totalFeedCount'];
					var currentPaginationOffset = feedMessageArr[0]['currentPaginationOffset'];	  
					
					for(var i = 0; (feedMessageArr.length) > i; i++){
						$("#feedsSliderSegment ul").append("<li><p>"+
						"<img height='"+feedMessageArr[i]['imageHeight']+"' width='"+feedMessageArr[i]['imageWidth']+"' src='"
						+feedMessageArr[i]['url']+"'></img><p><b>"+feedMessageArr[i]['title']+"</b></p>"+
						feedMessageArr[i]['description']+"</p><p><a href="+feedMessageArr[i]['link']+" target='_blank' >See More...</a>"+"</p></li>");
					}
					
					$( "#feedsSliderSegment" ).append("</ul>");
					
					document.getElementById('feedsSliderSegment').style.visibility = "visible";
					
					var startSlideVal = 0;	
						
					if(!paginateForward){
						startSlideVal = 9;
					}	
					
					
					var slider = $('.bxslider2').bxSlider({
						captions: true,
						auto:false,
						startSlide:startSlideVal
					});
					
					

					if(feedMessageArr.length >= 10 && totalFeedsSize > 10 && totalFeedsSize > currentPaginationOffset){ //only paginating function if this set is equal to 10 and there is more to paginate

						$('#feedsSliderSegment .bx-next').click(function () {	
						
							//alert($('#feedsSliderSegment').closest('.bx-next').html());
							
							//alert('gotten in here');							
							var current = slider.getCurrentSlide() + 1;
							if(current == 1){
								LastSlide(true);
							}else{
								slider.goToNextSlide();
							}
						});
						
					}
					
					if(totalFeedsSize > 10 && currentPaginationOffset >= 20){ //only paginating function if this set is equal to 10 and there is more to paginate
					//	alert('gotten in here previous');
						$('#feedsSliderSegment .bx-prev').click(function () {						
							var current = slider.getCurrentSlide() + 1;
							if(current == 10){
								LastSlide(false);
							}else{
								slider.goToPrevSlide();
							}
						});
					}
					
					//}
					//alert("The feed message array : "+feedMessageArr[0]['title']);
				 },

			 error: function(e){

		
					$(dlg).dialog("close");

					var errorDialog = $("<div></div>").dialog({
							hide: 'fade',
							maxWidth: 300,
							modal: true,
							show: 'fade',
							open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
								buttons: [
							{
								'class': 'btn btn-primary',
								click: function(e) {
									
								
									
									$(this).dialog("close");
									
								
								},
								text: 'OK'
							}
						
						],	
							title: 'Could NOT add book review!',
							width: ( 300 )
						});

						alert('we in error?');
						
						var msg = e.errorMessage;
						
						if('undefined' == msg || msg == null){
								msg = "There was an error retrieving the feed";
						}
						$('#mask').hide();
						$(errorDialog).html('<p>'+msg+'</p>');
				        $('.ui-dialog-buttonset').css("backgroundImage", "url('')");
				        $('.ui-dialog-buttonset').css("backgroundColor", "#c3c3c3");
						 $(errorDialog).dialog("open");
				         window.parent.location.href = 'logout'; 
			 }
			});  
	}
	
	function getSearchAllSelectOptionsToPixelsMapping(selectedText){
		return searchAllMap[selectedText];
	}
	
	function populateSearchAllOptions(){
		
		myOptions = [{ text: 'All', value: 'All'}, 
							 {text: 'Image of Day', value: 'https://www.nasa.gov/rss/dyn/lg_image_of_the_day.rss'}, 
							 {text : 'Education', value: 'https://www.nasa.gov/rss/dyn/educationnews.rss'},
							 {text : 'Breaking News', value: 'https://www.nasa.gov/rss/dyn/breaking_news.rss'}, 
							 {text : 'Earth', value: 'https://www.nasa.gov/rss/dyn/earth.rss'},
							 {text : 'Solar System', value: 'https://www.nasa.gov/rss/dyn/solar_system.rss'},
							 {text : 'Shuttle Station', value: 'https://www.nasa.gov/rss/dyn/shuttle_station.rss'},
							 {text : 'Aeronautics', value: 'https://www.nasa.gov/rss/dyn/aeronautics.rss'},
							 {text : 'Hurricane Update', value: 'https://www.nasa.gov/rss/dyn/hurricaneupdate.rss'},
							 {text : 'Keppler Mission', value: 'https://www.nasa.gov/rss/dyn/mission_pages/kepler/news/kepler-newsandfeatures-RSS.rss'},
							 {text : 'Chandra Mission', value: 'http://www.nasa.gov/rss/dyn/chandra_images.rss'},
							 {text : 'Goddard Space Center', value: 'https://www.nasa.gov/rss/dyn/goddard-NewsFeature.rss'}];	
							 
		
		$.each(myOptions, function(i, el) { 
		      
			   $('#searchAllSelect').append( new Option(el.text, el.value));
		});	
		
		searchAllMap['All'] = '40px';
		searchAllMap['Image of Day'] = '110px';
		searchAllMap['Education'] = '90px';
		searchAllMap['Breaking News'] = '120px';
		searchAllMap['Earth'] = '60px';
		searchAllMap['Solar System'] = '112px';
		searchAllMap['Shuttle Station'] = '120px';
		searchAllMap['Aeronautics'] = '100px';
	    searchAllMap['Hurricane Update'] = '140px';
		searchAllMap['Keppler Mission'] = '130px';
		searchAllMap['Chandra Mission'] = '130px';
		searchAllMap['Goddard Space Center'] = '175px';
	}
	
function renderTagList(obj){
	
	var ID = $(obj).attr('id');

	if(document.getElementById(ID).checked){
	
		var myOptions = ""
	
		if(ID == 'genre'){
			myOptions = [{ text: 'Please select..', value: ''}, { text: 'Thriller', value: 'Thriller'}, {text : 'Crime', value: 'Crime'},
							 {text : 'Biography', value: 'Biography'}, {text : 'Philosophy', value: 'Philosophy'},
							 {text : 'Romance', value: 'Romance'}];		
		}else if(ID == 'category'){
			myOptions= [{ text: 'Please select..', value: ''}, { text: 'Fiction', value: 'Fiction'}, {text : 'Non-fiction', value: 'Non-fiction'}];	 
		}else{
			myOptions= [{ text: 'Please select..', value: ''}, { text: 'English', value: 'English'}, {text : 'French', value: 'French'},
			 {text : 'Mandarin', value: 'Mandarin'}, {text : 'Hindi', value: 'Hindi'}, {text : 'Latin', value: 'Latin'},  {text : 'Spanish', value: 'Spanish'}];
		}
		
		 $('#'+ID+'Select').html('');
		
		$.each(myOptions, function(i, el) { 
		      
			   $('#'+ID+'Select').append( new Option(el.text,el.value) );
		});			
			
		 document.getElementById(ID+'Select').style.visibility = 'visible';
		document.getElementById(ID+'Select').style.display = 'inline';
							 
	}else{
		document.getElementById(ID+'Select').selectedIndex = 0;
		document.getElementById(ID+'Select').style.display = 'none';
		document.getElementById(ID+'Select').style.visibility = 'hidden';
	}
	
	
	
}



  function performAjaxAddReview(){
	
	 
	 	 var dlg = $("<div></div>").dialog({
			hide: 'fade',
			maxWidth: 600,
			modal: true,
			show: 'fade',
			title: 'Submitting Review....',
			width: ( ('__proto__' in {}) ? '600' : 600 )
		});

		$(dlg).parent().find('button').remove();
		$(dlg).html("<div class='ajax-loader-2 help-inline pull-right'></div><div><p>Adding a book review </p></div>");	
		$(dlg).dialog("open");
		
		
		var authorTextVal = $('#bookAuthorReview').val();
		var titleTextVal = $('#bookTitleReview').val(); 
		var reviewTextVal = $('#reviewText').val();
		
		$.ajax({
			url: 'addBookReview',
			dataType: 'JSON',
			data: { 
				titleText: titleTextVal,
				authorText: authorTextVal, 
				reviewText: reviewTextVal
			},
			processData: true,
			contentType: 'application/json; charset=utf-8',
			type: 'GET',
			success:  function(bookReviewsModel) {
			    $(dlg).dialog("close");
				window.location.href = 'reviewsReviewBook';
				
			 },

		 error: function(e){

	
				$(dlg).dialog("close");

				var errorDialog = $("<div></div>").dialog({
						hide: 'fade',
						maxWidth: 300,
						modal: true,
						show: 'fade',
						open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
							buttons: [
						{
							'class': 'btn btn-primary',
							click: function(e) {
								$(this).dialog("close");
								
							
							},
							text: 'OK'
						}
					
					],	
						title: 'Could NOT add book review!',
						width: ( 300 )
					});

					
					
					var msg = e.errorMessage;
					
					if('undefined' == msg || msg == null){
							msg = "There was an error adding the book review";
					}
					
					$(errorDialog).html('<p>'+msg+'</p>');
			        $('.ui-dialog-buttonset').css("backgroundImage", "url('')");
			        $('.ui-dialog-buttonset').css("backgroundColor", "#c3c3c3");
					 $(errorDialog).dialog("open");
			         window.parent.location.href = 'logout'; 
		 }
		});  
		
  }
 
 function performAjaxAddBook(){
	
	 
	 	 var dlg = $("<div></div>").dialog({
			hide: 'fade',
			maxWidth: 600,
			modal: true,
			height: 200,
			show: 'fade',
			title: 'Submitting Book....',
			width: ( ('__proto__' in {}) ? '600' : 600 )
		});

		$(dlg).parent().find('button').remove();
		
		$(dlg).html("<div class='ajax-loader-2 help-inline pull-right'></div><div><p>Submitting book title and author </p></div>");
		$(dlg).dialog("open");
		
		var authorTextVal = $('#authorTextAdd').val();
		var titleTextVal = $('#titleTextAdd').val(); 
		var publisherTextVal = $('#publisherTextAdd').val();   
		var genreTextVal = $('#genreSelect').val();  
		var catTextVal = $('#categorySelect').val();
		var langTextVal = $('#languageSelect').val();
		
		$.ajax({
			url: 'addNewBook',
			dataType: 'JSON',
			data: { 
				titleText: titleTextVal,
				authorText: authorTextVal, 
				publisherText: publisherTextVal,
				genreText: genreTextVal,
				catText: catTextVal,
				langText: langTextVal
			},
			processData: true,
			contentType: 'application/json; charset=utf-8',
			type: 'GET',
			success:  function(bookReviewsModel) {
			    $(dlg).dialog("close");
			    $('#activeSel3', parent.document).click();
				window.location.href = 'reviewsReviewBook';
				
			 },

		 error: function(e){

	
				$(dlg).dialog("close");

				var errorDialog = $("<div></div>").dialog({
						hide: 'fade',
						maxWidth: 300,
						modal: true,
						show: 'fade',
						open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
							buttons: [
						{
							'class': 'btn btn-primary',
							click: function(e) {
								$(this).dialog("close");
							},
							text: 'OK'
						}
					
					],	
						title: 'Could NOT find book!',
						width: ( 300 )
					});

					
					
					var msg = e.errorMessage;
					
					if('undefined' == msg || msg == null){
							msg = "There was an error adding the book";
					}
					
					$(errorDialog).html('<p>'+msg+'</p>');
			        $('.ui-dialog-buttonset').css("backgroundImage", "url('')");
			        $('.ui-dialog-buttonset').css("backgroundColor", "#c3c3c3");
					 $(errorDialog).dialog("open");
			         window.parent.location.href = 'logout'; 
		 }
		});    
		
 }

 function resetDocSearch(){
	 window.location.href = 'reviewsSearchDocs';
	
	//resetSearches();
 }
 
 function resetTheSearch(){
	 window.location.href = 'reviewsSearchBook';
	
	//resetSearches();
 }
 function resetSearches(){
	 
	 $('.bookRevList').html("");
	 
	 document.getElementById("search").style.display = "none";
	  $("select").hide();
	//document.getElementById(ID+'Select').style.visibility = 'hidden';
	
	$('#reviewsForm').trigger("reset");
	 

 }
 
 function displayFullContent(){
	
	 var fullTextDiv = $(".fullContent");	
	 
	 var dlg = $("<div class='dialogStyles'></div>").dialog({
		   buttons : [{
				'class' : 'dialogButton',
				click : function(e) {
					$(this).dialog("close");
				},
				text : 'Close'
			} ],
			hide: 'fade',
			maxWidth: 600,
			maxHeight: 1000,
			autoOpen: false,
			modal: true,
			show: 'fade',
			title: 'Excerpt',
			width: '650',
			height: 'auto'	
		});

		$(".ui-dialog-titlebar").hide();
		
		var parent = $(dlg).parent();

		$(dlg).resizable();
		
		$(fullTextDiv).css('display', 'inline-block');
		
		$(dlg).html("<div class='dialogStyles'  style='padding:10px; text-shadow:none!important;'>"+$(fullTextDiv).html()+"<div>");			
		$(dlg).dialog("open");
 }
 
 function performAjaxDocSearch(){
	
	var html = document.getElementById("bookRevList").html;
	var innerHTML = document.getElementById("bookRevList").innerHTML;
	
	document.getElementById("resultsSection").style.visibility = "visible";	
	document.getElementById("bookRevList").innerHTML = ""; //this is the original search results div that gets displayed
	
	if(document.getElementById("bookRevList2") != null && document.getElementById("bookRevList2") != 'undefined'){
		
		document.getElementById("bookRevList2").innerHTML = "";
		
		 $( ".bookRevList2" ).each(function( ) { //these are the search result divs that get added upon pagination of search results
				this.innerHTML = "";
		  });

	}

	 
	 var dlg = $("<div></div>").dialog({
			hide: 'fade',
			maxWidth: 600,
			modal: true,
			show: 'fade',
			height: 200,
			title: 'Searching Docs....',
			width: ( ('__proto__' in {}) ? '600' : 600 )
		});

		$(dlg).parent().find('button').remove();
		
		$(dlg).html("<div class='ajax-loader-2 help-inline pull-right'></div><div><p>Searching documents...</p></div>");
			
		$(dlg).dialog("open");
		
		var authorTextVal = $('#authorText').val();
		var titleTextVal = $('#titleText').val();
		var keywordTextVal = '';
		
		count = 0;
		
		 $( "#tags span" ).each(function( ) { //these are the search result divs that get added upon pagination of search results		
				count = count + 1;
				if(count > 1 && keywordTextVal.length > 0){
					keywordTextVal = keywordTextVal + ",";
				}
				
				if($(this).html().length > 0){
					keywordTextVal = keywordTextVal + $(this).html();
				}
		  });
		
//		alert("keywordTextVal : "+keywordTextVal); 

//		alert("authorTextVal : "+authorTextVal);

		
		$.ajax({
			url: 'searchForDocs',
			dataType: "JSON",
			data: { 
				titleText: titleTextVal,
				authorText: authorTextVal, 
				keywordText: keywordTextVal
			},
			processData: true,
			contentType: 'application/json; charset=utf-8',
			type: 'GET',
			success:  function(returnList) {

				document.getElementById("search").style.display = "inline";
		
				
				for(var i = 0; i < returnList.length ;i++){
					
					$('.bookRevList').append("<div class='docsSearchSegment' id='"+"item"+i+"' >");	
					$('.bookRevList').append("</div>");
					
					var ID = '#item'+i;
					
					$(ID).html(returnList[i]);	
					//toggleReadMoreSearchResults(ID);
				}
				
				
				$(".search").append("<div class='next'><a href='retrieveNextSearchDocsSegment'>"+""+"</a> </div>");
				
				$('.resultsSection').jscroll({		  
					loadingHtml: "<center><div class='ajax-loader-2'> </div></center>"     
				});
				
				$(dlg).dialog("close");
				
				 
			 },

		 error: function(e){

	            
				$(dlg).dialog("close");

				var errorDialog = $("<div></div>").dialog({
						hide: 'fade',
						maxWidth: 300,
						modal: true,
						show: 'fade',
						open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
							buttons: [
						{
							'class': 'btn btn-primary',
							click: function(e) {
								$(this).dialog("close");
							},
							text: 'OK'
						}
					
					],	
						title: 'Could NOT find book!',
						width: ( 300 )
					});

					
					
					var msg = e.errorMessage;
					
					if('undefined' == msg || msg == null){
							msg = "There was an error retrieving book";
					}
					
					$(errorDialog).html('<p>'+msg+'</p>');
					
			        $('.ui-dialog-buttonset').css("backgroundImage", "url('')");
			        $('.ui-dialog-buttonset').css("backgroundColor", "#c3c3c3");
					
					 $(errorDialog).dialog("open");
					 
					 
					 
					window.parent.location.href = 'logout'; 
			 
		 }
		});    

 }
 
 function toggleReadMoreSearchResults(ID){
		$('.docsSearchSegment').find('a[href="#"]').on('click', function (e) {
		    alert('in find');
			e.preventDefault();
		    this.expand = !this.expand;
		    $(this).text(this.expand?"Click to collapse":"Click to read more");
		    $(this).closest(ID).find('.docsSearchSegment, .docsSearchSegmentBig').toggleClass('docsSearchSegment docsSearchSegmentBig');
		});
 }
 
 function performAjaxSearch(){
	// $('.searchResults').trigger("reset");
	
	var html = document.getElementById("bookRevList").html;
	var innerHTML = document.getElementById("bookRevList").innerHTML;
	
	document.getElementById("resultsSection").style.visibility = "visible";	
	document.getElementById("bookRevList").innerHTML = ""; //this is the original search results div that gets displayed
	
	if(document.getElementById("bookRevList2") != null && document.getElementById("bookRevList2") != 'undefined'){
		
		document.getElementById("bookRevList2").innerHTML = "";
		
		 $( ".bookRevList2" ).each(function( ) { //these are the search result divs that get added upon pagination of search results
				this.innerHTML = "";
		  });
		
		
	}
	
	//alert("html : "+html);
	//alert("innerHTML : "+innerHTML);
	 
	 var dlg = $("<div></div>").dialog({
			hide: 'fade',
			maxWidth: 600,
			modal: true,
			height: 200,
			show: 'fade',
			title: 'Searching Books....',
			width: ( ('__proto__' in {}) ? '600' : 600 )
		});

		$(dlg).parent().find('button').remove();
		
		$(dlg).html("<div class='ajax-loader-2 help-inline pull-right'></div><div><p>Searching books...</p></div>");
			
		$(dlg).dialog("open");
		
		var authorTextVal = $('#authorText').val();
		var titleTextVal = $('#titleText').val();
		var publisherTextVal = $('#publisherText').val();   
		var genreTextVal = $('#genreSelect').val();  
		var catTextVal = $('#categorySelect').val();
		var langTextVal = $('#languageSelect').val();
		
		//alert("catTextVal : "+catTextVal);
		//alert("langTextVal : "+langTextVal);
		
		$.ajax({
			url: 'searchForBook',
			dataType: "JSON",
			data: { 
				titleText: titleTextVal,
				authorText: authorTextVal, 
				publisherText: publisherTextVal,
				genreText: genreTextVal,
				catText: catTextVal,
				langText: langTextVal
			},
			processData: true,
			contentType: 'application/json; charset=utf-8',
			type: 'GET',
			success:  function(bookReviewsModel) {
			  
				//alert('bookReviewsModel reviewText : '+bookReviewsModel['reviewText']);
			//	alert('bookReviewsModel : '+JSON.stringify(bookReviewsModel, undefined, 2));
			     //$('#activeSel3', parent.document).click();
			    //$('#'+ID+'Select').append( new Option(el.text,el.value) );
			    
				document.getElementById("search").style.display = "inline";
		
				
				for(var i = 0; i < bookReviewsModel['booksList'].length ;i++){
					
					$('.bookRevList').append("<div>");
					$('.bookRevList').append(bookReviewsModel['booksList'][i]);
					var bookDetails = bookReviewsModel['booksList'][i]
					
					if("No books found" != bookDetails){
						
						bookDetails = encodeURI(bookDetails);//bookDetails.replace(/ /g, "-");	
						
						$('.bookRevList').append("&nbsp; <a style='font-style:italic !important;' href='reviewsReviewBook?titleAuthorText="+bookDetails+"'"+"> Review this");				
						$('.bookRevList').append("</a>");
					}
					
					$('.bookRevList').append("</div>");
				}
				
				
				$(".search").append("<div class='next'><a href='retrieveNextSearchSegment'>"+""+"</a> </div>");
				
				$('.resultsSection').jscroll({		  
					loadingHtml: "<center><div class='ajax-loader-2'> </div></center>"     
				});
				
				$(dlg).dialog("close");
				
				 
			 },

		 error: function(e){

	            
				$(dlg).dialog("close");

				var errorDialog = $("<div></div>").dialog({
						hide: 'fade',
						maxWidth: 300,
						modal: true,
						show: 'fade',
						open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
							buttons: [
						{
							'class': 'btn btn-primary',
							click: function(e) {
								$(this).dialog("close");
							},
							text: 'OK'
						}
					
					],	
						title: 'Could NOT find book!',
						width: ( 300 )
					});


					var msg = e.errorMessage;
					
					if('undefined' == msg || msg == null){
							msg = "There was an error retrieving book";
					}
					
					$(errorDialog).html('<p>'+msg+'</p>');
			        $('.ui-dialog-buttonset').css("backgroundImage", "url('')");
			        $('.ui-dialog-buttonset').css("backgroundColor", "#c3c3c3");
					
					 $(errorDialog).dialog("open");
					 
					window.parent.location.href = 'logout'; 
			 
		 }
		});    

 }