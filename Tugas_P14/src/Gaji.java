import java.util.InputMismatchException;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

public class Gaji implements PTABC {
	//static Scanner scanner;
	static Connection conn;
	String url = "jdbc:mysql://localhost:3306/tugasp14";
	
	public int noPegawai;
	public int gajiPokok = 0; 
    public int jumlahHariMasuk = 0;
    public int jumlahHariAbsen = 0;
    public int totalGaji = 0;
    public int potongan = 0;
    public String namaPegawai, jabatan;
    Scanner terimaInput = new Scanner (System.in);

    public void lihatdata() throws SQLException {
		String text1 = "\n===Daftar Seluruh Data Pegawai===";
		System.out.println(text1.toUpperCase());
						
		String sql ="SELECT * FROM tabelptabc";
		conn = DriverManager.getConnection(url,"root","");
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);
		
		while(result.next()){
			System.out.print("\nNomor pegawai\t  : ");
            System.out.print(result.getInt("nopegawai"));
            System.out.print("\nNama pegawai\t  : ");
            System.out.print(result.getString("nama"));
            System.out.print("\nJabatan\t\t  : ");
            System.out.print(result.getString("jabatan"));
            System.out.print("\nGaji pokok\t  : ");
            System.out.print(result.getInt("gajipokok"));
            System.out.print("\nJumlah hari masuk : ");
            System.out.print(result.getInt("jumlahharmasuk"));
            System.out.print("\nTotal gaji\t  : ");
            System.out.print(result.getInt("total"));
            System.out.print("\n");
		}
	}
    
    public void tambahdata() throws SQLException {
    	String text2 = "\n===Tambah Data Pegawai===";
		System.out.println(text2.toUpperCase());
		
    	try {
	        // NoPegawai
	    	System.out.print("Masukkan nomor pegawai: ");
	        noPegawai = terimaInput.nextInt();
	        terimaInput.nextLine();
	
	        // NamaPegawai
	        System.out.print("\nMasukkan nama pegawai: ");
	        namaPegawai = terimaInput.nextLine(); 

	        // PilihJabatan
	        int pilihJabatan;
	        System.out.println("\n--------------------------------------");
            System.out.println("====== Tingkatan Jabatan ======\n1. Direktur \n2. Menejer \n3. Karyawan \n4. CS \n");
            System.out.println("Input Jabatan: ");
            pilihJabatan = terimaInput.nextInt();
	
	        // Jabatan
	        if(pilihJabatan==1){
                jabatan="DIREKTUR";
                gajiPokok=20000000;
            }else if(pilihJabatan==2){
                jabatan="MENEJER";
                gajiPokok=10000000;
            }else if(pilihJabatan==3){
                jabatan="KARYAWAN";
                gajiPokok=6000000;
            }else if(pilihJabatan==4){
                jabatan="CS";
                gajiPokok =3000000;
            }
	        else{
	            System.out.println("\nGaji pokok tidak tersedia");
	        }
	        System.out.println("=> Jabatan: " +jabatan);
            System.out.println(jabatan.toLowerCase().concat(" / ").concat(jabatan.toUpperCase()));
            System.out.println("\n=> Gaji Pokok : Rp " +gajiPokok);
	        
	        // JumlahHariMasuk
	        System.out.print("\n" + "Inputkan hari masuk kerja pada bulan ini : ");
            jumlahHariMasuk= terimaInput.nextInt();
            jumlahHariAbsen=30-jumlahHariMasuk;
	        System.out.println("Jumlah hari masuk: " + jumlahHariMasuk);
	        
	        // Potongan
	        potongan = jumlahHariAbsen*100000;
            System.out.println("=> Potongan : Rp " +potongan);
	        
	        // TotalGaji
	        totalGaji = gajiPokok - (int) potongan;
	        System.out.println("Total gaji: Rp" + totalGaji);
	        System.out.println("");
	        
	        conn = DriverManager.getConnection(url,"root","");
            String sql = "INSERT INTO tabelptabc (nopegawai, nama, jabatan, gajipokok, jumlahmasuk,potongan, total) VALUES ('"+noPegawai+"','"+namaPegawai+"','"+jabatan+"','"+gajiPokok+"','"+jumlahHariMasuk+"','"+potongan+"','"+totalGaji+"')";
	        Statement statement = conn.createStatement();
	        statement.execute(sql);
	        System.out.println("Berhasil input data");
    	}
    	catch (SQLException e) {
	        System.err.println("Terjadi kesalahan input data");
	    } 
    	catch (InputMismatchException e) {
	    	System.err.println("Inputlah dengan angka saja");
	   	}
	} 
	public void ubahdata() throws SQLException{
		String text3 = "\n===Ubah Data Pegawai===";
		System.out.println(text3.toUpperCase());
		
		try {
            lihatdata();
            System.out.print("\nMasukkan Nomor Pegawai yang akan di ubah atau update : ");
            Integer noPegawai = Integer.parseInt(terimaInput.nextLine());
            
            conn = DriverManager.getConnection(url,"root","");
            String sql = "SELECT * FROM tabelptabc WHERE nopegawai = " +noPegawai;
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            if(result.next()){
                
                System.out.print("Nama baru ["+result.getString("nama")+"]\t: ");
                String namaPegawai = terimaInput.nextLine();
                   
                sql = "UPDATE ptabc SET nama='"+namaPegawai+"' WHERE nopegawai='"+noPegawai+"'";
                System.out.println(sql);

                if(statement.executeUpdate(sql) > 0){
                    System.out.println("Berhasil memperbaharui data pegawai (Nomor "+noPegawai+")");
                }
            }
            statement.close();        
        } 
		catch (SQLException e) {
        	System.err.println("Terjadi kesalahan dalam mengedit data");
            System.err.println(e.getMessage());
        }
	}
	
	public void hapusdata() {
		String text4 = "\n===Hapus Data Pegawai===";
		System.out.println(text4.toUpperCase());
		
		try{
	        lihatdata();
	        System.out.print("\nKetik Nomor Pegawai yang akan Anda Hapus : ");
	        Integer noPegawai= Integer.parseInt(terimaInput.nextLine());
	        
	        conn = DriverManager.getConnection(url,"root","");
            String sql = "DELETE FROM tabelptabc WHERE nopegawai = "+ noPegawai;
	        Statement statement = conn.createStatement();
	        ResultSet result = statement.executeQuery(sql);
	        
	        if(statement.executeUpdate(sql) > 0){
	            System.out.println("Berhasil menghapus data pegawai (Nomor "+noPegawai+")");
	        }
	   }
		catch(SQLException e){
	        System.out.println("Terjadi kesalahan dalam menghapus data");
	    }
	}
	
	public void caridata () throws SQLException {
		String text5 = "\n===Cari Data Pegawai===";
		System.out.println(text5.toUpperCase());
				
		System.out.print("Masukkan Nama Pegawai : ");    
		String keyword = terimaInput.nextLine();
		
		conn = DriverManager.getConnection(url,"root","");
        String sql = "SELECT * FROM tabelptabc WHERE nama LIKE '%"+keyword+"%'";
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql); 
                
        while(result.next()){
        	System.out.print("\nNomor pegawai\t  : ");
            System.out.print(result.getInt("nopegawai"));
            System.out.print("\nNama pegawai\t  : ");
            System.out.print(result.getString("nama"));
            System.out.print("\nJabatan\t\t  : ");
            System.out.print(result.getString("jabatan"));
            System.out.print("\nGaji pokok\t  : ");
            System.out.print(result.getInt("gajipokok"));
            System.out.print("\nJumlah hari masuk : ");
            System.out.print(result.getInt("jumlahmasuk"));
            System.out.print("\nTotal gaji\t  : ");
            System.out.print(result.getInt("totalgaji"));
            System.out.print("\n");
        }
	}   
}
