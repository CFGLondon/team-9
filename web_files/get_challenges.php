<?php
	$con = mysql_connect("http://ec2-54-74-45-107.eu-west-1.compute.amazonaws.com", "root", "")

	if (!$con) {
		die('Could not connect: ' . mysql_error());
	}

	mysql_select_db("db", $con);

	// stars in a table in the db bigstars.
	$result = mysql_query("SELECT * FROM challenge WHERE userID = ");

	while ($row = mysql_fetch_assoc($result)) {
		$output[] = $row;
	}

	print(json_encode($output));
	mysql_close($con);

?>
