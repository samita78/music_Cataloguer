package sa01264_codeTest;



import static org.junit.Assert.*;



import org.junit.Test;

import sa01264_codeTest_class_DAO.DAOFactory;
import sa01264_codeTest_class_DAO.Playlist;
import sa01264_codeTest_class_DAO.PlaylistDAO;

;

public class DAOFactoryTest {
	
	
	PlaylistDAO songDAO=new Playlist();
	
	@Test
	public void testGetSongDAO() {
       assertNotSame(songDAO, DAOFactory.getSongDAO());
	}

}
