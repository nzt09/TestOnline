$(document).ready(function() {
    $(".title").click(function() {
        var that = $(this);
        var text = that.attr("class");
        var num = text.indexOf("title2");
        if (num == -1) {
            that.addClass("title2");
        } else {
            that.removeClass("title2");
        }
        var next = that.next();
        next.toggle("fast");
    });
});

function show_confirm()
{

    alert("试卷生成成功");
}

function delete_Confirm()
{

    alert("确定要删除？");
}

function show_info() {
    alert("添加成功！")
}


function show_scorePic() {
    var i = 0;
    var scoreData = document.getElementById("mynewForm:picData").value;
    var array = scoreData.split(",");
    var arraynew = new Array();
    for (; i < array.length; i++) {
        arraynew[i] = eval(array[i]);
    }
    $.plot($("#placeholder"), [d1]);
}
