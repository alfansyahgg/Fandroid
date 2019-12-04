<?php

include('koneksi.php');

$nama 		= $_POST['nama'];
$noinduk	= $_POST['noinduk'];
$alamat		= $_POST['alamat'];
$hobi 		= $_POST['hobi'];

if(!empty($nama) && !empty($noinduk)){

	$sql = "UPDATE tb_siswa set nama='$nama', alamat='$alamat', hobi='$hobi' WHERE noinduk='$noinduk' ";

	$query = mysqli_query($conn,$sql);

	if(mysqli_affected_rows($conn) > 0){
		$data['status'] = true;
		$data['result']	= "Berhasil";
	}else{
		$data['status'] = false;
		$data['result']	= "Gagal";
	}

}else{
	$data['status'] = false;
	$data['result']	= "Gagal, Nomor Induk dan Nama tidak boleh kosong!";
}


print_r(json_encode($data));




?>