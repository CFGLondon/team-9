<?php

	$con = mysql_connect("ec2-54-74-45-107.eu-west-1.compute.amazonaws.com", "root", "code4good");

	if (!$con) {
		die('Could not connect: ' . mysql_error());
	}

	mysql_select_db("db", $con);

    $g = $_GET['goal'];
    $id = $_GET['id'];

    $go = "(" . $id . ",'" . $g . "')";

	if (mysql_query("INSERT INTO goal VALUES " . $go)) {
		echo $challenge . " added successfully.";
	} else {
		echo "Error: " . mysql_error();
	}

	mysql_close($con);

?>
