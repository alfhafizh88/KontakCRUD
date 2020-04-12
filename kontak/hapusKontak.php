<?php

 //Mendapatkan Nilai ID
 $id_kontak = $_GET['id'];

 //Import File Koneksi Database
 require_once('koneksi.php');

 //Membuat SQL Query
 $sql = "DELETE FROM kontak WHERE id_kontak=$id_kontak;";


 //Menghapus Nilai pada Database
 if(mysqli_query($con,$sql)){
 echo 'Hapus Berhasil!';
 }else{
 echo 'Hapus Gagal!';
 }

 mysqli_close($con);
 ?>
