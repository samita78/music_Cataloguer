package sa01264_codeTest_class_DAO;

public class DAOFactory {
	private static final PlaylistDAO songDAO = new Playlist();

	public static PlaylistDAO getSongDAO() {
		return DAOFactory.songDAO;
	}
}
