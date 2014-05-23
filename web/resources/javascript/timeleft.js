/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function serverCurrTime() {
    
    var secondTime=document.getElementById("myForm:timeSecond").value;
    var count = secondTime;
    var str = "";

    if (count % 60 !== 0) {

        var temp = count / 60 + "";
        temp = temp.split(".");

        str = "0" + temp[0] + ":" + ((count % 60 - 1) > 9 ? count % 60 - 1 + "" : "0" + (count % 60 - 1)) + ":" + "59";

    } else {

        str = "0" + ((count / 60 > 1) ? (count / 60 - 1) : 0) + ":" + 59 + ":" + "59";
        
    }
    document.getElementById("currTime").innerHTML = str;
    setTimeout(reduceOneSec, 1000);

}

function reduceOneSec() {//自动减少一秒
    var currTime = document.getElementById("currTime");

    //	alert(currTime.innerHTML);

    var tempTime = currTime.innerHTML.split(":");

    var hour = tempTime[0];

    var minute = tempTime[1];

    var second = tempTime[2];

    //alert(hour+":"+minute+":"+second);

    if (second >= 1) {

        second = (second - 1 > 9) ? (second - 1) : "0" + (second - 1);

    } else {

        second = "59";

        if (minute >= 1) {

            minute = (minute - 1 > 9) ? (minute - 1) : "0" + (minute - 1);

        } else {

            if (hour >= 1) {

                minute = "59";

                hour = (hour - 1 > 9) ? (hour - 1) : "0" + (hour - 1);

                alert(hour + ":" + minute);
            } else {

                minute = "00";

                hour = "0" + 0;

            }

        }

    }

    document.getElementById("currTime").innerHTML = hour + ":" + minute + ":" + second;
    if (hour == 0 && minute == 15 && second == 0) {

        alert("还剩15分钟!");
        setTimeout(reduceOneSec, 1000);
    } else if (hour == 0 && minute == 15 && second == 0) {
        alert("考试结束");
    }
    else {

        setTimeout(reduceOneSec, 1000);
    }

}
