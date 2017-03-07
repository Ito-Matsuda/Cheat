/*
 * This is an attempt at automizing the game of cheat
 * I guarantee that this is of my own work
 * Specifics for this implementation can be seen in the text file
 * TODO -> After everything is finished, or when it needs to, optimize the randomInt
 * 		   Function as it may take a while to do
 * 		-> Could do hashing I suppose
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
    static player playerz[] = new player[7]; // just keep at max of 7 
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
		int theCard;
		int whatSuit;
		// The empty array of cards to give to each player
		for (int j = 0; j < 7; j++) { // the 7 is temporary for 7 players
			for (int i = 0; i < number; i++){
				// TODO Generate cards here call randomInt
				theCard = randomInt(0,52);
				if((theCard / 13.0) > 3){
					cardsHanded[i].setSuit(3);
					whatSuit = 3;
					System.out.println("Set the suit to Spades");
				}
				else if ((theCard / 13.0) > 2){
					cardsHanded[i].setSuit(2);
					whatSuit = 2;
					System.out.println("Set the suit to Hearts");
				}
				else if ((theCard / 13.0) > 1){
					cardsHanded[i].setSuit(1);
					whatSuit = 1;
					System.out.println("Set the suit to diamond");
				}
				else {
					cardsHanded[i].setSuit(0);
					whatSuit = 0;
					System.out.println("Set the suit to clubs");
				}
				System.out.println("Finished setting the suit");
				if(whatSuit == 0){
					cardsHanded[i].setNumber(theCard); // Generated a 1 or 13 or w/e
					System.out.println("Set " + theCard + " as the number");
				}
				else if (whatSuit == 1){
					cardsHanded[i].setNumber(theCard-13);
					System.out.println("Set " + (theCard-13) + " as the number");
				}
				else if (whatSuit == 2){
					cardsHanded[i].setNumber(theCard-26);
					System.out.println("Set " + (theCard-26) + " as the number");
				}
				else{
					cardsHanded[i].setNumber(theCard-39);
					System.out.println("Set " + (theCard-39) + " as the number");
				}
			}
		playerz[j] = new player(cardsHanded); // Make em 
		Arrays.fill(cardsHanded, null); 
		// Completely reset the cardsHanded array as we will used them again
		} // End the outer loop
	}
	
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
	}

}
