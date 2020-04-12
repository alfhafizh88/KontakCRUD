<?php 
    if($_SERVER['REQUEST_METHOD'] == 'POST') {
    	
		require_once('koneksi.php');

        
        $nama_depan = $_POST['nama_depan'];
        $nama_belakang = $_POST['nama_belakang'];
        $alamat = $_POST['alamat'];
        $jenis_kelamin = $_POST['jenis_kelamin'];
        $no_handphone = $_POST['no_handphone'];

        $ImageData = $_POST['foto'];
        $ImageName = $_POST['name'];
        $ImagePath = "http://192.168.43.138/kontak/upload/$ImageName";
        $insertSQL = "INSERT INTO kontak 
        (id_kontak,
        nama_depan, 
        nama_belakang, 
        alamat, 
        jenis_kelamin, 
        no_handphone, 
        foto) values
        ('',
        '$nama_depan', 
        '$nama_belakang', 
        '$alamat', 
        '$jenis_kelamin', 
        '$no_handphone', 
        'upload/$ImageName')";
        
        if(mysqli_query($con, $insertSQL)){
            file_put_contents($ImagePath,base64_decode($ImageData));
            echo "Kontak telepon telah ditambahkan.";
        }else{
            /*echo "Gagal ditambahkan.";*/
            echo $insertSQL;
        }

        mysqli_close($con);
    } else {
        echo "Please Try Again";
    }
 ?>