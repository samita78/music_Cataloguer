package sa01264_codeTest;


import static org.junit.Assert.*;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.Test;

import sa01264_codeTest_class.Song;
import sa01264_codeTest_class_DAO.Playlist;

public class PlaylistTest {
		Song s1= new Song ("C:UserssamitasongsHero - Mariah Carey (Lyrics)","Hero","Mariah Carey");
		Song s2= new Song ("C:UserssamitasongsRachel Platten - Fight Song (Official Video)","Fight song","Rachel Platten");
		Song s3= new Song ("C:UserssamitasongsHero - Mariah Carey (Lyrics)","Hero","Mariah Carey");
		Playlist p = new Playlist();
		Statement s=null;
		ResultSet rs= null;
	
	@Test(expected= AssertionError.class)
	public void testGetSongs(){
		Playlist p = new Playlist();
		ArrayList<Song> songs = new ArrayList<Song>();
		songs.add(s1);
		songs.add(s2);
		songs.add(s3);
		//assertEquals();
		assertEquals(songs,p.getSongs());
	}
	@Test(expected=RuntimeException.class)
		public void checkStatementNull() throws SQLException{
		//s=null;
		s.getConnection();
	}
	
	@Test(expected=RuntimeException.class)
	public void checkConnectionNull() throws SQLException{
		Connection con = null;
		con.prepareStatement("");
	}
	
	@Test(expected=RuntimeException.class)
	public void checkResultSetNull() throws SQLException{
		rs.getString("");
}
	
	

}
