package sa01264_codeTest_UserInterface;
/**
 * UI class allowing the addition of song to a playlist
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import sa01264_codeTest_Database.DatabaseConnector;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class AddSongUI extends JFrame {

	private JPanel contentPane= null;
	private JTextField songTitle= null;
	private JTextField songArtist= null;
	private JFileChooser browser = new JFileChooser();
	private FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV Sound", "wav");
	int returnValue;
	File selectedFile;
	Connection con;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddSongUI frame = new AddSongUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddSongUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 309, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		browser.setFileFilter(filter);
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				returnValue = browser.showOpenDialog(contentPane);
				if (returnValue == browser.APPROVE_OPTION) {
					selectedFile = browser.getSelectedFile();
				}
			}
		});
		btnBrowse.setBounds(84, 11, 116, 23);
		contentPane.add(btnBrowse);

		songTitle = new JTextField();
		songTitle.setBounds(84, 56, 116, 20);
		contentPane.add(songTitle);
		songTitle.setColumns(10);

		songArtist = new JTextField();
		songArtist.setBounds(84, 105, 116, 20);
		contentPane.add(songArtist);
		songArtist.setColumns(10);

		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setBounds(28, 59, 46, 14);
		contentPane.add(lblTitle);

		JLabel lblArtist = new JLabel("Artist:");
		lblArtist.setBounds(28, 108, 46, 14);
		contentPane.add(lblArtist);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String filepath = browser.getSelectedFile().getAbsolutePath();
				String escapedFilepath = filepath.replace("\\", "\\\\");

				String sql = "INSERT INTO song(Audio,Title,Artist) VALUES ('" + escapedFilepath + "','"
						+ songTitle.getText() + "','" + songArtist.getText() + "')";
				try {
					// get connection
					con = DatabaseConnector.connectDB();
					// get prepared statement
					Statement s = con.prepareStatement(sql);
					s.execute(sql);
					songTitle.setText("");
					songArtist.setText("");
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					DatabaseConnector.disconnectDB();
				}
			}
		});
		btnAdd.setBounds(94, 136, 89, 23);
		contentPane.add(btnAdd);

		JButton btnGoToPlaylist = new JButton("Go to playlist");
		btnGoToPlaylist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlaylistUI playlist = new PlaylistUI();
				playlist.setVisible(true);
			}
		});
		btnGoToPlaylist.setBounds(84, 201, 116, 23);
		contentPane.add(btnGoToPlaylist);

	}

}
