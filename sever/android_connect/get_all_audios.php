<?php
/*
 * Following code will list all the audios
 */
// array for JSON response
header('Content-type: text/html; charset=utf-8');


$response = array();
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// get all audios from audios table
$result = mysql_query("SELECT *FROM audio order by title") or die(mysql_error());
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // audios node
    $response["audios"] = array();
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $audio = array();
        $audio["id"] = $row["id"];
        $audio["title"] = $row["title"];
        $audio["url"] = $row["url"];
       
        // push single product into final response array
        array_push($response["audios"], $audio);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response m
    echo json_encode($response);
} else {
    // no audios found
    $response["success"] = 0;
    $response["message"] = "No audios found";
 
    // echo no audios JSON
    echo json_encode($response);
}
?>