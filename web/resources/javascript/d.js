var xmlhttp;
var num = 0;
function createXmlhttp() {
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
}
function show() {
    alert();
    var value = document.getElementById('form:type').value;
    alert(value);
}
function openwin(valueContainer, idValueContainer, flag) {
    window
            .open(
                    'mytree.xhtml?tablename=&OKbutton=1&valueContainer=' + valueContainer
                    + '&idValueContainer=' + idValueContainer
                    + '&flag=' + flag,
                    'openwin',
                    'width=200 ,height=650, top=150, left=350, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
}
function newwin(url, width, height) {
    window
            .open(
                    url,
                    'newwin',
                    'width='
                    + width
                    + ',height='
                    + height
                    + ', top=150, left=350, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
}
function getValue(valueContainer, idValueContainer) {
    var isnotLeaf = tree.currentNode.hasChild;
    var r1 = tree.currentNode.text;
    var id = new String(tree.currentNode.sourceIndex);
    var index = id.indexOf("_") + 1;
    if (r1 == "") {
        alert("该项未选中或者禁止选中！");
    } else {

        // if(null==window.opener.document.getElementById("itemschecked")&&null==window.opener.document.getElementById("nameofunit")){//一般情况下公用的取值
        window.close();
        window.opener.document.getElementById('form:' + valueContainer).value = r1;
        window.opener.document.getElementById('form:' + idValueContainer).value = id
                .substring(index, id.length);
        // alert("dfd");
        window.opener.document.getElementById('form:' + valueContainer)
                .fireEvent("onblur");
        window.opener.document.getElementById('form:' + idValueContainer)
                .fireEvent("onchange");
    }
}
function juse() {
    document.getElementById("selectrole").value = form1.role.value;
    document.getElementById("selectrole1").value = form1.role.value;
    createXmlhttp();
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            // 移除“请稍候”的信息----开始
            if (null != document.getElementById("mo")) {
                document.getElementById("wait").removeChild(
                        document.getElementById("mo"));
                num = 0;
            }
            // 移除“请稍候”的信息----结束
            document.getElementById("txtHint").innerHTML = xmlhttp.responseText;
            setSecondChecked('role', 'isCallSeeAll', 'change',
                    '../tools/RoleCanseeAllGetter');
        } else {
            add_div();
        }
    };
    xmlhttp
            .open("POST", "../operation/AjaxResource?roleID="
                    + form1.role.value);
    xmlhttp.send();
}
function editJs(edittype) {
    document.getElementById("edittype").value = parseInt(edittype);
}
function checkAdd() {
    if (document.getElementById("edittype").value == 0) {
        if (document.getElementById("addjs").value.length == 0) {
            alert("请输入要添加的角色名称");
            document.getElementById("addjs").focus();
            return false;
        } else
            return true;
    }
    return;
}

function Delete() {
    if (confirm("该课程可能包含知识点，题目，删除课程会连同下面的知识点，题目一并删除,您是否还要删除？？")) {
        return true;
    }
    ;
    return false;
}
