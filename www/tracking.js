var exec = require('cordova/exec');


var trackingPlugin = {
    getTracking: function(key, successCallback, errorCallback){
        cordova.exec(successCallback, errorCallback, 'TrackingPlugin', 'getTracking')
    }
    
}

module.exports =  trackingPlugin;