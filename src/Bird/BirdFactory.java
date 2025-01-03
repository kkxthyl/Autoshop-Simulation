package Bird;

/*
 * this is an interface for the Bird Concrete Factory class 
 * for the user to tell the program which object they want to initliaze 
 */

public abstract class BirdFactory {
	public abstract Bird createBird(String type); 
}
