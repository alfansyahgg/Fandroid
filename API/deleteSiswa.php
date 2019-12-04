<?php

include('koneksi.php');

$noinduk = $_POST['noinduk'];

if(!empty($noinduk)){
	$sql = "DELETE FROM tb_siswa WHERE noinduk='$noinduk' ";

	$query = mysqli_query($conn,$sql);

	$data['status'] = true;
	$data['result'] = 'Berhasil';
}else{
	$data['status'] = false;
	$data['result'] = 'Gagal';
}

print_r(json_encode($data));


?>