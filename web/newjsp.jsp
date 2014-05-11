<!DOCTYPE html public "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="Keywords" content="YES!B/S!,web??,???,???,????" />
	<meta name="Description" content="??????YES!B/S!??????,????????,http://justinyoung.cnblogs.com/" />
	<title>YES!B/S!??????</title>
<title>gchart????</title>
<style type="text/css">
#basicGChart { width: 450px; height: 300px }
</style>
<script type="text/javascript" src="resources/javascript/jquery.js"></script>
<script type="text/javascript" src="resources/javascript/jquery.gchart.js"></script>
<script type="text/javascript">
$(function () {
	//???
	var array1=[15, 13, 12, 33,28, 21, 30, 31, 33, 25, 22, 18];
	//????
	var array2=['1?','2??','3?','4?','5?','6?','7?','8?','9?','10?','11?','12?'];
	$('#basicGChart').gchart({
		type: 'line',//????
		title: '2009???????????', //????
		series: [
			$.gchart.series(array1,'red')//????
		],
		axes: [//?????
			$.gchart.axis('left', 0, 100,'blue'),
			$.gchart.axis('bottom',array2,'008000')
		],
		legend: 'top'});
});
</script>
</head>
<body>
<h2>?????gchart??</h2>
<div id="basicGChart">
</div>
</body>
</html>