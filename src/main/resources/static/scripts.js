var map;
var marker;
var geocoder;
var insuredlist;
var addy;
function GoogleMapInit(id, latitude, longitude, address) 
{    
   var latlng = new google.maps.LatLng(latitude, longitude);
   var latlngMarker = new google.maps.LatLng(latitude, longitude);
   var mapOptions = 
   {        
      center: latlng,        
      mapTypeId: google.maps.MapTypeId.ROADMAP,
      mapTypeControl: true,        
      scrollwheel: true,
      streetViewControl: false,        
      zoom: 9,
      zoomControl: true,        
      zoomControlOptions: { style: google.maps.ZoomControlStyle.SMALL }
   }
   element = document.getElementById(id);
   map = new google.maps.Map(element, mapOptions);   
   marker = new google.maps.Marker({ draggable:true, map: map, position: latlngMarker});   
   geocoder = new google.maps.Geocoder();
   geocoder.geocode({'address': address}, function(results, status) 
   {
      if (status == google.maps.GeocoderStatus.OK) 
      {
         map.setCenter(results[0].geometry.location);
         var marker = new google.maps.Marker(
         {
            map: map, 
            position: results[0].geometry.location
         });
      } 
      else 
      {
        //    alert("Geocode was not successful: " + status);
      }
   });
}
//geocode function
function GoogleMapcall()
{
	addy = document.getElementById("Editbox1").value;
	GoogleMapRecall(addy);
}

function GoogleMapRecall(address) 
{
	//alert("new");
	//alert(address); 
    geocoder.geocode( { 'address': address}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) 
        {
            map.setCenter(results[0].geometry.location);
            var marker = new google.maps.Marker({
                map: map, 
                position: results[0].geometry.location
            });
        } 
        else 
        {
            alert("Geocode was not successful for the following reason: " + status);
        }
    });
}

function callClaimSvcByzip()
{
        //alert("In claim");
		var claimSvcURL = "/custbypostalcode/"+ addy;
        var returnData = "";
        $.ajax({
            type: "GET",
            dataType: 'jsonp',
			jsonp: 'callback',
			jsonpCallback: 'jsonpCallback',
            async: true,
            url: claimSvcURL,
            success: function(data) {
					//alert("Server said123:\n '" + data + "'");
            },
			error: function(e){  
					alert('Error121212: ' + e);
					console.log(e);
					
       }  
        });
        return (false);

    }
function jsonpCallback(data) {
	//alert("in callback");

	console.log(data);
	insuredlist = data;
	console.log(insuredlist);
	console.log(insuredlist.Customer_zip.length);
	buildInsured(insuredlist);
}
function buildInsured(insuredlist) {
   console.log("in build insured");
   var insured = 0;
   var _lat;


   
 	 	for (insured = 0; insured < insuredlist.Customer_zip.length; insured++) {
		  var parsed_lat_long = { lat:parseFloat( insuredlist.Customer_zip[insured].position.substring(0, 9) ), lng:parseFloat( insuredlist.Customer_zip[insured].position.substring(10, 19)  ) };
		  console.log(parsed_lat_long);
 	  	  var marker = new google.maps.Marker({
 			//icon: icon.png,
			position: parsed_lat_long,
			animation: google.maps.Animation.DROP,
 			map: map,
 			title: "Policy: " + insuredlist.Customer_zip[insured].policyNbr
 		  });
 		  
 		  var infowindow = new google.maps.InfoWindow({ 
      		content: '<IMG BORDER="0" ALIGN="Left" SRC="icon.png">' + 
      		"Insured: " +  insuredlist.Customer_zip[insured].name + "<br />" + 
      		"Policy #: " + insuredlist.Customer_zip[insured].policynbr + "<br />" +
      		"Contact Info: " + insuredlist.Customer_zip[insured].telephoneNbr + "<br />" +  
      		'<IMG BORDER="0" ALIGN="Left" SRC="storm damage example.jpg">'
	  	  });
 		   		  
 		  marker.addListener('click', function() {
   			map.setZoom(8);
    		map.setCenter(marker.getPosition());
    		infowindow.open(map, marker);
 		  });
	  	}
}