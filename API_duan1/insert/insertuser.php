<?php
    include "connect.php";

    $username = $_POST['username'];
    $email = $_POST['email'];
    $pass = $_POST['pass'];
    $phone = $_POST['phone'];

    $query = "INSERT INTO user(id, username, email, pass, phone) 
    VALUES(NULL, '$username', '$email', '$pass', '$phone')";

    $data = mysqli_query($conn, $query);

    if($data) {
        echo "1";
    } else {
        echo "0";
    }
?>