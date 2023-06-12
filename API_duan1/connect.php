<?php
    $host = "localhost";
    $dbuser = "id19833921_user";
    $dbpass = "Q1tH\WB]\<HCrQ)d";
    $database = "id19833921_duan1";

    $conn = mysqli_connect($host, $dbuser, $dbpass, $database);

    if($conn) {
        mysqli_query($conn, "SET NAMES 'utf8'");
    } else {
        echo "Kết nối thất bại".mysqli_connect_error();
    }
?>