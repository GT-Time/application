<?php

	$con = mysqli_connect("database.cmahwbtfb8se.us-east-1.rds.amazonaws.com","admin","l&!bsH7rXV");
	mysqli_select_db($con, "app");
	
	$userID = $_POST["userID"];
	$courseNumber = $_POST["courseNumber"];

	$statement = mysqli_prepare($con,"INSERT INTO SCHEDULE VALUES (?,?)");
	mysqli_stmt_bind_param($statement,"ss",$courseNumber,$userID);
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>