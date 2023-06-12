<?php
    include "connect.php";
    
    $iduser = $_POST['iduser'];

    $query = "SELECT * FROM donhang WHERE iduser = $iduser";
    $data = mysqli_query($conn, $query);
    $mang = array();

    while($row = mysqli_fetch_assoc($data)) {
        $iddonhang = $row['id']; // lấy ra id đơn hàng
        
        $query_1 = "SELECT * FROM chitietdonhang 
        INNER JOIN sanpham ON chitietdonhang.idsanpham = sanpham.id
        WHERE chitietdonhang.iddonhang = $iddonhang";
        
        $data_1 = mysqli_query($conn, $query_1);
        $mang_1 = array();
        while($row_1 = mysqli_fetch_assoc($data_1)) {
            $mang_1[] = $row_1;
        }

        $row['item'] = $mang_1;

        $mang[] = $row; // thêm đối tượng vào trong mảng
    }

    echo json_encode($mang);



    // kết quả trả về 1 item trong mảng (có mảng)
    // {
    //     "id": "64",
    //     "iduser": "2",
    //     "name": "thuan",
    //     "address": "Hồ Chí Minh",
    //     "phone": "09123421341",
    //     "email": "thuan@gmail.com",
    //     "soluong": "2",
    //     "tongtien": "309820000",
    //     "item": [
    //       {
    //         "id": "26",
    //         "iddonhang": "64",
    //         "idsanpham": "25",
    //         "tensanpham": "Xiaomi Redmi Note 11 Pro",
    //         "soluongsanpham": "3",
    //         "giasanpham": "19500000"
    //       },
    //       {
    //         "id": "27",
    //         "iddonhang": "64",
    //         "idsanpham": "26",
    //         "tensanpham": "Samsung Galaxy Z Fold4",
    //         "soluongsanpham": "8",
    //         "giasanpham": "290320000"
    //       }
    //     ]
    //   }
?>