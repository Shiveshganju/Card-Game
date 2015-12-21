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

/**** THIS CLASS HAS THE main() METHOD - DONOT MODIFY THE CODE ****/
class GameApp {

	public static void main(String[] args) {
		
		final GameData game  = new GameData();
		final Dealer dealer  = new Dealer(game);
		final Player player1 = new Player(game, 0);
		final Player player2 = new Player(game, 1);
		
		Thread dealerThread  = new Thread(dealer );
		Thread player1Thread = new Thread(player1);
		Thread player2Thread = new Thread(player2);
		
		dealerThread. start();
		player1Thread.start();
		player2Thread.start();
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new GameGUI(game,dealer,player1,player2);
			}
		});		
	}
}

