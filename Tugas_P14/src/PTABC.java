import java.sql.*;

public interface PTABC {
	void lihatdata() throws SQLException;
	void tambahdata() throws SQLException;
	void ubahdata() throws SQLException;
	void hapusdata();
	void caridata() throws SQLException;
}
