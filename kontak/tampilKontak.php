<?php


	//Mendapatkan Nilai Dari Variable ID Pegawai yang ingin ditampilkan

	//SUDAH PATEN! JANGAN DIUBAH UTK ID!!!!
	$id = $_GET['id'];

	//Importing database
	require_once('koneksi.php');

	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
	$sql = "SELECT * FROM kontak WHERE id_kontak=$id";

	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);

	//Memasukkan Hasil Kedalam Array
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
			"id_kontak"=>$row['id_kontak'],
			"nama_depan"=>$row['nama_depan'],
			"nama_belakang"=>$row['nama_belakang'],
			"alamat"=>$row['alamat'],
			"jenis_kelamin"=>$row['jenis_kelamin'],
			"no_handphone"=>$row['no_handphone']

		));

	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));

	mysqli_close($con);
?>
