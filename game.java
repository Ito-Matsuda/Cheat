/*
 * This is an attempt at automizing the game of cheat
 * I guarantee that this is of my own work
 * Specifics for this implementation can be seen in the text file
 * TODO -> After everything is finished, or when it needs to, optimize the randomCard
 * 		   Function as it may take a while to do
 * 		-> Could do hashing I suppose
 * 		-> Implement placing doubles (as of right now only places singles)
 * 		-> A couple of other "todos" are in the player.java file
 */
import java.util.*;
public class game {

	
/*
 * Better Idea:
 * Generate ALL the cards in one go, the way I have it set up currently is that
 *  I keep an ongoing array to have all the cards in use. feelsbadman
 */
	
/*
 * Variable Declaration
 */
	static int usedCards[] = new int[52]; 
	// An array of card objects to see which one are in use
	// will probably get rid of this variable soon, 
	/* Will indicate this by having a "one" in the slot
	 * Suits are: Clubs (0), Diamond (1), Hearts (2), Spades (3),
	 * ace of clubs is 1, 2 of clubs is 2, king of spades = 52
	 * So formula is 13*SuitCode + cardNumber 
	*/
    static player playerz[] = new player[7]; // Just keep at max of 7 
	static Stack<Integer> cardStack = new Stack<Integer>(); 
	// The cards on the stack (people placing cards)
	// Declare the stack as type cards
	static boolean gameFinished = false;
    
    public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println("Enter in number of players: ");
		//generatePlayers(7); // For now will be tested with 7
		generateHand(7);
		tester();
		//cards cardPlaced = playerz[0].placeACard(6); // This really doesnt matter
		// ^ Person has to place the first card
		//goThroughTurns();
    }
	
    /**
     * FOLLOWING IS CHECK FOR GETTING RID OF NULLPOINTER
     */
    public static void generateSinglePlayer(){
    	player genPlayer = new player(0); 
    }
    /**
     * END NULL POINTER SHENANIGANS
     */
    
    /*
     * Used to check output and values 
     */
    public static void tester(){
    	for (int i = 0; i < 7; i++){
    		playerz[i].printCards();
    	}
    }
	
	/**
	 * Optimization of previous generatePlayers method
	 * Generates the hands for all the players
	 * 
	 * TO CHECK --> Check if every card is handed out / check the cardsEach variable
	 * 			--> do not want to end up trying to hand more cards than in the deck
	 * @param number
	 */
	public static void generateHand(int number){
		int cardsEach = (52/number); // for 7, gives 7 each (3 cards left unassigned)
		int playerAt = 0; // give em out to player 0 first, goes to 6
		int tempCard = 42; // To be changed later
		int size = 52; // Standard number of cards in a deck
        ArrayList<Integer> list = new ArrayList<Integer>(size);
        for(int i = 1; i <= size; i++) {
        	// Generate 52 numbers (cards)
            list.add(i);
        }
        Random rand = new Random();
        while(list.size() > 0) { // While there are cards to be handed
        	// Condition to check if playerAt has enough cards
        	// Line directly below causes a NullPointerException
        	if (playerz[playerAt].handSize() == cardsEach +1){
        		// TODO ABOVE
        		// +1 because I give every player a "zero" card in the player class
        		System.out.println("Finished handing cards to player " + playerAt);
        		playerAt++; // Move to the next player
        		System.out.println("Now moving onto player "+ playerAt);
        	}
        	else{
        		int index = rand.nextInt(list.size()); // Pick a random number
            	// Generate the card, and put it into person's hand
            	tempCard = index; // Create the card
            	playerz[playerAt].addCard(tempCard); // Add it to the hand
            	list.remove(index); // Remove the card so it wont be chosen
        	}
        }
	} // End generateHand
	
//	public static cards generateSingleCard(int theCard) {
//		cards card = new cards(theCard);
//		int card 
//		return card;
//	}
	// This is useless

	/**
	 * Goes through all the turns until the game ends
	 */
	public static void goThroughTurns(){
		int whoseTurn = 0; // begins at player 1
		int numberLeftGO = playerz.length / 3; // Once players reduced to a third just end game
		while(!gameFinished){ // while game is not finished
			placeCard(whoseTurn); // Place your card if you want to see if funct works, replace with 0
			// Also covers checking to see if anything is bullshit etc
			
			if(checkWin(whoseTurn)){ // Check if someone won
				numberLeftGO++;
				if (numberLeftGO == playerz.length){
					System.out.println("Game is over");
					System.exit(0);
				}
				System.out.println("Sup from goThroughTurns \nNumber until victory is " + 
						(playerz.length - numberLeftGO));
			}
			// else no one has emptied yet and pass over turns 
			// Controls who goes next below
			if (whoseTurn == 6){
				whoseTurn = 0; // reset to the first person
			}
			else{
			whoseTurn++; // go to next player
			}
		}
	} // End goThroughTurns
	
	/**
	 * Used to place the card on the stack
	 * @param whichPlayer the player whose making their move
	 */
	public static void placeCard(int whichPlayer){
		int cardNumber = cardStack.peek(); // Get the last put number on the pile lmao @.peek()
		int whoseMemory; // Who will call out the bullshit or not
		int cardPlaced = playerz[whichPlayer].placeACard(cardNumber);
		// add it to everyone's memory
		// ^IMPORTANT -imagine if player called bs on their own thing
		for (int i = 0; i < playerz.length; i++){
			playerz[i].addCardMemory(cardPlaced);
			// Add the 8 or whatever to memory
		}
		// call the check memory thing for everyone (kinda redundant as everyone has the same memory)
		// add some variety somehow later
		if (whichPlayer == playerz.length){
			// It's the last person
			whoseMemory = 0; // Just let this guy decide if it's bs or not
		}
		else{
			whoseMemory = whichPlayer+1; // Let the guy after decide
		}
		if (playerz[whoseMemory].checkMemory() == false){ // Call em out
			if (checkPile(cardPlaced) == true){ // If he isnt bs'ing
				System.out.println("Player " + whoseMemory + " got whopped, NO LIES");
				getWhopped(whoseMemory); // Give em the pile lmao
			}
			else{
				System.out.println("Player " + whichPlayer + " lied, get called out son");
				getWhopped(whichPlayer); // That's what you get for lying boi
			}
		} // End calling em out
		else{ // No bullshit / no call out, just add the card to the pile
			cardStack.push(cardPlaced);
		}
	} // End whichPlayer
	
	/**
	 * This would be called after checkMemory from player thing
	 * @param theCard The "card" that was placed down
	 * @return False if the person lied about the card
	 * 			True if the person did not lie
	 */
	public static boolean checkPile(int theCard){
		int topCard = cardStack.pop(); // Get the card that was put down
		if (topCard == theCard){
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
		int theCard;
		while(!cardStack.empty()){ // While it isn't empty
			theCard = cardStack.pop(); // Get the card
			playerz[whichPlayer].addCard(theCard); // Add the card to the players hand
		}
	} // End getWhopped
} // End game.java