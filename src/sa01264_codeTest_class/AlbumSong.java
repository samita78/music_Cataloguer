package sa01264_codeTest_class;
/**
 * Class that stores song belonging to an album
 * @author samita
 *
 */
public class AlbumSong {
	
	private String path= null;
	private String title= null;

	public AlbumSong(String path, String title) {
		super();
		this.path = path;
		this.title = title;
	}

	public String getPath() {
		return this.path;
	}

	public String getTitle() {
		return this.title;
	}
}
