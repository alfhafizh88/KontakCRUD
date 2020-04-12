<?php

	//Import File Koneksi Database
	require_once('koneksi.php');

	//Membuat SQL Query
	$sql = "SELECT * FROM kontak order by nama_depan";

	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);

	//Membuat Array Kosong
	$result = array();

	while($row = mysqli_fetch_array($r)){

		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat
		array_push($result,array(
			"id_kontak"=>$row['id_kontak'],
			"nama_depan"=>$row['nama_depan'],
			"nama_belakang"=>$row['nama_belakang']

		));
	}

	//Menampilkan Array dalam Format JSON
	echo json_encode(array('result'=>$result));

	mysqli_close($con);
?>
