<?php
    include "connect.php";
    $iduser = $_POST['iduser'];
    $name = $_POST['name'];
    $address = $_POST['address'];
    $phone = $_POST['phone'];
    $email = $_POST['email'];
    $soluong = $_POST['soluong'];
    $tongtien = $_POST['tongtien'];

    if(strlen($name) > 0 && strlen($address) > 0 && strlen($email) > 0 && strlen($phone) > 0) {
        // insert vào bảng
        $query = "INSERT INTO donhang(id, iduser, name, address, phone, email, soluong, tongtien)
         VALUES(NULL, '$iduser', '$name', '$address', '$phone', '$email', '$soluong', '$tongtien')";
        if(mysqli_query($conn, $query)) {

            // lấy ra id vừa mới insert
            $query_1 = "SELECT id FROM donhang ORDER BY id DESC LIMIT 1";
            $data = mysqli_query($conn, $query_1);
            
            if($row = mysqli_fetch_assoc($data)) {
                echo $row['id'];
            }

        } else {
            echo "Thất bại";
        }
    } else {
        echo "Mời nhập đầy đủ dữ liệu";
    }
?>