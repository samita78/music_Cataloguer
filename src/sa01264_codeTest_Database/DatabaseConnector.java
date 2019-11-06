package sa01264_codeTest_Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;
/**
 * Database connector class so all the classes can get connection.
 * @author samita
 *
 */
public class DatabaseConnector {

	private static Connection con = null;

	public static Connection connectDB() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sa01264_musicCataloguer", "root", "");
			return con;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Could Not Connect!");
			throw new RuntimeException(e);
		}
	}

	public static void disconnectDB() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
