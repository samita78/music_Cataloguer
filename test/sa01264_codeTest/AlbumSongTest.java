package sa01264_codeTest;



import static org.junit.Assert.*;


import org.junit.Test;

import sa01264_codeTest_class.AlbumSong;

public class AlbumSongTest {

	AlbumSong as1 = new AlbumSong("C:UserssamitasongsHero - Mariah Carey (Lyrics).wav","Hero - Mariah Carey (Lyrics).wav");
	@Test
	public void testConstructor() {
	
		assertEquals("C:UserssamitasongsHero - Mariah Carey (Lyrics).wav",as1.getPath());
		assertEquals("Hero - Mariah Carey (Lyrics).wav",as1.getTitle());

	}

}
