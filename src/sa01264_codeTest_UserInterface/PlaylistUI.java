package sa01264_codeTest_UserInterface;

import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import sa01264_codeTest_Database.DatabaseConnector;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
/**
 * UI class allowing a song to be added at a time alongside the title and the artist name. 
 * Implements audioFunctions and carries out all the methods to showcase all the functionalities.
 * @author samita
 *
 */
public class PlaylistUI extends JFrame implements AudioFunctions {

	private JPanel contentPane= null;
	private JTable table= null;
	private JButton playButton= null;
	private JButton stopButton= null;
	private JButton loopButton= null;
	private JTextField searchField= null;
	private JLabel lblSearch= null;
	private JButton btnAddSong= null;
	private JFileChooser browser = new JFileChooser();
	private FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV Sound", "wav");
	private int returnValue=0;
	File selectedFile= null;
	private File sound= null;
	private AudioInputStream ais= null;
	private Clip clip= null;
	private JButton deleteButton= null;
	private JTextField songTitle= null;
	private JTextField songArtist= null;
	private JButton editButton= null;
	private JButton btnBrowse= null;
	Connection con;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlaylistUI frame = new PlaylistUI();
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
	public PlaylistUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 507, 251);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 44, 340, 166);
		contentPane.add(scrollPane);
		browser.setFileFilter(filter);

		table = new JTable();
		scrollPane.setViewportView(table);
		retrieve();
		// sort();
		playButton = new JButton();
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int row = table.getSelectedRow();
					String selectedItem = (String) table.getValueAt(row, 1);
					sound = new File(selectedItem);
					ais = AudioSystem.getAudioInputStream(sound);
					clip = AudioSystem.getClip();
					clip.open(ais);
					clip.start();
				} catch (Exception exp) {
					JOptionPane.showMessageDialog(null, exp);
				}
			}
		});
		playButton.setBounds(396, 27, 44, 38);
		contentPane.add(playButton);
		Image play = new ImageIcon(getClass().getResource("/play.png")).getImage();
		playButton.setIcon(new ImageIcon(play));

		stopButton = new JButton();
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clip.stop();
			}
		});
		stopButton.setBounds(353, 27, 44, 38);
		contentPane.add(stopButton);
		Image stop = new ImageIcon(getClass().getResource("/stop.png")).getImage();
		stopButton.setIcon(new ImageIcon(stop));

		loopButton = new JButton();
		loopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
		});
		loopButton.setBounds(447, 27, 46, 38);
		contentPane.add(loopButton);
		Image loop = new ImageIcon(getClass().getResource("/loop.png")).getImage();
		loopButton.setIcon(new ImageIcon(loop));

		searchField = new JTextField();
		searchField.setBounds(52, 11, 208, 20);
		contentPane.add(searchField);
		searchField.setColumns(10);
		searchField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				String query = searchField.getText();
				search(query);
			}
		});

		lblSearch = new JLabel("Search:");
		lblSearch.setBounds(10, 14, 46, 14);
		contentPane.add(lblSearch);

		btnAddSong = new JButton("");
		btnAddSong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addBtnActionPerformed(e);
				retrieve();
				songTitle.setText("");
				songArtist.setText("");
			}
		});
		btnAddSong.setBounds(449, 123, 44, 38);
		contentPane.add(btnAddSong);
		Image add = new ImageIcon(getClass().getResource("/add.png")).getImage();
		btnAddSong.setIcon(new ImageIcon(add));

		deleteButton = new JButton();
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteActionPerformed(e);
				retrieve();
				songTitle.setText("");
				songArtist.setText("");
			}
		});
		deleteButton.setBounds(449, 74, 44, 38);
		contentPane.add(deleteButton);
		Image delete = new ImageIcon(getClass().getResource("/delete.png")).getImage();
		deleteButton.setIcon(new ImageIcon(delete));

		songTitle = new JTextField();
		songTitle.setBounds(355, 123, 86, 20);
		contentPane.add(songTitle);
		songTitle.setColumns(10);

		songArtist = new JTextField();
		songArtist.setBounds(355, 154, 86, 20);
		contentPane.add(songArtist);
		songArtist.setColumns(10);

		editButton = new JButton();
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editBtnActionPerformed(e);
				retrieve();
				songTitle.setText("");
				songArtist.setText("");
			}

		});
		editButton.setBounds(449, 163, 44, 38);
		contentPane.add(editButton);
		Image edit = new ImageIcon(getClass().getResource("/edit.png")).getImage();
		editButton.setIcon(new ImageIcon(edit));

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				tableMouseClicked(evt);

			}
		});
		btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				browseBtnActionPerformed(ae);
			}
		});
		btnBrowse.setBounds(355, 89, 86, 23);
		contentPane.add(btnBrowse);
	}

	@Override
	public DefaultTableModel displayTable() {
		DefaultTableModel dm = new DefaultTableModel();
		dm.addColumn("ID");
		dm.addColumn("Audio");
		dm.addColumn("Title");
		dm.addColumn("Artist");
		String sql = "SELECT * FROM song";
		try {
			// get connection
			con = DatabaseConnector.connectDB();
			// get prepared statement
			Statement s = con.prepareStatement(sql);
			ResultSet rs = s.executeQuery(sql);
			// loop through getting all names

			while (rs.next()) {
				// get cell value for each row
				String id = rs.getString(1);
				String audio = rs.getString(2);
				String title = rs.getString(3);
				String artist = rs.getString(4);

				// add to dm row collection
				Object[] rowData = { id, audio, title, artist };
				dm.addRow(rowData);
			}
			return dm;
		} catch (Exception ex) {
			ex.printStackTrace();
			
		} finally {
			DatabaseConnector.disconnectDB();
		}
		return null;
	}

	@Override
	public void search(String query) {
		DefaultTableModel dm = displayTable();
		table.setModel(dm);
		TableRowSorter<DefaultTableModel> filter = new TableRowSorter<DefaultTableModel>(dm);
		table.setRowSorter(filter);
		filter.setRowFilter(RowFilter.regexFilter(query));
	}

	@Override
	public void sort() {
		DefaultTableModel dm = displayTable();
		table.setModel(dm);
		TableRowSorter<DefaultTableModel> sort = new TableRowSorter<DefaultTableModel>(dm);
		table.setRowSorter(sort);

	}

	@Override
	public void deleteActionPerformed(ActionEvent evt) {
		String[] options = { "Yes", "No" };
		int answ = JOptionPane.showOptionDialog(null, "Are you sure you want to delete this song??", "Delete Confirm",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if (answ == 0) {
			int index = table.getSelectedRow();
			String id = table.getValueAt(index, 0).toString();
			String sql = "DELETE FROM song WHERE ID ='" + id + "'";
			try {
				// GET CONNECTION
				con = DatabaseConnector.connectDB();
				// prepare STATEMENT
				Statement s = con.prepareStatement(sql);

				// EXECUTE
				s.execute(sql);

			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				DatabaseConnector.disconnectDB();
			}
		}
	}

	@Override
	public void tableMouseClicked(MouseEvent evt) {
		String title = table.getValueAt(table.getSelectedRow(), 2).toString();
		String artist = table.getValueAt(table.getSelectedRow(), 3).toString();
		songTitle.setText(title);
		songArtist.setText(artist);
	}

	@Override
	public void retrieve() {
		DefaultTableModel dm = displayTable();
		table.setModel(dm);
	}

	@Override
	public void editBtnActionPerformed(ActionEvent evt) {
		int index = table.getSelectedRow();
		String id = table.getValueAt(index, 0).toString();
		String sql = "UPDATE song SET Title ='" + songTitle.getText() + "',Artist='" + songArtist.getText()
				+ "' WHERE ID='" + id + "'";

		try {
			// GET COONECTION
			con = DatabaseConnector.connectDB();
			// STATEMENT
			Statement s = con.prepareStatement(sql);
			// EXECUTE
			s.execute(sql);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DatabaseConnector.disconnectDB();
		}
	}

	@Override
	public void addBtnActionPerformed(ActionEvent evt) {
		String filepath = browser.getSelectedFile().getAbsolutePath();
		String escapedFilepath = filepath.replace("\\", "\\\\");
		String sql = "INSERT INTO song(Audio,Title,Artist) VALUES ('" + escapedFilepath + "','" + songTitle.getText()
				+ "','" + songArtist.getText() + "')";
		try {
			// get connection
			con = DatabaseConnector.connectDB();
			// get prepared statement
			Statement s = con.prepareStatement(sql);
			s.execute(sql);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DatabaseConnector.disconnectDB();
		}
	}

	@Override
	public void browseBtnActionPerformed(ActionEvent evt) {
		returnValue = browser.showOpenDialog(contentPane);
		if (returnValue == browser.APPROVE_OPTION) {
			selectedFile = browser.getSelectedFile();
		}
	}

}
