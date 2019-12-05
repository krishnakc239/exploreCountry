function exploreCountries() {
    var country = $("#country").val();
    if (country.trim() == "") {
        alert("Please provide country code...");
        return;
    }

    var matchRegion = $("#matchRegion").prop('checked');
    var matchIncomeLevel = $("#matchIncomeLevel").prop('checked');
    var matchLendingLevel = $("#matchLendingLevel").prop('checked');

    var lat = 0;
    var lng = 0;

    $.ajax({
        type: "GET",
        async: false,
        url: "http://localhost:8080/api/getcountry/" + country,
        success: function (data) {
            lat = data.latitude;
            lng = data.longitude;

        }
    });

    if (lat == undefined && lng == undefined) {
        alert("Country not found...");
        return;
    }

    if (!(matchRegion || matchIncomeLevel || matchLendingLevel)) {
        alert("PLease check at least one option...");
        return;
    }

    //alert(lat + " " + lng);
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 2,
        center: new google.maps.LatLng(lat, lng),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    var infowindow = new google.maps.InfoWindow();

    var marker, i;
    $.get("http://localhost:8080/api/countries/"
        + country + "?matchRegion="
        + matchRegion + "&matchIncomeLevel="
        + matchIncomeLevel + "&matchLendingLevel="
        + matchLendingLevel,
        function (data) {
            $.each(data, function (index, value) {
                marker = new google.maps.Marker({
                    position: new google.maps.LatLng(value.latitude, value.longitude),
                    map: map,
                    icon: {
                        url: "http://maps.google.com/mapfiles/ms/icons/green-dot.png"
                    }
                });

                google.maps.event.addListener(marker, 'click', (function (marker, i) {
                        return function () {
                            infowindow.setContent(value.name + "/" + value.capitalCity);
                            infowindow.open(map, marker);
                        }
                    }
                )(marker, i));
                $("#count").html('Similar countries found: ' + index);
            })
        });
}