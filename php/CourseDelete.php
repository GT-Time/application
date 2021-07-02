<?php

	$con = mysqli_connect("database.cmahwbtfb8se.us-east-1.rds.amazonaws.com","admin","l&!bsH7rXV");
	mysqli_select_db($con, "app");
	
	$userID = $_POST["userID"];
	$courseID = $_POST["courseNumber"];

	$statement = mysqli_prepare($con,"DELETE FROM SCHEDULE WHERE userID = ? AND courseID = ?");
	mysqli_stmt_bind_param($statement,"si",$userID,$courseID);
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>