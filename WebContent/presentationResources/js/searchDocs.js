//remember to always use ng-init to initialise html input elements to empty string '' so you can test
//for empty string only to enable or disable a  button or other component 

(function(angular) {

	var searchDocsApp  = angular.module('searchDocsApp', []);



		searchDocsApp.controller('searchDocsController', function($scope, $log, $http, $timeout) {
		 $log.info("11 title text from search page controller : "+$scope.titleText);
			 
			 
			 $scope.$watch('titleText', function(newVal, oldVal, scope) {
				 
				// $log.info("newVal : "+newVal);
				//  $log.info("we are here1 "+$scope.titleText);
				 if(newVal != undefined){
				 
					//$scope.authorText = "My Author : "+newVal;
				 }
				 
				   
				 
			});
			
		 
			 $scope.performDocSearch = function () {
			 
				var html = document.getElementById("bookRevList").html;
				var innerHTML = document.getElementById("bookRevList").innerHTML;
				
				document.getElementById("resultsSectionDocs").style.visibility = "visible";	
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
				
			 
				
				
				$http({
						url : 'searchForDocs',
						method : 'GET',
						headers: {'Content-Type' : 'application/json'},
						dataType: "JSON",
						params: { 
							titleText: titleTextVal,
							authorText: authorTextVal, 
							keywordText: keywordTextVal

						}
					}).success(function(returnList){
						
						document.getElementById("search").style.display = "inline";

						if( returnList.indexOf("html") > -1 && returnList.indexOf("body") >  -1){
							$(dlg).dialog("close");
							window.parent.location.href = 'logout'; 
						}else{
						
							if(returnList.length == 0){
								$('.bookRevList').append("<div style='font-style: italic; font-size:15px;'> No documents found </div>");	
									
							}else{
							
								for(var i = 0; i < returnList.length ;i++){
	
									
									$('.bookRevList').append("<div class='docsSearchSegment' id='"+"item"+i+"' >");	
									$('.bookRevList').append("</div>");
									
									var ID = '#item'+i;
									
									//$log.info("return  list !!@!@ "+returnList[i]['largercontent']);
									
									$(ID).html(formatDocSearchContent(returnList[i], $log));	
									//toggleReadMoreSearchResults(ID);
								}
								
								$(".search").append("<div class='next'><a href='retrieveNextSearchDocsSegment'>"+""+"</a> </div>");
							
								$('.resultsSectionDocs').jscroll({		  
									loadingHtml: "<center><div class='ajax-loader-2'> </div></center>"     
								});
								
								$('a').click( function(e) {
									e.preventDefault();
								});
								
							}
							
							$(dlg).dialog("close");
						}
					}).error(function(data, status){
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
								window.parent.location.href = 'logout'; 
							},
							text: 'OK'
						}
					
					],	
						title: 'Could NOT find book!',
						width: ( 300 )
					});
					
					
					
					var msg = "There was an error retrieving book : "+status

					$(errorDialog).html('<p>'+msg+'</p>');
					
			        $('.ui-dialog-buttonset').css("backgroundImage", "url('')");
			        $('.ui-dialog-buttonset').css("backgroundColor", "#c3c3c3");
					
					 $(errorDialog).dialog("open");
					 
				})

			 }
			 
			 
			 			
			$scope.testValue = function(searchType, tmpStr){
				
				if(searchType === 'title'){
					if (tmpStr === $scope.titleText && $scope.lastSelectedTitleItem != $scope.titleText){
						return true;
					}
				}else if(searchType === 'author'){
					if (tmpStr === $scope.authorText && $scope.lastSelectedAuthorItem != $scope.authorText){
						return true;
					}
				}
				
				return false;
				
			}
			
			$scope.mouseLeave = function(objToHide){
				console.log('lost focus');
				$("."+objToHide).css('display', 'none');
			}
			
			$scope.performInstantSearch = function(tmpStr, objClass, searchType){ 
				
				if (!tmpStr || tmpStr.length == 0) {
                     console.log("within the null empty text "+tmpStr);
					 $scope.data = "";
					 $(objClass).css("display", "none");
					 return 0;
				}
			
				
				$timeout(function() {

					// if searchStr is still the same..
					// go ahead and retrieve the data
						if ($scope.testValue(searchType, tmpStr)){
							
							console.log("within the title text ddd "+tmpStr);
							
							if(searchType == 'title'){
								searchType = 'id';
							}
							
							$http({
								url : 'partialSearchForDocs',
								method : 'GET',
								headers: {'Content-Type' : 'application/json'},
								dataType: "JSON",
								params: { 
									partialSearch: searchType+'-'+tmpStr
								}
							}).success(function(data){
								//$scope.responseData = data; 
								console.log(data);
							   $scope.data = data;
							   
							   if(data.length == 0){
								   $(objClass).css("display", "none");
							   }else{
								   $(objClass).css("display", "table");
							   }
							   
							    
							   
							}).error(function(data, status){
								
								console.log('error retrieving data');
							})
						}
					}, 250);
			}
			
			
			$scope.displayAuthors = function(data){
				console.log('hello there : '+data);
				$scope.authorText = data;
				$scope.data = "";
				$scope.lastSelectedAuthorItem = data;
				 $('.authorSearchPossibles').css("display", "none");
				 
			}
					
			$scope.displayTitles = function(data){
				console.log('titles hello there : '+data);
				$scope.titleText = data;
				$scope.data = "";
				$scope.lastSelectedTitleItem = data;
				 $('.titleSearchPossibles').css("display", "none");
				 
			}
			
			$scope.$watch('titleText', function (tmpStr){
			
				  //close any other instant search list overlay displaying and reset scope data to empty string
				  $('.authorSearchPossibles').css("display", "none");
				  $scope.data = "";		  
				 $scope.performInstantSearch(tmpStr, '.titleSearchPossibles', 'title');
			});
			
			$scope.$watch('authorText', function (tmpStr){
				  console.log("tmpStr : "+tmpStr);	
				  //close any other instant search list overlay displaying and reset scope data to empty string	
				  $('.titleSearchPossibles').css("display", "none");
				  $scope.data = "";
				 $scope.performInstantSearch(tmpStr, '.authorSearchPossibles', 'author');
			});
			 
			 

			});
	 
		
	
})(angular);


	 function displayFullContent(){
		
		 var fullTextDiv = $(".fullContent");	
		 
		 var dlg = $("<div class='dialogStyles responsive'></div>").dialog({
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
			
		//	$(fullTextDiv).css('display', 'inline-block');
			
			$(dlg).html("<div class='dialogStyles'  style='padding:10px; text-shadow:none!important;'>"+$(fullTextDiv).html()+"<div>");			
			$(dlg).dialog("open");
			
	 }
	
	 function formatDocSearchContent(searchData, $log){

		var formattedMarkup =  "<div style='float:left; margin-right:1.5em;' ><img alt='book thumb' src='"+searchData['thumbnailLocation']+"' /></div>"+
		"<b>Title : </b>"+searchData['title']+"<b> Author : </b> "+searchData['author']+" &nbsp; <b> link to doc </b> <a href='file://///"+searchData['id']+"'"+
		" target="+"'"+"_blank"+"'"+">"+searchData['title']+"</a><p style='font-size:x-small;!important'>"+searchData['extract']+
		"<i> <a href='#' onclick='displayFullContent();' > ...see more</a></i></p><div class='fullContent' style='color:white; display:none'>"+searchData['largercontent']+"</div>";
	 
	 
		return formattedMarkup;
	}
	
 