cordova.define("cordova-plugin-Test.TestPlugin",
    function(require, exports, module) {
        var exec = require("cordova/exec");
        module.exports = {
            lbs: function(successCallback, errorCallback, content){
                var success = function(data){
                };
                var error = function(code){
                };
                exec(
                success,
                error,
                "TestPlugin",//feature name
                "lbs",//action
                [content]//要传递的参数，json格式
                );
            },
            contacts: function(successCallback, errorCallback, content){
                exec(
                 function(message){//成功回调function
                            var str;
                            if (typeof message == "object"){
                                str = JSON.stringify(message);
                                console.log("object "+str);
                            }else{
                                str = message;
                                console.log("other "+str);
                            }
                 },
                 function(message){//失败回调function
                       console.log(message);
                 },
                "TestPlugin",//feature name
                "contacts",//action
                [content]//要传递的参数，json格式
                );
            }
        }
});