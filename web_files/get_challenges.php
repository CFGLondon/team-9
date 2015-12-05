<?php
	$con = mysql_connect("0.0.0.0", "root", "")

	if (!$con) {
		die('Could not connect: ' . mysql_error());
	}

	mysql_select_db("db", $con);

	// stars in a table in the db bigstars.
	$result = mysql_query("SELECT * FROM challenges");

	while ($row = mysql_fetch_assoc($result)) {
		$output[] = $row;
	}

	print(json_encode($output));
	mysql_close($con);

?>
