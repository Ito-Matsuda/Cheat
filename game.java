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
		generatePlayers(7); // For now will be tested with 7
		cards cardPlaced = playerz[0].placeACard(6); // This really doesnt matter
		// ^ Person has to place the first card
		goThroughTurns();
	}
	
	/**
	 * This will generate and hand out each player's
	 * starting hand
	 * @param number --> Number of players
	 */
	public static void generatePlayers(int number){
		int cardsEach = (52/number); // May end up giving more cards than intended
		// Give a floored number
		// at the end, just give each player another card until there is no more to be given
		// use addCard funct in player
		cards cardsHanded[] = new cards[cardsEach];
		cards tempCard = generateSingleCard(42);
//		tempCard.setNumber(3); // this causes an error , must create each object first
//		cardsHanded[6] = tempCard;
//		cardsHanded[6].printCardDetails();;
		// make a cards handed array based on the thing
		int theCard;
		int determineSuit;
		// The empty array of cards to give to each player
		for (int j = 0; j < number; j++) { // to go through the number of players
			for (int i = 0; i < cardsEach; i++){ // put in cards
				theCard = randomCard(0,52); // put in a random card
				if((theCard / 13.0) > 3){
					// cardsHanded[i].setSuit(3);
					determineSuit = 3;
					//System.out.println("Set the suit to Spades");
				}
				else if ((theCard / 13.0) > 2){
					// cardsHanded[i].setSuit(2);
					determineSuit = 2;
					//System.out.println("Set the suit to Hearts");
				}
				else if ((theCard / 13.0) > 1){
					// cardsHanded[i].setSuit(1);
					determineSuit = 1;
					//System.out.println("Set the suit to diamond");
				}
				else {
					// cardsHanded[i].setSuit(0);
					determineSuit = 0;
				//	System.out.println("Set the suit to clubs");
				}
				//System.out.println("The number to put is " + (theCard - (determineSuit*13)));
				tempCard = generateSingleCard(theCard- (determineSuit*13));
				cardsHanded[i] = tempCard;
				//cardsHanded[i].setNumber(theCard - (determineSuit*13));
				//System.out.println("Set the number as " + (theCard - (determineSuit*13)));
			}
		System.out.println("---- Finished setting for player " + j + " -----");
		for(int k = 0; k <7;k++){
			System.out.print("Card number: ");
			cardsHanded[k].printCardDetails();
		}
		playerz[j] = new player(cardsHanded); // Make em 
		Arrays.fill(cardsHanded, null); 
		// Completely reset the cardsHanded array as we will used them again
		} // End the outer loop
	} // End generatePlayers
	
	
	public static cards generateSingleCard(int theCard) {
		cards card = new cards(theCard);
		return card;
	}
	/**
	 * Generates a random number given a max and a min
	 * USED SOLEY FOR CARD GENERATION
	 * Exact same code from Auto-Avalon
	 * @param min The lowest number
	 * @param max The highest number
	 * @return
	 */
	public static int randomCard(int min, int max) {
		Random rand = new Random();
		int randomNum;
		// We need it to execute at least once
		do {
			randomNum = rand.nextInt((max - min) + 1) + min;
			//System.out.println("Generated " + randomNum);
		} while (usedCards[randomNum] != 0); 
		// While you have not found something taken up
		// Super inefficient (it's like random sort lol)
		// OPTIMIZE LATER, this is just a proof of concept, so it just has to work
		usedCards[randomNum] = 1;// The array now has something in it and that index is used
		return randomNum;
	} // End randomCard

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
		int cardNumber = cardStack.peek().getNumber(); // Get the last put number on the pile lmao @.peek()
		int whoseMemory; // Who will call out the bullshit or not
		cards cardPlaced = playerz[whichPlayer].placeACard(cardNumber);
		// add it to everyone's memory
		// ^IMPORTANT -imagine if player called bs on their own thing
		for (int i = 0; i < playerz.length; i++){
			playerz[i].addCardMemory(cardPlaced.getNumber());
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