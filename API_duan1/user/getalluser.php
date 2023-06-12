<?php
    include "connect.php";
    
    $query = "SELECT * FROM user";
    $data = mysqli_query($conn, $query);
    $mangdulieu = array();

    while($row = mysqli_fetch_assoc($data)) {
        array_push($mangdulieu, new user(
            $row['id'],
            $row['username'],
            $row['email'],
            $row['pass'],
            $row['phone']
        ));
    }

    echo json_encode($mangdulieu);

    class user {
        function user($id, $username,$email, $pass, $phone) {
            $this->id = $id;
            $this->username = $username;
            $this->email = $email;
            $this->pass = $pass;
            $this->phone = $phone;
        }
    }
?>