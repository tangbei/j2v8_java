var allJs = "";

function StringFunction(val) {
    var a = "你好！";
    var b = "我是js返回的字符串";
    allJs = a + b + "全局数据哦";
    var x = 0;
    for (var i = 0; i < 10;i++){
        for (var j = 0; j < 10;j ++){
            if (i == j){
                x = j++;
            }
        }
    }
    return a+b+"序列:"+val;
}

function overAllTest() {
    return allJs;
}

function objFunction() {
    allJs ="我是全局变量在obj中的改变";
    var json = {};
    json.id = 1000;
    json.name = "我是js创建的实体";
    return json;
}

function testObj() {
    var id = 1000;
    var name = "我是js创建的实体";
}

function voidFunction() {
    javaCall("我是方法体中的回调");
}

function jsLog() {
    console.log("我是js中日志的打印");
    return "我是调用java方法的函数体";
}