package sa01264_codeTest_class;

import sa01264_codeTest_class_DAO.DAOFactory;
import sa01264_codeTest_class_DAO.PlaylistDAO;
/**
 * Main method to show what has been added to the database. It will output everything that is stored in the database. 
 * Initially there won't be any songs so it would display nothing.
 * @author samita
 *
 */
public class Main {
	public static void main(String[] args) {
		PlaylistDAO songDAO = null;
		try {
			songDAO = DAOFactory.getSongDAO();
			System.out.println("what's in the database rn?");
			songDAO.showAllSongs();
			songDAO.showAllAlbumSongs();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (songDAO != null) {
				songDAO.closeConnection();
			}
		}
	}
}
