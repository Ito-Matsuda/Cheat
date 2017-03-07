/*
 * This is an attempt at automizing the game of cheat
 * I guarantee that this is of my own work
 * Specifics for this implementation can be seen in the text file
 * TODO -> After everything is finished, or when it needs to, optimize the randomInt
 * 		   Function as it may take a while to do
 * 		-> Could do hashing I suppose
 * 		-> Implement placing doubles (as of right now only places singles)
 * 		-> A couple of other "todos" are in the player.java file
 */
import java.util.*;
public class game {

/*
 * Variable Declaration
 */
	static int usedCards[] = new int[52]; // An array of card objects to see which one are in use
	/* Will indicate this by having a "one" in the slot
	 * Suits are: Clubs (0), Diamond (1), Hearts (2), Spades (3),
	 * ace of clubs is 0, 2 of clubs is 1,
	 * So formula is 13*SuitCode + cardNumber 
	*/
    static player playerz[] = new player[7]; // Just keep at max of 7 
	static Stack<cards> cardStack = new Stack<cards>(); 
	// The cards on the stack (people placing cards)
	// Declare the stack as type cards
	static boolean gameFinished = false;
    
    public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Enter in number of players: ");
		// generatePlayers(7); // For now will be tested with 7
		// goThroughTurns();
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
		int theCard;
		int determineSuit;
		// The empty array of cards to give to each player
		for (int j = 0; j < 7; j++) { // the 7 is temporary for 7 players
			for (int i = 0; i < number; i++){
				// TODO Generate cards here call randomInt
				theCard = randomInt(0,52);
				if((theCard / 13.0) > 3){
					cardsHanded[i].setSuit(3);
					determineSuit = 3;
					System.out.println("Set the suit to Spades");
				}
				else if ((theCard / 13.0) > 2){
					cardsHanded[i].setSuit(2);
					determineSuit = 2;
					System.out.println("Set the suit to Hearts");
				}
				else if ((theCard / 13.0) > 1){
					cardsHanded[i].setSuit(1);
					determineSuit = 1;
					System.out.println("Set the suit to diamond");
				}
				else {
					cardsHanded[i].setSuit(0);
					determineSuit = 0;
					System.out.println("Set the suit to clubs");
				}
				System.out.println("Finished setting the suit");
				cardsHanded[i].setNumber(theCard - (determineSuit*13));
				System.out.println("Set the number as " + (theCard - (determineSuit*13)));
			}
		playerz[j] = new player(cardsHanded); // Make em 
		Arrays.fill(cardsHanded, null); 
		// Completely reset the cardsHanded array as we will used them again
		} // End the outer loop
	} // End generatePlayers
	
	/**
	 * Generates a random number given a max and a min
	 * Exact same code from Auto-Avalon
	 * @param min The lowest number
	 * @param max The highest number
	 * @return
	 */
	public static int randomInt(int min, int max) {
		Random rand = new Random();
		int randomNum;
		// We need it to execute at least once
		do {
			randomNum = rand.nextInt((max - min) + 1) + min;
		} while (usedCards[randomNum] != 0); 
		// While you have not found something taken up
		// Super inefficient (it's like random sort lol)
		// OPTIMIZE LATER, this is just a proof of concept, so it just has to work
		usedCards[randomNum] = 1;// The array now has something in it and that index is used
		return randomNum;
	} // End randomInt

	/**
	 * Goes through all the turns until the game ends
	 */
	public static void goThroughTurns(){
		while(!gameFinished){ // while game is not finished
			// place card on stack
			// check if anyone won 
			// check if anyone calls bull
				// check pile if so
					// if bull player who put gets whopped
					// if not then that person who calls it get whopped
			// else go to next player and end up at gameFinished again
		}
	} // End goThroughTurns
	
	/**
	 * Used to place the card on the stack
	 * @param whichPlayer the player whose making their move
	 */
	public static void placeCard(int whichPlayer){
		
	} // End whichPlayer
	/**
	 * This would be called after checkMemory from player thing
	 * @param theCard The "card" that was placed down
	 * @return False if the person lied about the card
	 * 			True if the person did not lie
	 */
	public static boolean checkPile(cards theCard){
		cards topCard = cardStack.pop(); // Get the card that was put down
		if (topCard.getNumber() == theCard.getNumber()){
			return true;
		}
		return false;
	} // End checkPile
	
	/**
	 * Used to see if a player has won
	 * @param whichPlayer check to see if that player won
	 * @return False if no win, true if win
	 */
	public static boolean checkWin(int whichPlayer){
		if (playerz[whichPlayer].checkHand() == true){
			System.out.println("Player " + whichPlayer + " has emptied their hand!");
			return true;
		}
		return false;
	} // End checkWin
	
	/**
	 * Used when one player gets whooped, IE) when someone has to pick up all the cards
	 * Called eventually after checkPile
	 * @param whichPlayer The player that got cheesed
	 */
	public static void getWhopped(int whichPlayer){
		cards theCard;
		while(!cardStack.empty()){ // While it isn't empty
			theCard = cardStack.pop(); // Get the card
			playerz[whichPlayer].addCard(theCard); // Add the card to the players hand
		}
	} // End getWhopped
} // End game.java