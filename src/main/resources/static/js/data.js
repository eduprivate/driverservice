var postApp = angular.module('map', []);
// Controller function and passing $http service and $scope var.
postApp.controller('mapController', function($scope, $http) {
    
	var latlon;
	
	$http({
      method  : 'GET',
      url     : 'http://localhost:8080/drivers/inArea?sw=-23.612474,-46.702746&ne=-23.589548,-46.673392',
     })
      .success(function(data) {
    	  $scope.drivers = data;
    	  if (navigator.geolocation) {
    	        navigator.geolocation.getCurrentPosition(showPosition,showError);
    	    } else { 
    	        x.innerHTML = "Geolocation is not supported by this browser.";
    	    }
    	  
      });
    
   
    function showPosition(position) {
        lat = position.coords.latitude;
        lon = position.coords.longitude;
        latlon = new google.maps.LatLng(lat, lon)
        mapholder = document.getElementById('mapholder')
        mapholder.style.height = '350px';
        mapholder.style.width = '680px';

        
        
        var myOptions = {
        center:latlon,zoom:11,
        mapTypeId:google.maps.MapTypeId.ROADMAP,
        mapTypeControl:false,
        navigationControlOptions:{style:google.maps.NavigationControlStyle.SMALL}
        }
        icon = "http://maps.google.com/mapfiles/ms/icons/blue.png";
        var map = new google.maps.Map(document.getElementById("mapholder"), myOptions);
        var marker = new google.maps.Marker({position:latlon,map:map,title:"You are here!", 
        	icon: new google.maps.MarkerImage(icon)});

  	  	for(i = 0; i < $scope.drivers.length; i++){
  		    lat = $scope.drivers[i].latitude;
  	        lon = $scope.drivers[i].longitude;
  	        latlon = new google.maps.LatLng(lat, lon)
  	        marker = new google.maps.Marker({
		          position: new google.maps.LatLng(lat, lon),
		          map: map,title:"TAXI!"
		    });
 
  	  }
    }
    
    function showError(error) {
    switch(error.code) {
        case error.PERMISSION_DENIED:
            x.innerHTML = "User denied the request for Geolocation."
            break;
        case error.POSITION_UNAVAILABLE:
            x.innerHTML = "Location information is unavailable."
            break;
        case error.TIMEOUT:
            x.innerHTML = "The request to get user location timed out."
            break;
        case error.UNKNOWN_ERROR:
            x.innerHTML = "An unknown error occurred."
            break;
    }
}
    
});
