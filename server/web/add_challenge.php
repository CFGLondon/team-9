<?php

	$con = mysql_connect("ec2-54-74-45-107.eu-west-1.compute.amazonaws.com", "root", "code4good");

	if (!$con) {
		die('Could not connect: ' . mysql_error());
	}

	mysql_select_db("db", $con);

    $title = $_GET['title'];
    $desc = $_GET['desc'];
    $img = $_GET['img']
    $id = $_GET['id'];

    $challenge = "(" . $id . "'," . $title . "','" . $desc . "'," . $img . ")";

	if (mysql_query("INSERT INTO challenge VALUES " . $challenge)) {
		echo $challenge . " added successfully.";
	} else {
		echo "Error: " . mysql_error();
	}

	mysql_close($con);

?>
