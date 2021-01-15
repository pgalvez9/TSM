<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
  // collect value of input field
  $json = file_get_contents('php://input');
  $data = json_decode($json);
  $id = $data->id;
  if (empty($id) && $id !== '0') {
    echo "No se encuentra el ID";
  } 
  else {
    // Create connection
    $con=mysqli_connect("localhost","id15555774_tsm123","qgQsGj)IO+H-Z5#t","id15555774_lightstatus");

    // Check connection
    if (mysqli_connect_errno())
    {
        echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }
    $sql = "SELECT status FROM `devices` WHERE ID = {$id}";
    if ($result = mysqli_query($con, $sql))
    {
	    // We have results, create an array to hold the results
        // and an array to hold the data
	    $resultArray = array();
	    $tempArray = array();
 
	    // Loop through each result
	    while($row = $result->fetch_object())
	    {
		    // Add each result into the results array
		    $tempArray = $row;
	        array_push($resultArray, $tempArray);
	    }
 
	    // Encode the array to JSON and output the results
	    echo json_encode($resultArray);
    }
    // Close connections
    mysqli_close($con);
    //echo $sql;
    }
}
?>