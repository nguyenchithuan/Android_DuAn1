<?php
    include "connect.php";

    $query = "SELECT * FROM sanpham ORDER BY id DESC LIMIT 5";
        $data = mysqli_query($conn, $query);    

    $mang = array();

    while($row = mysqli_fetch_assoc($data)) {
        array_push($mang, new SanPhamMoiNhat(
            $row['id'],
            $row['tensanpham'],
            $row['giasanpham'],
            $row['hinhanhsanpham'],
            $row['motasanpham'],
            $row['idsanpham']
        ));
    }

    echo json_encode($mang);


    class SanPhamMoiNhat {
        function SanPhamMoiNhat($id, $tensanpham, $giasanpham, $hinhanhsanpham, $motasanpham, $idsanpham) {
            $this->id = $id;
            $this->tensanpham = $tensanpham;
            $this->giasanpham = $giasanpham;
            $this->hinhanhsanpham = $hinhanhsanpham;
            $this->motasanpham = $motasanpham;
            $this->idsanpham = $idsanpham;
        }
    }
?>