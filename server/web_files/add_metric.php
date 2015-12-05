<?php

	$con = mysql_connect("http://ec2-54-74-45-107.eu-west-1.compute.amazonaws.com", "root", "code4good");

	if (!$con) {
		die('Could not connect: ' . mysql_error());
	}

	mysql_select_db("db", $con);

    $m1 = $_GET['m1'];
    $m2 = $_GET['m2'];
    $m3 = $_GET['m3'];
    $m4 = $_GET['m4'];
    $m5 = $_GET['m5'];
    $m6 = $_GET['m6'];
    $m7 = $_GET['m7'];
    $id = $_GET['id'];

    $metric = "(" . $id . "," . $m1 . "" . $m2 . "," . $m3 . "" . $m4 . "," . $m5 . "" . $m6 . "," . $m7 . ")";

	if (mysql_query("INSERT INTO metric VALUES " . $metric)) {
		echo $metric . " added successfully.";
	} else {
		echo "Error: " . mysql_error();
	}

	mysql_close($con);

?>
