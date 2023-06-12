<?php
    include "connect.php";

    // số lượng sản phẩm được mua
    $query = "SELECT ct.idsanpham, sp.tensanpham, SUM(soluongsanpham) AS tongsoluong FROM chitietdonhang AS ct
            INNER JOIN sanpham AS sp ON ct.idsanpham = sp.id GROUP BY idsanpham";

    $data = mysqli_query($conn, $query);
    $mang = array();

    while($row = mysqli_fetch_assoc($data)) {
        $mang[] = $row; // đưa row vào mảng
    }

    echo json_encode($mang);
?>