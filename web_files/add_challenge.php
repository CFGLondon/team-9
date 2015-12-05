<?php

	$con = mysql_connect("0.0.0.0", "root", "");

	if (!$con) {
		die('Could not connect: ' . mysql_error());
	}

	mysql_select_db("db", $con);

	$challenge = $_GET['challenge'];

	if (mysql_query("INSERT INTO challenges VALUES " . $challenge)) {
		echo $challenge . " added successfully.";
	} else {
		echo "Error: " . mysql_error();
	}

	mysql_close($con);

?>
