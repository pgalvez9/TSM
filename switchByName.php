<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
  // collect value of input field
  $json = file_get_contents('php://input');
  $data = json_decode($json);
  $name = $data->name;
  $status=$data->status;
  if (empty($name)) {
    echo "No se encuentra el Name";
  } 
  else {
    // Create connection
    $con=mysqli_connect("localhost","id15555774_tsm123","qgQsGj)IO+H-Z5#t","id15555774_lightstatus");

    // Check connection
    if (mysqli_connect_errno())
    {
        echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }
      $sql = "UPDATE `devices` SET `status` = '$status' WHERE `devices`.`name` = '$name'";
    if ($result = mysqli_query($con, $sql))
    {
        echo "success";
    }
    else{
        echo "error";
    }
    // Close connections
    mysqli_close($con);
    //echo $sql;
    }
}
?>
