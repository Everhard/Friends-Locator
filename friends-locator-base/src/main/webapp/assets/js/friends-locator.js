var Map = function() {
    
    var yandexMap;
    var membersCollection;
    
    this.init = function(id) {
        
        $("#" + id).css('height', $("body").height());
        
        ymaps.ready(function () {
            yandexMap = new ymaps.Map(id, {
                center: [50.4438, 30.5177],
                zoom: 10,
                controls: []
            });
            
            membersCollection = new ymaps.GeoObjectCollection();
            yandexMap.geoObjects.add(membersCollection);
        });
        
        return this;
    }
    
    this.getCurrentLocation = function(callback) {
        
        ymaps.geolocation.get({
            provider: 'browser',
            autoReverseGeocode: false,
        }).then(function(res) {
            callback(res.geoObjects.get(0).geometry.getCoordinates());
        });
        
        return this;
    }
    
    this.updateMembersLocation = function(members) {
        
        for (var member in members) {
            
            var memberId = "m" + members[member].groupMemberId;
            var coordinates = [members[member].latitude, members[member].longitude];
            
            if (memberId in app.members) {
                
                /*
                 * Update position:
                 */
                app.members[memberId].geometry.setCoordinates(coordinates);
                
                continue;
            }
            
            app.members[memberId] = new ymaps.GeoObject({
                geometry: {
                    type: "Point",
                    coordinates: coordinates,
                },
                properties: {
                    iconContent: 'Участник №' + members[member].groupMemberId,
                    hintContent: '7 секунд назад'
                }}, {
                    preset: 'islands#blackStretchyIcon',
            });

            membersCollection.add(app.members[memberId]);
        }
    }
}

var Server = function() {
    
    this.getGroupId = function(callback) {
        
        this.makeRequest("GET", "/ajax/group/new-id", function(data) {
            callback(data.stringGroupId);
        });
        
        return this;
    }
    
    this.getGroupCoordinates = function(callback) {
        
        var params = {
            groupId: app.groupId,
        }
        
        this.makeRequest("GET", "/ajax/group/friends-coordinates", function(data) {
            callback(data);
        }, params);
        
        return this;
    }
    
    this.makeRequest = function(method, url, callback, requestData) {
        
        var attempt = 1;
        
        requestData = (typeof(requestData) !== "undefined") ? requestData : {};
        
        this.doAjax({
           method: method,
           url: url,
           data: requestData,
           success: function(response) {
               var responseData = $.parseJSON(response);               
               callback(responseData);
           },
           error: function() {
               console.log("Error while trying to make request!");
           }
        }, 3);
        
        return this;
    }
    
    this.doAjax = function(ajaxArgs, attempt) {
        
        var wrappedDfd = $.Deferred();
        var attemptCounter = 0;

        (function nestedCall() {
            $.ajax(ajaxArgs).then(wrappedDfd.resolve, function() {
                attemptCounter++;
                
                if (attemptCounter < attempt) {
                    
                    setTimeout(function() {
                        nestedCall();
                    }, attemptCounter * 2000);
                    
                } else {
                    wrappedDfd.reject();
                }
            });
        })();

        return wrappedDfd.promise();
    }
}

var URL = function() {
    
    this.getGroupId = function() {
        
        var UUID = /[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/i;
        var matchedGroupId;
        
        if ((matchedGroupId = UUID.exec(document.location.pathname)) != null) {
            return matchedGroupId[0];
        }
        
        return false;
    }
}

/*
 * Application config:
 */
var app = {
    server : new Server(),
    map : new Map(),
    members : {},
    username : '',
    groupId : ''
}

$(document).ready(function() {
    /*
     * Get group ID from URL:
     */
    var url = new URL();
    app.groupId = url.getGroupId();
    
    /*
     * Create new group:
     */
    $("#create-group-button").on("click", function(e) {
        
        $(this).button('loading');
       
        app.server.getGroupId(function(groupId) {
            // Redirection:
            document.location.href = "/group/" + groupId;
        });
       
        e.preventDefault();
    });
    
    $("#group-url").text(document.location);
    
    /*
     * Connect to the group:
     */
    $("#save-name-button").on("click", function(e) {
        
        if (!$("#inputName").val()) {
            return false;
        }
        
        app.map.init("friends-locator-map");
        app.username = $("#inputName").val();
        
        $('#enterNameModal').modal('hide');
        
        e.preventDefault();
    });
    
    $('#enterNameModal').on('hidden.bs.modal', function (e) {
        
        if (!$("#inputName").val()) {
            return false;
        }
        
        app.map.getCurrentLocation(function(coordinates) {
            console.log(coordinates);
            
            $("header + .start-part").animate({
                marginTop : "-500px"
            }, 500, function() {

                $(".start-part").remove();
                $("#map-page").slideDown(500);

                app.server.getGroupCoordinates(function(data) {
                    console.log(data);
                    app.map.updateMembersLocation(data);
                });                
                
            });
        });
    })
});