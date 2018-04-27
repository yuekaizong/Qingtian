console.log("welcome editHome");

function getFileUrl(sourceId) {
   var url;
   if (navigator.userAgent.indexOf("MSIE")>=1) { // IE
       url = document.getElementById(sourceId).value;
   } else if(navigator.userAgent.indexOf("Firefox")>0) { // Firefox
       url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
   } else if(navigator.userAgent.indexOf("Chrome")>0) { // Chrome
       url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
   }
   return url;
}

function previewImg(sourceId, targetId) {
   var url = getFileUrl(sourceId);
   var imgPre = document.getElementById(targetId);
   imgPre.src = url;
}

function previewText(sourceId, targetId) {
   var dest = document.getElementById(targetId);
   document.getElementById(targetId).innerHTML = dest.value;
}

function getFormJson(frm) {  //frm：form表单的id
        var o = {};
        var a = $("#"+frm).serializeArray();
        $.each(a, function() {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [ o[this.name] ];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    }

function getData(source){
    var json = JSON.stringify($('#form1').serializeJSON());
    var obj = JSON.parse(json);
    var array = formParseArray(obj);
    console.log(array);
   $.ajax({
     type: "GET",
     url: "http://localhost/data",
     crossDomain: true,
     dataType: "jsonp",
     success: success,
     error: fail
   });

   function success(result){
      console.log(result);
   }

   function fail(result){
     console.log(result);
   }
}

function formParseArray(obj){
    var data = [];
    for(var key in obj){
        var item = obj[key];
        var item_cells = [];
        for(var item_key in item.cells){
            item_cells.push(item.cells[item_key])
        }
        item.cells = item_cells;
        data.push(item);
    }
    return data;
}

function sendData(source){
   $.ajax({
     type: "POST",
     url: "https://api.douban.com//v2/movie/top250",
     url: "/saveData",
     crossDomain: true,
     dataType: "jsonp",
//     dataType: "json",
     success: success,
     error: fail
   });

   function success(result){
      console.log(result);
   }

   function fail(result){
     console.log(result);
   }
}

