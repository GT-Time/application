<?php include "../inc/dbinfo.inc"; ?>
  
<?php
$conn = mysqli_connect(DB_SERVER, DB_USERNAME, DB_PASSWORD);
mysqli_select_db($conn, DB_DATABASE);

$userID = $_POST["userID"];

$statement = mysqli_prepare($conn, "SELECT userID FROM USER WHERE userID=?");
mysqli_stmt_bind_param($statement,"s", $userID);
mysqli_stmt_execute($statement);
mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement,$userID); /* store userIDs loaded from database */

$response = array();
$response["success"] = true;

while(mysqli_stmt_fetch($statement)) {
$response["success"] = false;
$response["userID"] = $userID;
}

echo json_encode($response);
?>

~                        