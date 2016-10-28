//remember to always use ng-init to initialise html input elements to empty string '' so you can test
//for empty string only to enable or disable a  button or other component 

(function(angular) {

	var reviewsBookPageApp  = angular.module('reviewsBookPageApp', []);


		reviewsBookPageApp.controller('reviewPageController', function($scope, $log) {
		 $log.info("11 title text from search page controller : "+$scope.titleText);
			 $scope.$watch('starRating', function(newVal, oldVal, scope) {
				 
				 $log.info("newVal : "+newVal);
				  $log.info("we are here1 "+$scope.titleText);
				 if(newVal != undefined){
				 
					//$scope.authorText = "My Author : "+newVal;
				 }
			});
			
			$scope.$watch('starRating', function(newVal, oldVal, scope) {
				 
				 $log.info("newVal : "+newVal);
				//  $log.info("we are here1 "+$scope.titleText);
				 if(newVal != undefined){
				 
					//$scope.authorText = "My Author : "+newVal;
				 }
			});


		});
	 
	 

	 
	//searchBookApp.factory('titleVal', function(){
	  //return { titleText: '' };
	//});
	  
	 
		reviewsBookPageApp.controller('bookReviewSubmitter', function($scope, $http, $log) {
		 
			 $scope.addBookReview = function () {
			 
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
				
				
				var starRating =    //$("input[name='rating']:checked").val();
				
				$log.info("star rating found "+$scope.starRating);
				
				$http({
					url: 'addBookReview',
					dataType: 'JSON',
					method : 'GET',
					headers: {'Content-Type' : 'application/json'},
					params: { 
							titleText: $('#bookTitleReview').val(),
							authorText: $('#bookAuthorReview').val(), 
							reviewText: $scope.reviewText,
							starRating: $scope.starRating
						}
					}).success(function(bookReviewsModelArray){
					
						$log.info("bookReviewsModelArray text : "+bookReviewsModelArray);
					
						if( bookReviewsModelArray.indexOf("html") > -1 && bookReviewsModelArray.indexOf("body") >  -1){
							$(dlg).dialog("close");
							window.parent.location.href = 'logout'; 
						}else{
						
							$log.info("success");
							$(dlg).dialog("close");
							window.location.href = 'reviewsReviewBook';
						}
						
					 }).error(function(e){
						
						
						$log.info("error");
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
				 })

			 }

		});
		
		
	
})(angular);

	
	
 