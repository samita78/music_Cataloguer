package sa01264_codeTest_UserInterface;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.table.DefaultTableModel;
/**
 * interface defining the functionalities list of songs can have- whether thats in a playlist or an album.
 * @author samita
 *
 */
public interface AudioFunctions {

	public DefaultTableModel displayTable();

	public void search(String query);

	public void sort();

	public void deleteActionPerformed(ActionEvent evt);

	public void tableMouseClicked(MouseEvent evt);

	public void retrieve();

	public void editBtnActionPerformed(ActionEvent evt);

	public void addBtnActionPerformed(ActionEvent evt);

	public void browseBtnActionPerformed(ActionEvent evt);
}
