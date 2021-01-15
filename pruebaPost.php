<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
  // collect value of input field
  $json = file_get_contents('php://input');
  $data = json_decode($json);
  $id = $data->id;
  if (empty($id)  && $id !== '0') {
    echo "No se encuentra el ID";
  } 
  else {
    $sql = "SELECT Status FROM `light`WHERE ID = {$id}";
    echo $sql;
  }
}
?>