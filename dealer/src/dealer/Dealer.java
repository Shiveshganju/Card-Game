package dealer;
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

class Dealer implements Runnable {
	
	private GameData gameData; //shared data 
	private int numberAnnounced = 0; //it is set when a button on GUI is pressed
		
	/* **** DO NOT MODIFY **** this label is used by the dealer to set the game status */
	public final JLabel lblGameStatus = new JLabel();   
	
	/************************** DONOT MODIFY **************************/
	public Dealer(GameData gameData) {
		this.gameData = gameData;			
		lblGameStatus.setAlignmentX(JLabel.CENTER_ALIGNMENT);		
	}
	
	/************************* WRITE CODE FOR THIS METHOD *******************/
	public void run() {
		
		/* STEP-1: write code to take a lock on gameData using lock1*/ 
		synchronized(gameData.lock1)
		{
			// dealer executes until either (or both) players declare success 
			while(!gameData.playerSuccessFlag[0]&&!gameData.playerSuccessFlag[1])
			/* STEP-2: specify condition for player1 and specify condition for player2 */ {
				// set number announced flag to false before announcing the number
				gameData.noAnnouncedFlag = false;
				
				// set checked flag of both players as false before the number is announced
				gameData.playerChanceFlag[0] = false;
				gameData.playerChanceFlag[1] = false;
		
				/* STEP-3: write code to take a lock on gameData using lock2 and wait while 
				 * no number has been pressed by the user on the GUI (See actionPerformed
				 * method of the GameGUI class 
				 * HINT: until the number is not announced the variable numberAnnounced 
				 * remains 0 (zero)
				 */
				synchronized(gameData.lock2){
					try{
						while(this.numberAnnounced==0){
							gameData.lock2.wait();
						}
					}
					catch(InterruptedException e){}
				}
				
				// STEP-4: initialize the announcedNumber in GameStat with the 
				// number pressed on GameGUI for the players to read
				    gameData.announcedNumber=this.numberAnnounced;
				// STEP-5: reset the announced number
				    this.numberAnnounced=0;
				  
				// STEP-6: communicate to the players that the number is announced
				// using one of the variables in GameData 
				   gameData.noAnnouncedFlag=true;
								
				// STEP-7: notify all the players waiting for the number to be announced 
				// by the dealer using lock1 of GameData
				   gameData.lock1.notifyAll();
								
				// STEP-8: wait using lock1 of GameData while the players haven't checked 
				// the numbers 		
				   //synchronized(gameData.lock1){
					   try{
					   while(!gameData.playerChanceFlag[0]&&!gameData.playerChanceFlag[1]){
						   gameData.lock1.wait();
					   }
					   
					   }
					   catch(InterruptedException e){}
				   //}
				   
			}
			
			// Check if Player1 has won
			/* STEP-9: specify condition */ if(gameData.playerSuccessFlag[0] && this.gameData.playerSuccessFlag[1]==false){ 
				// UNCOMMENT THE ONE LINE OF CODE WRITTEN BELOW AFTER SPECIFYING THE CONDITION
				lblGameStatus.setText("PLAYER-1 HAS WON");				
			} 
			// Check if Player2 has won
			/* STEP-10: specify condition*/if(gameData.playerSuccessFlag[1]&& gameData.playerSuccessFlag[0]==false) { 
				// UNCOMMENT THE ONE LINE OF CODE WRITTEN BELOW AFTER SPECIFYING THE CONDITION
				lblGameStatus.setText("PLAYER-2 HAS WON");				
			} 
			// Check if both Player1 and Player2 have won
			/* STEP-11: specify condition */if(gameData.playerSuccessFlag[0]&&gameData.playerSuccessFlag[1]) {
				// UNCOMMENT THE ONE LINE OF CODE WRITTEN BELOW AFTER SPECIFYING THE CONDITION
				lblGameStatus.setText("BOTH PLAYER-1 AND PLAYER-2 HAVE WON");				
			}
			
			/* UNCOMMENT THE FOLLOWING TWO LINES OF CODE ONCE YOU FINISH WRITTING 
			 * CODE FOR ALL STEPS ABOVE 
			 */
			gameData.gameCompleteFlag = true; // Set the complete flag to true 
			gameData.lock1.notifyAll(); // If at all any player is waiting			
		}		
	}

	public void setAnnouncedNumber(int i) {
		this.numberAnnounced = i;	
	}
}