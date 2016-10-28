(function(angular) {

	var addBookApp  = angular.module('addBookApp', []);


		addBookApp.controller('addItemController', function($scope, $log, $http) {
		 $log.info("11 title text from search page controller : "+$scope.titleText);
			 
			 $scope.genreHide = true;
			 $scope.categoryHide = true;
			 $scope.languageHide = true;
			 
			 
			 
			 $scope.$watch('titleText', function(newVal, oldVal, scope) {
				 
				// $log.info("newVal : "+newVal);
				//  $log.info("we are here1 "+$scope.titleText);
				 if(newVal != undefined){
				 
					
				 }

			});
			
			$scope.$watch('authorText', function(newVal, oldVal, scope) {
				 
				// $log.info("newVal : "+newVal);
				//  $log.info("we are here1 "+$scope.titleText);
				 if(newVal != undefined){
				 
					//$scope.authorText = "My Author : "+newVal;
				 }

			});
			
			$scope.$watch('publisherText', function(newVal, oldVal, scope) {
				 
				// $log.info("newVal : "+newVal);
				//  $log.info("we are here1 "+$scope.titleText);
				 if(newVal != undefined){
				 
					//$scope.authorText = "My Author : "+newVal;
				 }

			});
			
			$scope.$watch('excerptText', function(newVal, oldVal, scope) {
				 
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
			
			
			
			$scope.addItem = function () {
			 
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
				
				var formdata = new FormData();
				formdata.append('bookAuthorFound', $scope.authorText);
				formdata.append('bookTitleFound', $scope.titleText);
				formdata.append('bookPublisherFound', $scope.publisherText);
				formdata.append('genreTextVal', $scope.genreSelect.selectedOption.value);
				formdata.append('catTextVal',  $scope.catSelect.selectedOption.value);
				formdata.append('langTextVal', $scope.langSelect.selectedOption.value);
				formdata.append('excerpt', $scope.excerptText);

				var file = document.getElementById('file').files[0];	
				var fileName = $(".uploadLabel").val();
				
				formdata.append('theFile', file);
				formdata.append('filename', fileName);

				var data = formdata;
	
				var config = {
					headers : {'Content-Type': undefined}
				}
				
				
				$http.post('addNewBook', data, config).success(function(bookReviewsModelArray){

						if( bookReviewsModelArray.indexOf("html") > -1 && bookReviewsModelArray.indexOf("body") >  -1){
							$(dlg).dialog("close");
							window.parent.location.href = 'logout'; 
						}else{
						
							$log.info("success for adding book");
							$(dlg).dialog("close");
							$('#activeSel3', parent.document).click();
							window.location.href = 'reviewsReviewBook';
						}	
						
					 }).error(function(error, status){
						
						
						$log.info("error for adding book "+error);
						$log.info("error status for adding book "+status);
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
									click: function(error) {
										$log.info("error coming back ");
										$(this).dialog("close");
										 //window.parent.location.href = 'logout'; 
									
									},
									text: 'OK'
								}
							
							],	
								title: 'Could NOT add book review!',
								width: ( 300 )
							});

							
							
							var msg = status;
							
							if('undefined' == msg || msg == null){
									msg = "There was an error adding the book review";
							}
							
							$(errorDialog).html('<p>'+msg+'</p>');
							$('.ui-dialog-buttonset').css("backgroundImage", "url('')");
							$('.ui-dialog-buttonset').css("backgroundColor", "#c3c3c3");
							 $(errorDialog).dialog("open");
							
				 })

			 }



		});
	 
	 

	 
	//searchBookApp.factory('titleVal', function(){
	  //return { titleText: '' };
	//});
	  
	 
		addBookApp.controller('attributesAndUploadCtrl', function($scope, $http, $log) {
		 


		});
		
		
	
})(angular);

	
	
 