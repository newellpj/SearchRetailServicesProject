(function(angular) {

	var searchBookApp  = angular.module('searchBookPageApp', []);


		searchBookApp.controller('searchPageController', function($scope, $log) {
		 $log.info("11 title text from search page controller : "+$scope.titleText);
			 
			 $scope.genreHide = true;
			 $scope.categoryHide = true;
			 $scope.languageHide = true;
			 
			 $scope.$watch('titleText', function(newVal, oldVal, scope) {
				 
				// $log.info("newVal : "+newVal);
				//  $log.info("we are here1 "+$scope.titleText);
				 if(newVal != undefined){
				 
					//$scope.authorText = "My Author : "+newVal;
				 }
				 
				   
				 
			});
			
			$scope.genreSelect = {
				model: null,
				availableOptions: [
					 {value: '', name: 'Please select..'},
					 {value: 'Drama', name: 'Drama'},
					 {value: 'Thriller', name: 'Thriller'},
					 {value: 'Crime', name: 'Crime'},
					 {value: 'Biography', name: 'Biography'},
					 {value: 'Philosophy', name: 'Philosophy'},
					 {value: 'Mystery', name: 'Mystery'},
					 {value: 'Non-fiction', name: 'Non-fiction'},
					 {value: 'Romance', name: 'Romance'},
					 {value: 'Sci-fi', name: 'Sci-fi'},
					 {value: 'Human Interest', name: 'Human Interest'}
				 ],
				 selectedOption: {value: '', name: 'Please select..'} 
			};
			
			$scope.catSelect = {
				model: null,
				availableOptions: [
					 {value: '', name: 'Please select..'},
					 {value: 'Fiction', name: 'Fiction'},
					 {value: 'Non-fiction', name: 'Non-fiction'}
				 ],
				 selectedOption: {value: '', name: 'Please select..'} 
			};
			
			$scope.langSelect = {
				availableOptions: [
					 {value: '', name: 'Please select..'},
					 {value: 'English', name: 'English'},
					 {value: 'French', name: 'French'},
					 {value: 'Mandarin', name: 'Mandarin'},
				     {value: 'Hindi', name: 'Hindi'}, 
					 {value: 'Latin', name: 'Latin'},
					 {value: 'Spanish', name: 'Spanish'}
				 ],
				 selectedOption: {value: '', name: 'Please select..'} 
			};
			
			
   
			
			$scope.$watch('catCheck', function(newVal, oldVal, scope) {
				$log.info("newVal : "+newVal);
				
					$scope.catSelect.selectedOption = $scope.catSelect.availableOptions[0];
				
					
			});
			
			$scope.$watch('langCheck', function(newVal, oldVal, scope) {
				$log.info(newVal);
				
					$scope.langSelect.selectedOption = $scope.langSelect.availableOptions[0];
				
			});
			
			$scope.$watch('genreCheck', function(newVal, oldVal, scope) {		
				$log.info(newVal);
				
					$scope.genreSelect.selectedOption = $scope.genreSelect.availableOptions[0];
				
			});


		});
	 
	 

	 
	//searchBookApp.factory('titleVal', function(){
	  //return { titleText: '' };
	//});
	  
	 
		searchBookApp.controller('searchSubmitter', function($scope, $http, $log) {
		 
			 $scope.performBookSearch = function () {
			 
				var html = document.getElementById("bookRevList").html;
				var innerHTML = document.getElementById("bookRevList").innerHTML;
				
				document.getElementById("resultsSection").style.visibility = "visible";	
				document.getElementById("bookRevList").innerHTML = ""; //this is the original search results div that gets displayed
				
				$log.info("inner html of  book rev list : "+document.getElementById("bookRevList").innerHTML);
				
				if(document.getElementById("bookRevList2") != null && document.getElementById("bookRevList2") != 'undefined'){
					
					document.getElementById("bookRevList2").innerHTML = "";
					
					 $( ".bookRevList2" ).each(function( ) { //these are the search result divs that get added upon pagination of search results
							this.innerHTML = "";
					  });
						
					$( ".searchSegment" ).remove();
					
				 					 
						
				}
				
				//as search segment can get placed outside the book list by the jscroll function we should 
				//remove all searchSegments - they will be re-added by javascript or the controllers dynamically 
				//anyway so no damage is done
				
				
				
			 
				$log.info("we are titleVal 323 : "+$scope.titleText);	
				
					var titleText = $scope.titleText;
					var publisherText = $scope.publisherText;
					var authorText = $scope.authorText;
					var genreSelect = $scope.genreSelect.selectedOption.value;
					var catSelect = $scope.catSelect.selectedOption.value;
					var langSelect = $scope.langSelect.selectedOption.value;
				
					$log.info("publisher text ::: "+publisherText);
					$log.info("langSelect text ::: "+langSelect);
					
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
				
				
				$http({
						url : 'searchForBook',
						method : 'GET',
						headers: {'Content-Type' : 'application/json'},
						dataType: "JSON",
						params: { 
							title: titleText,
							publisher: publisherText,
							author: authorText,
							genreSelect: genreSelect,
							catSelect: catSelect,
							langSelect: langSelect
						}
					}).success(function(bookReviewsModelArray){
						
						$log.info("we are here : "+bookReviewsModelArray.length);	
						
						
						document.getElementById("search").style.display = "inline";
			
					
					    $scope.formattedSearchData = '';
					
						var testFirstElement = bookReviewsModelArray[0]['booksList'];
					
						$log.info('testFirstElement : '+testFirstElement);
						$log.info('bookReviewsModelArray : '+bookReviewsModelArray.length);
					
						if("No Books Found!!" != testFirstElement){
							$log.info("we here again");
							for(var i = 0; i < bookReviewsModelArray.length; i++){
							
								//$log.info("first book in array : "+$('.bookRevList').html());
								
								//$('.bookRevList').append("<div class='searchSegment'>");
								
								var formattedContent = "<div class='searchSegment'>"+formatBooksSearchContent(bookReviewsModelArray[i], $log)+"</div>"
								
								$('.bookRevList').append(formattedContent);
							//	$('.bookRevList').append("</div>");
		
							}
							
							$(".search").append("<div class='next'><a href='retrieveNextSearchSegment'>"+""+"</a> </div>");
							
							$('.resultsSection').jscroll({		  
								loadingHtml: "<center><div class='ajax-loader-2'> </div></center>"     
							});
							
						}else{
							$('.bookRevList').append("<span style='text-shadow: 0.5px 0.5px #a8a8a8; '>No Books Found!! </span>");
						}
						
						$(dlg).dialog("close");
					
					}).error(function(data, status){
						$log.error("we errored here");
						
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
									
								create: function (event, ui) {
									//$(event.target).parent().css('position', 'fixed'); - this stops the error message jumping around after initial popup
								},									
								title: 'Could NOT find book!',
								width: ( 300 )
							});

							msg = "There was an error retrieving book";
						
							$(errorDialog).html('<p>'+msg+'</p>');
							$('.ui-dialog-buttonset').css("backgroundImage", "url('')");
							$('.ui-dialog-buttonset').css("backgroundColor", "#c3c3c3");
							
							
							 $(errorDialog).dialog("open");
								window.parent.location.href = 'logout'; 
							})

			 }

		});
		
		
	
})(angular);

	function showHideSearchBookSelect(obj){
		
		var ID = $(obj).attr('id');

		if(document.getElementById(ID).checked){
			document.getElementById(ID+'Select').style.visibility = 'visible';
			document.getElementById(ID+'Select').style.display = 'inline';
								 
		}else{
			document.getElementById(ID+'Select').selectedIndex = 0;
			document.getElementById(ID+'Select').style.display = 'none';
			document.getElementById(ID+'Select').style.visibility = 'hidden';
		}

	} 

	
	function formatBooksSearchContent(searchData, $log){
			$log.info("formatting");
			var bookDetails =  searchData['booksList'];
			var formattedMarkup = "";
			 
			$log.info("formatBooksSearchContent "+bookDetails); 
			 
			if("No books found" != bookDetails){
							
				bookDetails = encodeURI(bookDetails);//bookDetails.replace(/ /g, "-");	
				
				formattedMarkup = "<div style='float:left; margin-right:1.5em;' ><img width='"+searchData['imageWidth']+"' height='"+searchData['imageHeight']
				+"' src='"+searchData['thumbnailLocation']+"' /></div>"+
				"<span style='font-family:courier;'><b>Title : </b>"+searchData['titleText']+"<b> Author : </b> "+searchData['authorText']+" &nbsp; <b>Publisher: </b>"
				+searchData['publisherText']+"</span>"+
				" <p style='font-size:x-small;!important'>"+searchData['excerpt']+
				
				"&nbsp; <a style='font-size:x-small;!important; font-style:italic !important;' href='reviewsReviewBook?titleAuthorText="+bookDetails
				+"&imageHeight="+searchData['imageHeight']+"&imageWidth="+searchData['imageWidth']+"&thumbnailLocation="+searchData['thumbnailLocation']+"'> Review this </p>";
			}
			 
			return formattedMarkup; 
	 }
	
 