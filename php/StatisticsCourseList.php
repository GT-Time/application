<?php
	header("Content-Type: text/html; charset = UTF-8");
	$con = mysqli_connect("database.cmahwbtfb8se.us-east-1.rds.amazonaws.com","admin","l&!bsH7rXV");
	mysqli_select_db($con, "app");
	
	$userID = $_GET["userID"];

	$result = mysqli_query($con,"SELECT COURSE.courseNumber, COURSE.courseTitle, COURSE.courseDivide, COURSE.courseCredit, COURSE.courseTime FROM SCHEDULE, COURSE WHERE SCHEDULE.courseNumber IN (SELECT SCHEDULE.courseNumber FROM SCHEDULE WHERE SCHEDULE.userID = '$userID') AND SCHEDULE.courseNumber = COURSE.courseNumber GROUP BY SCHEDULE.courseNumber");

	$response = array();
	while($row = mysqli_fetch_array($result)) {
		array_push($response,array("courseNumber" => $row[0], "courseTitle" => $row[1], "courseDivide" => $row[2], "courseCredit" =>$row[3], "courseTime" => $row[4]));
	}

	echo json_encode(array("response" => $response), JSON_UNESCAPED_UNICODE);
	mysqli_close($con);
?>