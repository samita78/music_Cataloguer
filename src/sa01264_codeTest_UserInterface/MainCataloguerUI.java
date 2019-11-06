package sa01264_codeTest_UserInterface;

import java.awt.Color;

import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import sa01264_codeTest_UserInterface.PlaylistUI;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class MainCataloguerUI {
	private JFrame frame= null;
	private JButton btnAlbum= null;
	private JLabel lblNewLabel= null;
	private JButton btnPlaylist= null;
	private JButton btnAddSong= null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainCataloguerUI window = new MainCataloguerUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainCataloguerUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);

		lblNewLabel = new JLabel();
		Image music = new ImageIcon(getClass().getResource("/musicpic.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(music));
		lblNewLabel.setBounds(-10, 22, 444, 106);
		frame.getContentPane().add(lblNewLabel);

		btnPlaylist = new JButton("Playlist");
		btnPlaylist.setBounds(180, 139, 89, 23);
		frame.getContentPane().add(btnPlaylist);
		btnPlaylist.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlaylistUI playList = new PlaylistUI();
				playList.setVisible(true);
			}
		});

		btnAddSong = new JButton("Add Song");
		btnAddSong.setBounds(180, 165, 89, 23);
		frame.getContentPane().add(btnAddSong);

		btnAlbum = new JButton("Album");
		btnAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				AlbumUI album = new AlbumUI();
				album.setVisible(true);
			}
		});
		btnAlbum.setBounds(180, 199, 89, 23);
		frame.getContentPane().add(btnAlbum);
		btnAddSong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddSongUI add = new AddSongUI();
				add.setVisible(true);
			}
		});
	}
}
