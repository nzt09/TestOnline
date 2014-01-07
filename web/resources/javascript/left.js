$(document).ready(function() {
    $(".title").click(function() {
        var that = $(this);
        var text = that.attr("class");
        var num = text.indexOf("title2");
        if (num == -1) {
            that.addClass("title2");
        }else{
            that.removeClass("title2");
        }
        var next = that.next();
        next.toggle("fast");
    });
});
