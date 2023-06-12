<?php
    include "connect.php";

    $id = $_POST['id'];
    $pass = $_POST['pass'];

    // update bảng user
    $query = "UPDATE user SET pass = '$pass' WHERE id = $id";
    $data = mysqli_query($conn, $query); // thực hiện câu lệnh

    if($data) {
        echo "1"; // update thành công trả về 1
    } else {
        echo "0"; // update thất bại trả về 0
    }
?>