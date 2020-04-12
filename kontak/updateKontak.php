<?php
 
	if($_SERVER['REQUEST_METHOD']=='POST'){
		//Mendapatkan Nilai Dari Variable
		$id_kontak = $_POST['id_kontak'];
		$nama_depan = $_POST['nama_depan'];
		$nama_belakang = $_POST['nama_belakang'];
		$alamat = $_POST['alamat'];
		$jenis_kelamin = $_POST['jenis_kelamin'];
		$no_handphone = $_POST['no_handphone'];
		

		//import file koneksi database
		require_once('koneksi.php');

		//Membuat SQL Query
		$sql = "UPDATE kontak SET nama_depan = '$nama_depan', nama_belakang = '$nama_belakang', alamat = '$alamat', jenis_kelamin = '$jenis_kelamin', no_handphone = '$no_handphone' WHERE id_kontak = $id_kontak;";

		//Meng-update Database
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Data Kontak';
		}else{
			echo 'Gagal Update Data Kontak';
		}

		mysqli_close($con);
	}
?>
