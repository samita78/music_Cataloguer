package sa01264_codeTest_class;
/**
 * Class holding the song which comprise together to make a playlist.
 * @author samita
 *
 */
public class Song {

	private String audio= null;
	private String title= null;
	private String artist= null;

	public Song(String audio, String title, String artist) {
		this.audio = audio;
		this.title = title;
		this.artist = artist;
	}

	public String getAudio() {
		return this.audio;
	}

	public String getTitle() {
		return this.title;
	}

	public String getArtist() {
		return this.artist;
	}

}
