<?php
 
// Create connection
$con=mysqli_connect("localhost","id15555774_tsm123","qgQsGj)IO+H-Z5#t","id15555774_lightstatus");
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
// Select all of our stocks from table 'stock_tracker'
$sql = "UPDATE `light` SET `Status`=1 WHERE ID = 0";
 
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