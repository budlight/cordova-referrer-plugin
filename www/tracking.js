var exec = require('cordova/exec');


var trackingPlugin = {
    get: function(key, successCallback, errorCallback){
        return cordova.exec(successCallback, errorCallback, 'TrackingPlugin', 'get', [key])
    }
    
}

module.exports =  trackingPlugin;