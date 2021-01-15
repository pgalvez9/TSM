<?php
header('Content-Type: application/json');
// Create connection
$con=mysqli_connect("localhost","id15555774_tsm123","qgQsGj)IO+H-Z5#t","id15555774_lightstatus");
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
$response = array();

$body = json_decode(file_get_contents("php://input"), true);

$id = $body['id'];
$name = $body['name'];
$status = $body['status'];

$sql = "INSERT INTO devices (id, name, status) 
VALUES ('$id', '$name', '$status')";

// Confirm there are results
if (mysqli_query($con, $sql))
{
    print("done");
}
else
{
	print("error");
}
// Close connections
mysqli_close($con);
?>