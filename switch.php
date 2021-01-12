<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
  // collect value of input field
  $json = file_get_contents('php://input');
  $data = json_decode($json);
  $id = $data->id;
    $status=$data->status;
  if (empty($id)  && $id !== '0') {
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
      $sql = "UPDATE `devices` SET `Status`={$status} WHERE ID = {$id}";
    if ($result = mysqli_query($con, $sql))
    {
        echo "success"
    }
    else{
        echo "error"
    }
    // Close connections
    mysqli_close($con);
    //echo $sql;
    }
}
?>
