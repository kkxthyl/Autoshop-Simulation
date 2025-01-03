package Bird;

import processing.core.PVector;
import util.Util;

/*
 * this is the concrete factory class that is used to inialize the bird objects and hide the parameters from the users
 */
public class BirdConcreteFactory extends BirdFactory{
	public Bird createBird(String type) {
		Bird bird = null; 
		if (type == "blackBirdOne") {
			bird = new BlackBird(new PVector(150, 70), new PVector(3, 0), Util.random(0.08,  0.15));
		}
		else if (type == "blackBirdTwo") {
			bird = new BlackBird(new PVector(250, 75), new PVector(3, 0), Util.random(0.08,  0.15));
		}
		else if (type == "greyBird") {
			bird = new GreyBird(new PVector(200, 90), new PVector(3, 0), Util.random(0.08,  0.15));
		}
		return bird; 
	}
}
