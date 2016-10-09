
// Perform install steps
	//Open a cache.
	//Cache our files.
	//Confirm whether all the required assets are cached or not.
	
	var CACHE_NAME = 'my-site-cache-v1';
	//not sure we need to cache all these or only those on login page so remove if not necessary after testing
	var urlsToCache = [
	  '/',
	  './presentationResources/css/bootstrap.css',
	  './presentationResources/css/bootstrap.min.css',
	  './presentationResources/css/bootstrap-custom.css',
	  './presentationResources/css/font-awesome.css',
	  './presentationResources/css/myStyles.css',
	  './presentationResources/css/login.css',
	  './presentationResources/js/jquery-1.9.1.js',
	  './presentationResources/js/jquery-ui.js',
	  './presentationResources/js/jsCustomScript.js',
	  './presentationResources/js/angular.js',
	];


self.addEventListener('install', function(event) {
  
	 event.waitUntil(
	    caches.open(CACHE_NAME)
	      .then(function(cache) {
	        console.log('Opened cache');
	        alert('opened cache');
	        return cache.addAll(urlsToCache);
	      })
	);
});

self.addEventListener('fetch', function(event) {  //if event request matches 'install'
	  event.respondWith(
	    caches.match(event.request)
	      .then(function(response) {
	        // Cache hit - return response
	        if (response) {
	          return response;
	        }
	        return fetch(event.request);
	      }
	    )
	  );
	});


self.addEventListener('activate', function(event) {
	  console.log('Activated', event);
	  alert('Activated');
});


self.addEventListener('push', function(event) {
	  console.log('Push message received', event);
	  alert('push message received');
	  
	  
	  if ('serviceWorker' in navigator) { 
		  console.log('Service Worker is supported'); 
		  
		  navigator.serviceWorker.register('./presentationResources/js/sw.js').then(function() { 
			  
			  return navigator.serviceWorker.ready;
			  
		  }).then(function(reg) { 
			  console.log('Service Worker is ready :)', reg); 
			  	alert('Service Worker ready : '+reg);
			  reg.pushManager.subscribe({userVisibleOnly: true
			  
			  }).then(function(sub) { 
				  console.log('endpoint:', sub.endpoint); 
				  alert('endpoint');
			}); 
		}).catch(function(error) { 
				console.log('Service Worker error :(', error); 
				alert('Service Worker error : '+error);
			}); 
		  }
	  
	  
});





