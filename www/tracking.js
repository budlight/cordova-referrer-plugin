var exec = require('cordova/exec');


var trackingPlugin = {
    get: function(key, successCallback, errorCallback){
        cordova.exec(successCallback, errorCallback, 'TrackingPlugin', 'get')
    }
    
}

module.exports =  trackingPlugin;