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
    if (arraynew[0] == null) {
        alert("请选择班级！");
    }
    else {
        $('#container1').highcharts({
            title: {
                text: '学生成绩反馈表',
                x: -20 //center
            },
            subtitle: {
                text: '来自数据库',
                x: -20
            },
            xAxis: {
                categories: ['0~59分', '60~69分', '70~79分', '80~89分', '90~100分']
            },
            yAxis: {
                title: {
                    text: '人数 (人)'
                },
                min:0,
                plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
            },
            tooltip: {
                valueSuffix: '人'
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            series: [{
                    name: '学生成绩曲线',
                    data: arraynew
                }]
        });
    }
}

function show_knowledgePic() {
    var i = 0;
    var j = 0;
    var passLuData = document.getElementById("mynewForm:passLu").value;

    var knowledgeName = document.getElementById("mynewForm:knowledgeName").value;


    var array = passLuData.split(",");
    var array2 = knowledgeName.split(",");
    var array1 = new Array();
    for (; i < array.length - 1; i++) {
        array1[i] = eval(array[i]);
    }
    if (array[0] == null) {
        alert("请选择班级！");
    }
    else {
        $('#container1').highcharts({
            title: {
                text: '知识点对应题目的错误率',
                x: -20 //center
            },
            subtitle: {
                text: '来自数据库',
                x: -20
            },
            xAxis: {
                categories: array2
            },
            yAxis: {
                title: {
                    text: '错误率 (%)',
                    lineWidth: 2
                },
                max:100,
                min:0,
                plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
            },
            tooltip: {
                valueSuffix: '%'
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            series: [{
                    name: '错误率',
                    data: array1
                }]
        });

    }
}
