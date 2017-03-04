import java.util.*;
public class player {
	
private ArrayList<cards> hand = new ArrayList<cards>();
private int memory[][] = new int[3][2];
// memory is a 3x2 representing how many cards of a certain suit has been played
// ex in [1][1] is which number (like 11 for Jack) and [1][2] is number of Jacks seen
// You keep track of at most 3 cards at a time
/*
 * [][] -> Most recent card
 * [][]
 * [][] -> The card that you would forget
 */

	/**
	 * Constructor used in beginning of the game, used to hand out cards to players
	 * @param handedOut The list of cards to give to this player
	 */
	public player (cards handedOut[]){
	// Builds the player's hand based on cards handed out
	hand = (ArrayList<cards>)(Arrays.asList(handedOut)); // Cast it
	// Memory has nothing at this point
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
	public void removeCard(cards theCard){
		hand.remove(theCard);
	}
	
	/**
	 * This controls the memory of the player for the cards that have been played 
	 * TODO look below for super tryhard thing
	 * IF you want to be super tryhard, you should discard the card which is furthest
	 * from the card that was just placed.
	 * IE) If card 6 was placed, and there is a Queen in your memory, get rid of that one
	 *     --> The cards necessary to get to that one are farther than say a 7 or 8
	 *     --> So if 7 with 3 count is in memory[2][1], you do not replace that one 
	 * @param theCard The card to remember
	 * 		  "theCard" is an int because it does NOT matter which suit it is
	 */
	public void addCardMemory(int theCard){
		/*
		 * First in first out
		 * 	The card at index [1][1] and [1][0] would get moved to [2][1] and [2][0]
		 *  The card at index [0][0] and [0][1] would get moved to [1][0] and [1][1]
		 */
		boolean cardFound = false;
		for (int i = 0; i < 3; i++){
			if(theCard == memory[i][0]){ // Check if the card is already there
				// If you found the card, then you do not need to make changes to memory array
				// Just add "one" to cards seen
				memory[i][0] = memory[i][0]+1;
				cardFound = true;
				break;
			}
			
		}
		/* The card said is not in your memory so commit it
		 * Probably should make it such that if the card at the last index is at count of 3
		 * then that card isn't replaced. The card in question is not as important 
		 * Look at the "todo" above to see a cool implementation, would have lower priority over
		 *   the one about count of 3 
		 */
		if (cardFound == false){
			if (memory[2][1]==3){
				if (memory[1][1] == 3){
					if (memory[0][1] == 3){
						// All cards in memory have 3 so just ignore it
					}
					else{
						// [1][1] and [2][1] have a 3, but [0][1] doesnt
						// Replace the [0][0] with the now known card
						memory[0][0] = theCard; memory[0][1] = 1;
					} 
				}
				// [2][1] is a three, others are not pick one of the [1][0] or [0][0] to replace
				else{
					if (memory[1][1] < memory[0][1]){ // [1][1] has a lower count
						memory[1][0] = theCard; memory[1][1] = 1;
					}
					else{
						memory[0][0] = theCard; memory[0][1] = 1;
					}
				}
			}
			// Else none of the cards have a 3, replace one that has lowest count
			else{
				if (memory[1][1] < memory[0][1] && memory[1][1] < memory[2][1]){
					// [1][1] has the lowest so replace that one 
					memory[1][0] = theCard; memory[1][1] = 1;
				}
				else if (memory[0][1] < memory[2][1] && memory[0][1] < memory[1][1]){
					memory[0][0] = theCard; memory[0][1] = 1;
				}
				else if (memory[2][1] < memory[1][1] && memory[2][1] < memory[0][1]){
					memory[2][0] = theCard; memory[2][1] = 1;
				}
				else{ // They're all the same could do the thing here where you look at the number
					// Furthest off the current card, but eh, can't make it perfect
					memory[1][0] = memory[0][0]; memory[1][1] = memory[0][1];
					memory[2][0] = memory[1][0]; memory[2][1] = memory[1][1];
					memory[0][0] = theCard; memory[0][1] = 1; // Make note of it, with 1 occurence
				}
			}
		} // End cardFound == false
		// Add a function call to something that checks the cards in memory 
		// Checks if anything is fishy, if so, return false or something to call bullshit
	} // End addCardMemory
	
} // End class player