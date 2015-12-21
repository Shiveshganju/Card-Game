package dealer;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;



/**** THE INSTANCE OF THIS CLASS IS USED AS A MEANS OF COMMUNICATION *
 * AND SYNCHRONIZTION BETWEEN THE PLAYER AND DEALER THREADS ********** 
 * DONOT MODIFY THE CODE ****/
class GameData {
	public int announcedNumber = 0;	 
	public boolean gameCompleteFlag = false;	
	public boolean noAnnouncedFlag = false;
	public boolean[] playerSuccessFlag = new boolean[2];
	public boolean[] playerChanceFlag = new boolean[2];
	
	public Object lock1 = new Object();
	public Object lock2 = new Object();
}