import java.util.*;
public class player {
	
private ArrayList<cards> hand = new ArrayList<cards>();
private int memory[][] = new int[3][2];
// memory is a 3x2 representing how many cards of a certain suit has been played
// ex in [1][1] is which number (like 11 for Jack) and [1][2] is number of Jacks seen
// You keep track of at most 3 cards at a time

	/**
	 * Constructor used in beginning of the game, used to hand out cards to players
	 * @param handedOut The list of cards to give to this player
	 */
	public player (cards handedOut[]){
	// Builds the player's hand based on cards handed out
	
	}

	/**
	 * Adds a card to the players hand
	 * @param cardToAdd The card to add 
	 */
	public void addCard(cards cardToAdd){
		hand.add(cardToAdd); // Append to end of list
	}
	
	/**
	 * Removes a card from the players hand
	 * @param theCard The card to take away
	 */
	public void removeCard(int theCard){
		hand.remove(theCard);
	}
} // End class player
