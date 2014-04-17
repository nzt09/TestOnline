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

$(function() {
    var d1 = [];
    var pictureData = document.getElementById('pictureData').value;
    var data = pictureData.split("#");
    for (var i = 0; i < data.length; i++) {
        var str = data[i].split("@");
        d1.push([i + 1, str[1]]);
    }
    $.plot($("#placeholder"), [d1]);
});