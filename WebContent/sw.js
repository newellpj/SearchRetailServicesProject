
// Perform install steps
	//Open a cache.
	//Cache our files.
	//Confirm whether all the required assets are cached or not.
	
	var CACHE_NAME = 'my-site-cache-v1';
	//not sure we need to cache all these or only those on login page so remove if not necessary after testing
	var urlsToCache = [
	  '/',
	  '../css/bootstrap.css',
	  '../css/bootstrap.min.css',
	  '../css/bootstrap-custom.css',
	  '../css/font-awesome.css',
	  '../css/myStyles.css',
	  '../css/login.css',
	  './jquery-1.9.1.js',
	  './jquery-ui.js',
	  './jsCustomScript.js',
	  './angular.js',
	];


console.log('Started', self);

self.addEventListener('install', function(event) {
  self.skipWaiting();
  console.log('Installed', event);
});

self.addEventListener('activate', function(event) {
  console.log('Activated', event);
});

self.addEventListener('push', function(event) {
  console.log('Push message received', event);
  // TODO
});