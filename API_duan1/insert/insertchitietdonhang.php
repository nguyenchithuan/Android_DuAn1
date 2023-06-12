<?php
    include "connect.php";

    $json = $_POST['json'];
    // chuyển json thành mảng dữ liệu
    $data = json_decode($json, true);

    foreach ($data => $value) {
        $iddonhang = $value['iddonhang'];
        $idsanpham = $value['idsanpham'];
        $tensanpham = $value['tensanpham'];
        $soluongsanpham = $value['soluongsanpham'];
        $giasanpham = $value['giasanpham'];
        
        $query = "INSERT INTO chitietdonhang(id, iddonhang, idsanpham, tensanpham, soluongsanpham, giasanpham) 
        VALUES(NULL, '$iddonhang', '$idsanpham', '$tensanpham', '$soluongsanpham', '$giasanpham')";

        $dt = mysqli_query($connection, $query);
    }

    // kiểm tra xem đã insert thành công chưa
    if($dt) {
        echo "1";
    } else {
        echo "0";
    }
?>
