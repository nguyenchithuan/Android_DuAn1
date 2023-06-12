<?php

    include "connect.php";

    $idsanpham = $_POST['$idsanpham'];
    $sapxep = $_POST['$sapxep']; // trả về số 1 là tăng dần, còn lại là giảm dần

    if($sapxep == 1) {
        $query = "SELECT * FROM sanpham WHERE idsanpham = $idsanpham ORDER BY giasanpham ASC";
        $data = mysqli_query($conn, $query);
    } else {
        $query = "SELECT * FROM sanpham WHERE idsanpham = $idsanpham ORDER BY giasanpham DESC";
        $data = mysqli_query($conn, $query);
    }
    
    $mang = array();

    while($row = mysqli_fetch_assoc($data)) {
        array_push($mang, new SanPham(
            $row['id'],
            $row['tensanpham'],
            $row['giasanpham'],
            $row['hinhanhsanpham'],
            $row['motasanpham'],
            $row['idsanpham']
        ));
    }

    echo json_encode($mang);

    class SanPham {
        function SanPham($id, $tensanpham, $giasanpham, $hinhanhsanpham, $motasanpham, $idsanpham) {
            $this->id = $id;
            $this->tensanpham = $tensanpham;
            $this->giasanpham = $giasanpham;
            $this->hinhanhsanpham = $hinhanhsanpham;
            $this->motasanpham = $motasanpham;
            $this->idsanpham = $idsanpham;
        }
    }
?>