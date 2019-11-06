package sa01264_codeTest;

import static org.junit.Assert.*;


import org.junit.Test;

import sa01264_codeTest_class.Song;

public class SongTest {

	Song s1= new Song ("C:UserssamitasongsHero - Mariah Carey (Lyrics)","Hero","Mariah Carey");
	Song s2= new Song ("C:UserssamitasongsRachel Platten - Fight Song (Official Video)","Fight song","Rachel Platten");

	@Test
	public void testSong() {
		assertEquals("C:UserssamitasongsHero - Mariah Carey (Lyrics)",s1.getAudio());
		assertEquals("Hero",s1.getTitle());
		assertEquals("Mariah Carey",s1.getArtist());
	}

}
