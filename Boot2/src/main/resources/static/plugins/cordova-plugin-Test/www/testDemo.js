cordova.define("cordova-plugin-Test.TestPlugin",
    function(require, exports, module) {
        var exec = require("cordova/exec");
//        module.exports = {
//            lbs: function(successCallback, errorCallback, content){
//                console.log("successCallback::: "+ typeof successCallback)
//                exec(
//                 function(data){
//                    successCallback(data);
//                 },
//                  function(data){
//                     return errorCallback.apply(this, [data]);
//                 },
//                "TestPlugin",//feature name
//                "lbs",//action
//                [content]//要传递的参数，json格式
//                );
//            },
//            contacts: function(successCallback, errorCallback, content){
//                exec(
//                 function(data){//成功回调function
//                            var str;
//                            if (typeof data == "object"){
//                                str = JSON.stringify(data);
//                                console.log("object "+str);
//                            }else{
//                                str = data;
//                                console.log("other "+str);
//                            }
//                 },
//                 function(result){//失败回调function
//                       console.log(result);
//                 },
//                "TestPlugin",//feature name
//                "contacts",//action
//                [content]//要传递的参数，json格式
//                );
//            }
//        }
var myFunc = function(){};
myFunc.prototype.lbs = function(successCallback, errorCallback, content){
        console.log("这个一个TestCordova lbs "+successCallback);
        var win = function(data){
           testLog(data);
           data = "喀喀喀喀喀喀";
           testLog(data);
           successCallback(data);
        };
        var fail = function(data){
           testLog(data);
           successCallback(data);
        };
        exec(win, fail,  "TestPlugin", "lbs", [content]);
};

myFunc.prototype.contacts = function(successCallback, errorCallback, content){
        exec(successCallback, errorCallback,  "TestPlugin", "contacts", [content]);
};

myFunc.prototype.liveDetect = function(successCallback, errorCallback, content){
        exec(successCallback, errorCallback,  "TestPlugin", "liveDetect", [content]);
};

myFunc.prototype.edApplInfoAndRiskInfo = function(successCallback, errorCallback, content){
        exec(successCallback, errorCallback,  "TestPlugin", "edApplInfoAndRiskInfo", [content]);
};

function testLog(data){
       var str;
       if (typeof data == "object"){
           str = JSON.stringify(data);
           console.log("testLog object "+str);
       }else{
           str = data;
           console.log("testLog other "+str);
       }
}
var obj = new myFunc();
module.exports = obj;
});