package sa01264_codeTest_class_DAO;

import java.util.List;
import sa01264_codeTest_class.AlbumSong;
import sa01264_codeTest_class.Song;

public interface PlaylistDAO {

	public List<Song> getSongs();

	public void writeSong(Song song);

	public void writeAlbumSong(AlbumSong song);

	public void showAllSongs();

	public void openConnection();

	public void closeConnection();

	public List<AlbumSong> getAlbumSongs();

	public void showAllAlbumSongs();

}
