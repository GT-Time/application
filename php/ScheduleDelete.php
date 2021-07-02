<?php
	$con = mysqli_connect("database.cmahwbtfb8se.us-east-1.rds.amazonaws.com","admin","l&!bsH7rXV");
	mysqli_select_db($con, "app");
	
	$userID = $_POST["userID"];
	$courseNumber = $_POST["courseNumber"];

	$statement = mysqli_prepare($con, "DELETE FROM SCHEDULE WHERE userID = '$userID' AND courseNumber = '$courseNumber'");
	mysqli_stmt_bind_param($statement,"ss",$userID,$courseNumber);
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = true;

	echo json_encode($response);
	mysqli_close($con);
?>