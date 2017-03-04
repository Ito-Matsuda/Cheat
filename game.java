/*
 * This is an attempt at automizing the game of cheat
 * I guarantee that this is of my own work
 * Specifics for this implementation can be seen in the text file
 */
import java.util.*;
public class game {

/*
 * Variable Declaration
 */
// static cards usedCards[] = new cards[52] // An array of card objects to see which one are in use
	// Will indicate this by having a "one" in the slot
// static ArrayList<player> playerz = new ArrayList<player>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Enter in number of players: ");
		//generatePlayers(7); // For now will be tested with 7
	}
	
	/**
	 * This will generate and hand out each player's
	 * starting hand
	 * @param number --> Number of players
	 */
	public static void generatePlayers(int number){
		int cardsEach = (52/number); 
		// Give a floored number
		// at the end, just give each player another card until there is no more to be given
		// use addCard funct in player
		cards cardsHanded[] = new cards[cardsEach];
		// The empty array of cards to give to each player
		for (int i = 0; i < number; i++){
			// TODO Generate cards here
			// playerz.add(i,
		}
		
	}

}
