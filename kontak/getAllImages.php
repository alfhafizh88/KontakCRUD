<?php
	require_once('koneksi.php');
	
	$sql = "SELECT * from kontak";
	
	$res = mysqli_query($con,$sql);
	
	$result = array();
	
	while($row = mysqli_fetch_array($res)){
		array_push($result,array('foto'=>$row['foto'],
			'nama_depan'=>$row['nama_depan']

	));
	}
	
	echo json_encode(array("result"=>$result));
	
	mysqli_close($con);
?>