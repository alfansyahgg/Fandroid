<?php 
$hostname = '127.0.0.1';
$username = 'root';
$password = '123';
$database = 'db_siswa';

$conn = mysqli_connect($hostname,$username,$password,$database);
if(!$conn){
	echo "gagal";
}



?>