package sa01264_codeTest_UserInterface;

import java.awt.EventQueue;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import sa01264_codeTest_Database.DatabaseConnector;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JLabel;
/**
 * UI class allowing multiple songs to be added at once. 
 * Implements audioFunctions and carries out all the methods to showcase all the functionalities.
 * @author samita
 *
 */
public class AlbumUI extends JFrame implements AudioFunctions {

	private JPanel contentPane= null;
	private JButton btnAdd= null;
	private JTable table= null;
	private JFileChooser browser = new JFileChooser();
	private FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV Sound", "wav");
	int returnValue=0;
	private File[] files= null;
	private File sound= null;
	private AudioInputStream ais= null;
	private Clip clip= null;
	private JButton playButton= null;
	private JButton stopButton= null;
	private JButton loopButton= null;
	private JButton deleteButton= null;
	private JButton browseButton= null;
	private JLabel lblSearch= null;
	private JTextField searchField= null;
	private JTextField songTitle= null;
	private JButton editButton= null;
	Connection con;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlbumUI frame = new AlbumUI();
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
	public AlbumUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 423, 280);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		browser.setFileFilter(filter);
		browser.setMultiSelectionEnabled(true);

		contentPane.setLayout(null);
		browseButton = new JButton("Browse");
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				browseBtnActionPerformed(ae);
			}
		});
		browseButton.setBounds(142, 11, 89, 23);
		getContentPane().add(browseButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 54, 299, 181);
		contentPane.add(scrollPane);
		table = new JTable();
		scrollPane.setViewportView(table);

		retrieve();

		btnAdd = new JButton("Add Songs");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				addBtnActionPerformed(ae);
				retrieve();
			}
		});
		btnAdd.setBounds(241, 11, 102, 23);
		getContentPane().add(btnAdd);
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
		playButton.setBounds(312, 54, 44, 38);
		contentPane.add(playButton);
		Image play = new ImageIcon(getClass().getResource("/play.png")).getImage();
		playButton.setIcon(new ImageIcon(play));

		stopButton = new JButton();
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clip.stop();
			}
		});
		stopButton.setBounds(353, 54, 44, 38);
		contentPane.add(stopButton);
		Image stop = new ImageIcon(getClass().getResource("/stop.png")).getImage();
		stopButton.setIcon(new ImageIcon(stop));

		loopButton = new JButton();
		loopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
		});
		loopButton.setBounds(312, 103, 44, 38);
		contentPane.add(loopButton);
		Image loop = new ImageIcon(getClass().getResource("/loop.png")).getImage();
		loopButton.setIcon(new ImageIcon(loop));

		deleteButton = new JButton();
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteActionPerformed(e);
				retrieve();
			}
		});
		deleteButton.setBounds(353, 103, 44, 38);
		contentPane.add(deleteButton);
		Image delete = new ImageIcon(getClass().getResource("/delete.png")).getImage();
		deleteButton.setIcon(new ImageIcon(delete));

		searchField = new JTextField();
		searchField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				String query = searchField.getText();
				search(query);
			}
		});
		searchField.setBounds(46, 12, 86, 20);
		contentPane.add(searchField);
		searchField.setColumns(10);

		lblSearch = new JLabel("Search:");
		lblSearch.setBounds(10, 15, 46, 14);
		contentPane.add(lblSearch);

		songTitle = new JTextField();
		songTitle.setBounds(319, 161, 86, 20);
		contentPane.add(songTitle);
		songTitle.setColumns(10);

		editButton = new JButton();
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editBtnActionPerformed(e);
				songTitle.setText("");
				retrieve();
			}
		});
		editButton.setBounds(329, 192, 44, 38);
		contentPane.add(editButton);
		Image edit = new ImageIcon(getClass().getResource("/edit.png")).getImage();
		editButton.setIcon(new ImageIcon(edit));

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				tableMouseClicked(evt);
			}
		});
	}

	@Override
	public void tableMouseClicked(MouseEvent evt) {
		String title = table.getValueAt(table.getSelectedRow(), 2).toString();
		songTitle.setText(title);
	}

	@Override
	public DefaultTableModel displayTable() {
		DefaultTableModel dm = new DefaultTableModel();
		dm.addColumn("ID");
		dm.addColumn("Path");
		dm.addColumn("Title");

		String sql = "SELECT * FROM Album";
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

				String path = rs.getString(2);
				String title = rs.getString(3);
				// add to dm row collection
				Object[] rowData = { id, path, title };
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
	public void deleteActionPerformed(ActionEvent evt) {
		String[] options = { "Yes", "No" };
		int answ = JOptionPane.showOptionDialog(null, "Are you sure you want to delete this song??", "Delete Confirm",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if (answ == 0) {
			int index = table.getSelectedRow();
			String id = table.getValueAt(index, 0).toString();
			String sql = "DELETE FROM Album WHERE ID ='" + id + "'";
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
		} else {
			JOptionPane.showMessageDialog(null, "Not Deleted");
		}
	}

	@Override
	public void retrieve() {
		DefaultTableModel dm = displayTable();
		table.setModel(dm);
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
	public void editBtnActionPerformed(ActionEvent evt) {
		int index = table.getSelectedRow();
		String id = table.getValueAt(index, 0).toString();
		String sql = "UPDATE Album SET Title ='" + songTitle.getText() + "' WHERE ID='" + id + "'";
		try {
			// GET CONECTION
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
		for (int i = 0; i < files.length; i++) {
			String path = files[i].getAbsolutePath().toString().replace("\\", "\\\\");
			String title = files[i].getName();
			String sql = "INSERT INTO Album(Path,Title) VALUES ('" + path + "','" + title + "')";
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
	}

	@Override
	public void browseBtnActionPerformed(ActionEvent evt) {
		returnValue = browser.showOpenDialog(contentPane);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			files = browser.getSelectedFiles();
		}
	}
}
