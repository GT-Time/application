<?php 
	header("Content-Type: text/html; charset=UTF-8");
	$con = mysqli_connect("database.cmahwbtfb8se.us-east-1.rds.amazonaws.com","admin","l&!bsH7rXV");
	mysqli_select_db($con, "app");
	
	$userID = $_GET["userID"];

	$result = mysqli_query($con,"SELECT COURSE.courseNumber, COURSE.courseTime, COURSE.courseProfessor, COURSE.courseTitle, COURSE.courseCredit, COURSE.courseLocation FROM USER, COURSE, SCHEDULE WHERE USER.userID = '$userID' AND USER.userID = SCHEDULE.userID AND SCHEDULE.courseNumber = COURSE.courseNumber");


	$response = array();
	while($row = mysqli_fetch_array($result)) {
		array_push($response, array("courseNumber" => $row[0], "courseTime" => $row[1], "courseProfessor" => $row[2], "courseTitle" => $row[3], "courseCredit" => $row[4], "courseLocation" => $row[5]));
	} 

	echo json_encode(array("response" => $response));
	mysqli_close($con);
?>