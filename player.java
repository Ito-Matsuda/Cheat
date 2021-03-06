import java.util.*;
public class player {
	
private ArrayList<cards> hand = new ArrayList<cards>();
private int memory[][] = new int[3][2];
private int sizeHand;
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
/*
	public player (int initNumCards){
		// Test this needs to be empty at the beginning
		// Builds the player's hand based on cards handed out
		setHand(initNumCards);
		setMemory();
		setHandSize();
		// Memory has nothing at this point
	}
	
	public void setHand(int initNumCards){
		cards handedOut[] = new cards [1];
		handedOut[0].setNumber(0); // Instatiate it? 
		hand = (ArrayList<cards>)(Arrays.asList(handedOut));
	}
	*/	


	/**
	 * Constructor used in beginning of the game, used to hand out cards to players
	 * @param handedOut The list of cards to give to this player
	 */
	public player (){
		// Test this needs to be empty at the beginning
		// Builds the player's hand based on cards handed out
		setHand();
		setMemory();
		setHandSize();
		// Memory has nothing at this point
	}
	
	public void setHand(){
		hand = (ArrayList<cards>)(Arrays.asList(0));
	}
	
	public void setMemory(){
		Arrays.fill(memory, 0); // Everything is a zero
	}
	
	public void setHandSize() {
		sizeHand = 0;
	}
	
	
	/**
	 * Return number of cards  in the hand 
	 * @return
	 */
	public int handSize(){
		System.out.println("Size of hand " + hand.size());
		return hand.size();
	}
	/**
	 * Used to check the status of the player hands
	 * @return True if the hand is empty, false if not
	 */
	public boolean checkHand(){
		if (hand.isEmpty()){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param lastCardOnPile The card they have to match
	 * @return The card they wish to place
	 */
	public cards placeACard(int lastCardOnPile){
		cards theCard;
		int i = 0;
		while(i<hand.size()){
			theCard = hand.get(i); // Get the card
			if(Math.abs(theCard.getNumber()-lastCardOnPile) == 1){
				// If the card is either one up or one down from the thing
				System.out.println("Player places card " + theCard.getNumber());
				return theCard; // To be used in game.java 
			}
			i++;
		}
		/*
		 *  If you can't find a card that is one less or one greater
		 *  You don't have a choice but to bullshit it
		 *  TODO
		 *  I would imagine you would want to get rid of a card for which
		 *  You don't have dupes for but that can be added later
		 */
		return hand.get(i); // Return some BS card the last one
	} // End placeACard
	// Functions that help visualize what's going on right below
	/**
	 * Prints the cards
	 */
	public void printCards(){
		cards currentCard;
		for(int i = 0; i < hand.size(); i++){
			currentCard = hand.get(i);
			currentCard.printCardDetails();
		}
	} // End printCards
	
	/**
	 * Prints what you have in memory
	 */
	public void printMemory(){
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 2; j++){
				System.out.print(memory[i][j] + ":");
			}
			System.out.println(""); // Change it to newline
		}
	} // End printMemory

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
	} // End addCardMemory
	
	/**
	 * This is called after every addCardMemory function
	 * Will check the player's memory to see if there are any possible contradictions 
	 * with the card just played. Does this by looking at the counts of each card in memory
	 * @return True if there was no contradiction found, false if fishy
	 */
	public boolean checkMemory(){
		for (int i = 0; i < 3; i++){
			if (memory[i][1] >4){
				System.out.println("Imma call bs on that boiii");
				return false;
				// Call em out on it
			}
		}
		// Nothing of importance was found
		return true; 
	} // End checkMemory
	
} // End class player