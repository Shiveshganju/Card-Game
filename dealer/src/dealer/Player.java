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

class Player implements Runnable {

	private int id;							// player id [0 or 1]
	private GameData gameData;				// shared object
	private JPanel playerTicketPanel;		// GUI component
	private JButton[] btnOnTicket;			// buttons on player ticket
	private int totalNumbersFound;    		// total numbers found
	private final static int MAXNO = 6;		// maximum numbers on player ticket
	
	// stores the numbers on the player ticket
	private int[] ticket = new int[MAXNO];
			
	/****************** DO NOT MODIFY *****************/
	public Player(GameData gameData, int id) { 
		
		this.id = id; 		
		this.gameData = gameData;	
		this.totalNumbersFound = 0;
		
		// randomly generate six numbers and store them in the lstTicket
		for(int i = 0; i < MAXNO; i++) {
			int p = randInt(i*5 + 1, (i+1) * 5);
			ticket[i] = p;
		}
		
		// initialize player panel
		playerTicketPanel = new JPanel();
		// set playerPanel layout
		playerTicketPanel.setLayout(new GridLayout(1,6));
		// create an array of six buttons 
		btnOnTicket = new JButton[MAXNO];
		
		// initialize the buttons on ticket and add them to playerPanel
		for(int i = 0; i < MAXNO; i++) {
			btnOnTicket[i] = new JButton(String.valueOf(ticket[i]));
			btnOnTicket[i].setEnabled(false);
			playerTicketPanel.add(btnOnTicket[i]);
		}
	}
	
	/******************** DO NOT MODIFY **********************/
	private static int randInt(int min, int max) {	//method to generate random numbers
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	/************* WRITE CODE FOR THIS METHOD ****************/
	public void run() {
		/* STEP-12: write code to take a lock on gameData using lock1 */
synchronized(gameData.lock1) 
		{			
			// both players execute while the game is not complete
			/* STEP-13: Specify condition */
while(!gameData.gameCompleteFlag) {
			
				// STEP-14: both players should wait using lock1 of GameData until a number 
				// is announced by the dealer or its not the chance of the player
				try{
				while(gameData.announcedNumber==0||gameData.playerChanceFlag[id])				
				gameData.lock1.wait();
				}
				catch(InterruptedException e){}
				// Its important to check this condition again because it is possible that
				// one player may have found all the numbers when the other was waiting
				if(!gameData.gameCompleteFlag) {					
					
					// STEP-15: Check if the announced number is on the player's ticket
					// if the number is found, the player increments the totalNumbersFound
					// and set the back ground color of the button to GREEN using the following statement
					for(int i=0;i<this.MAXNO;i++){
						if(ticket[i]==gameData.announcedNumber){
							this.btnOnTicket[i].setBackground(Color.GREEN);
							this.totalNumbersFound++;
							break;
						}
					}
					
					// this.btnOnTicket[i].setBackground(Color.GREEN)
										
					// STEP-16: player checks if it has won the game i.e., it has found all numbers
					// then it should report success
										if(this.totalNumbersFound==6){
											gameData.playerSuccessFlag[id]=true;
										}
					// player sets its chance flag 
					gameData.playerChanceFlag[id] = true;
					
					// STEP-17: notify all others waiting on lock1 of GameData
					gameData.lock1.notifyAll();
				}
			}
		}
	}

	public JPanel getPlayerTicketPanel() {		
		return playerTicketPanel;
	}
}