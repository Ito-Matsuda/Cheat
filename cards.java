
public class cards {
// Attribute declaration
// This class is actually useless as we just get details from the player and game class
	// At this point it makes it more complicated to use this as a class for a set of cards
	// That are just numbers

private int number; 
// Ace = 1, 2 = 2 ... Jack = 11, Queen = 12, King = 13
//private int suit; 
// Suits are: Clubs (0), Diamond (1), Hearts (2), Spades (3),
// Dont use this, mostly useless
	public cards(int num){
		setNumber(num);
		//setSuit(suits);
	}
	public void setNumber(int theNum){
		number = theNum;
	}
	
//	public void setSuit(int theSuit){
//		suit = theSuit;
//	}
	
	public int getNumber(){
		return number;
	}
//	public int getSuit(){
//		return suit;
//	}
	public void printCardDetails(){
		System.out.println("Card Number is " + number);
	}
}
