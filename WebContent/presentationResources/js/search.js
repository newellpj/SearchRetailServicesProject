(function(angular) {

	var searchBookApp  = angular.module('searchBookPageApp', []);


		searchBookApp.controller('searchPageController', function($scope, $log) {
		 $log.info("11 title text from search page controller : "+$scope.titleText);
			 $scope.$watch('titleText', function(newVal, oldVal, scope) {
				 
				// $log.info("newVal : "+newVal);
				//  $log.info("we are here1 "+$scope.titleText);
				 if(newVal != undefined){
				 
					//$scope.authorText = "My Author : "+newVal;
				 }
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
				
				if(document.getElementById("bookRevList2") != null && document.getElementById("bookRevList2") != 'undefined'){
					
					document.getElementById("bookRevList2").innerHTML = "";
					
					 $( ".bookRevList2" ).each(function( ) { //these are the search result divs that get added upon pagination of search results
							this.innerHTML = "";
					  });
					
					
				}
				
			 
				$log.info("we are titleVal 323 : "+$scope.titleText);	
				
					var titleText = $scope.titleText;
					var publisherText = $scope.publisherText;
					var authorText = $scope.authorText;
					var genreText = $scope.genreText;
					var catText = $scope.catText;
					var langText = $scope.langText;
				
					$log.info("publisher text ::: "+publisherText);
				
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
							titleText: titleText,
							publisherText: publisherText,
							authorText: authorText,
							genreText: genreText,
							catText: catText,
							langText: langText
						}
					}).success(function(bookReviewsModelArray){
						
						$log.info("we are here : "+bookReviewsModelArray.length);	
						
						
						document.getElementById("search").style.display = "inline";
			
					
						for(var i = 0; i < bookReviewsModelArray.length ;i++){
							$log.info();
						  	$('.bookRevList').append("<div class='searchSegment'>");
							$('.bookRevList').append(formatBooksSearchContent(bookReviewsModelArray[i]));
							$('.bookRevList').append("</div>");
						}
						
					
						$(".search").append("<div class='next'><a href='retrieveNextSearchSegment'>"+""+"</a> </div>");
						
						$('.resultsSection').jscroll({		  
							loadingHtml: "<center><div class='ajax-loader-2'> </div></center>"     
						});
						
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
								
							})

			 }

		});
		
		
		//searchBookApp.controller('searchResultsController', function($scope, $controller, $log) {
		//	angular.extend(this, $controller('searchPageController', {$scope: $scope}));
		//	$log.info("22 title text from search results controller : "+$scope.titleText);
		//	 $scope.$watch('titleText', function(newVal, oldVal, scope) {
		//		 $log.info(newVal);
		//	 });
		//});
		
		
	
})(angular);

	
	function formatBooksSearchContent(searchData){
		 
		 var bookDetails =  searchData['booksList'];
		 
			if("No books found" != bookDetails){
							
					bookDetails = encodeURI(bookDetails);//bookDetails.replace(/ /g, "-");	
					
					//$('.bookRevList').append("&nbsp; <a style='font-style:italic !important;' href='reviewsReviewBook?titleAuthorText="+bookDetails+"'"+"> Review this");				
					//$('.bookRevList').append("</a>");
			}
		 
			var formattedMarkup = "<div style='float:left; margin-right:1.5em;' ><img width='"+searchData['imageWidth']+"' height='"+searchData['imageHeight']
			+"' src='"+searchData['thumbnailLocation']+"' /></div>"+
			"<span style='font-family:courier;'><b>Title : </b>"+searchData['titleText']+"<b> Author : </b> "+searchData['authorText']+" &nbsp; <b>Publisher: </b>"
			+searchData['publisherText']+"</span>"+
			" <p style='font-size:x-small;!important'>"+searchData['excerpt']+
			
			"&nbsp; <a style='font-size:x-small;!important; font-style:italic !important;' href='reviewsReviewBook?titleAuthorText="+bookDetails
			+"&imageHeight="+searchData['imageHeight']+"&imageWidth="+searchData['imageWidth']+"&thumbnailLocation="+searchData['thumbnailLocation']+"'> Review this </p>";

			return formattedMarkup; 
	 }
	
 