<?php include "../inc/dbinfo.inc"; ?>
  
<?php
$conn = mysqli_connect(DB_SERVER, DB_USERNAME, DB_PASSWORD);
if(!conn) {
error_log("Connection Error:". mysqli_connect_errno());
error_log("Connection Error:". mysqli_connect_error());
}
mysqli_select_db($conn,DB_DATABASE);

$userID = $_POST["userID"];
$userPassword = $_POST["userPassword"];
$userEmail = $_POST["userEmail"];
$userGender = $_POST["userGender"];
$userMajor = $_POST["userMajor"];

$hashedPW = password_hash($userPassword, PASSWORD_DEFAULT);
$stmt = mysqli_prepare($conn, "INSERT INTO USER VALUES(?, ?, ?, ?, ?)");
mysqli_stmt_bind_param($stmt, "sssss", $userID, $hashedPW, $userGender, $userMajor, $userEmail);
mysqli_stmt_execute($stmt);

$success = true;
$errorCode = 0;
if(mysqli_errno($conn)){
$success = false;
$errorCode = mysqli_errno($conn);
echo mysqli_error($conn);
}

$response = array();
$response["success"] = $success;
$response["errorCode"] = $errorCode;

echo json_encode($response);
?>
