<?php
	$con = mysqli_connect("database.cmahwbtfb8se.us-east-1.rds.amazonaws.com","admin","l&!bsH7rXV");
	mysqli_select_db($con, "app");
	
	$userID = $_POST["userID"];
	$userPassword = $_POST["userPassword"];

	$checkedPassword = password_hash($userPassword, PASSWORD_DEFAULT);
	$statement = mysqli_prepare($con,"SELECT * FROM USER WHERE userID = ?");
	mysqli_stmt_bind_param($statement,"s",$userID);
	mysqli_stmt_execute($statement);

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement,$userID, $checkedPassword, $userGender, $userMajor, $userEmail);

	$response = array();
	$response["success"] = false;

	while(mysqli_stmt_fetch($statement)) {
		if(password_verify($userPassword, $checkedPassword))
		$response["success"] = true;
		$response["userID"] = $userID;
	}
	echo json_encode($response);

?>