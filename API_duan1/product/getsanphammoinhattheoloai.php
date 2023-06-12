<?php
    include "connect.php";

    $idsanpham = $_POST['idsanpham'];

    $query = "SELECT * FROM sanpham WHERE idsanpham = $idsanpham ORDER BY id DESC";
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