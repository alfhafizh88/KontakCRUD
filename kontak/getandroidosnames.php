<?php
require_once('koneksi.php');
$sql = "SELECT * FROM kontak";
$r = mysqli_query($con,$sql);
$result = array();
	while($res = mysqli_fetch_array($r)){
		array_push($result,array(
		"AndroidNames"=>$res['nama_depan'],
		"ImagePath"=>$res['foto']
		));
	}
echo json_encode(array("result"=>$result));
mysqli_close($con);
?>