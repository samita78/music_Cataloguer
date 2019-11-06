package sa01264_codeTest_class_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sa01264_codeTest_Database.DatabaseConnector;
import sa01264_codeTest_class.AlbumSong;
import sa01264_codeTest_class.Song;

public class Playlist implements PlaylistDAO {

	Connection con;
	Statement statement;

	public Playlist() {
		super();
		this.con = null;
		this.statement = null;
		this.openConnection();
	}

	@Override
	public void openConnection() {
		try {
			if (this.con == null || this.con.isClosed()) {

				this.con = DatabaseConnector.connectDB();
			}
			if (this.statement == null || this.statement.isClosed()) {
				this.statement = this.con.createStatement();
			}

		} catch (SQLException ex) {
			System.out.println("ERROR - Failed to create a connection to the database");
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<Song> getSongs() {
		ArrayList<Song> songs = new ArrayList<Song>();
		try {
			String query = "SELECT * FROM song";
			ResultSet results = this.statement.executeQuery(query);
			while (results.next()) {
				String audio = results.getString("Audio");
				String title = results.getString("Title");
				String artist = results.getString("artist");
				songs.add(new Song(audio, title, artist));
			}
		} catch (SQLException e) {
			System.out.println("SQLException happened while retrieving records- abort programmme");
			throw new RuntimeException(e);
		}

		return songs;
	}

	@Override
	public void closeConnection() {
		try {

			if (this.statement != null) {
				this.statement.close();
			}
			if (this.con != null) {
				this.con.close();
			}
			System.out.println("Closed the connection to the database");
		} catch (Exception e) {
			System.out.print("ERROR-Failed to close the connection to the database");
			throw new RuntimeException(e);
		}
	}

	@Override
	public void writeSong(Song song) {

		try {
			
			PreparedStatement preparedStatement = this.con
					.prepareStatement("INSERT INTO song (audio, title, artist) VALUES (?, ?, ?)");
			preparedStatement.setString(1, song.getAudio());
			preparedStatement.setString(2, song.getTitle());
			preparedStatement.setString(3, song.getArtist());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLException happened while writing a book- abort programmme");
			throw new RuntimeException(e);
		}
	}

	@Override
	public void showAllSongs() {
		List<Song> songs = getSongs();
		Iterator<Song> iter = songs.iterator();
		Song tmpBook;
		while (iter.hasNext()) {
			tmpBook = iter.next();
			System.out.println("---- " + tmpBook.getAudio() + " ----");
			System.out.println("Author: " + tmpBook.getArtist());
			System.out.println("Title: " + tmpBook.getTitle() + "\n");
		}
	}

	@Override
	public List<AlbumSong> getAlbumSongs() {
		ArrayList<AlbumSong> songs = new ArrayList<AlbumSong>();
		try {
			String query = "SELECT * FROM Album";
			ResultSet results = this.statement.executeQuery(query);
			while (results.next()) {
				String path = results.getString("Path");
				String title = results.getString("Title");
				songs.add(new AlbumSong(path, title));
			}
		} catch (SQLException e) {
			System.out.println("SQLException happened while retrieving records- abort programmme");
			throw new RuntimeException(e);
		}
		return songs;
	}

	@Override
	public void showAllAlbumSongs() {
		List<AlbumSong> songs = getAlbumSongs();
		Iterator<AlbumSong> iter = songs.iterator();
		// While there are still results...
		AlbumSong tmpBook;
		while (iter.hasNext()) {
			tmpBook = iter.next();
			// Prints results to console
			System.out.println("Path: " + tmpBook.getPath());
			System.out.println("Title: " + tmpBook.getTitle() + "\n");
		}
	}

	@Override
	public void writeAlbumSong(AlbumSong song) {
		try {
			// Prepared statements allow us to use variables in them more
			// efficiently
			PreparedStatement preparedStatement = this.con
					.prepareStatement("INSERT INTO Album (Path, Title) VALUES (?, ?)");
			preparedStatement.setString(1, song.getPath());
			preparedStatement.setString(2, song.getTitle());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLException happened while writing a book- abort programmme");
			throw new RuntimeException(e);
		}
	}

}
